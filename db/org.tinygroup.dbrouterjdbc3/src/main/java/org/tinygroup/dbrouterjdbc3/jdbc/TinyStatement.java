/**
 *  Copyright (c) 1997-2013, tinygroup.org (luo_guo@live.cn).
 *
 *  Licensed under the GPL, Version 3.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.gnu.org/licenses/gpl.html
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 * --------------------------------------------------------------------------
 *  版权 (c) 1997-2013, tinygroup.org (luo_guo@live.cn).
 *
 *  本开源软件遵循 GPL 3.0 协议;
 *  如果您不遵循此协议，则不被允许使用此文件。
 *  你可以从下面的地址获取完整的协议文本
 *
 *       http://www.gnu.org/licenses/gpl.html
 */
package org.tinygroup.dbrouterjdbc3.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.tinygroup.commons.tools.Assert;
import org.tinygroup.dbrouter.RouterManager;
import org.tinygroup.dbrouter.StatementProcessor;
import org.tinygroup.dbrouter.config.Partition;
import org.tinygroup.dbrouter.config.Router;
import org.tinygroup.dbrouter.config.Shard;
import org.tinygroup.dbrouter.factory.RouterManagerBeanFactory;
import org.tinygroup.dbrouter.util.DbRouterUtil;
import org.tinygroup.dbrouter.util.OrderByProcessor;
import org.tinygroup.dbrouter.util.OrderByProcessor.OrderByValues;
import org.tinygroup.dbrouter.util.SortOrder;
import org.tinygroup.jsqlparser.statement.select.PlainSelect;
import org.tinygroup.jsqlparser.statement.select.Select;
import org.tinygroup.jsqlparser.statement.select.SelectBody;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;

/**
 * 
 * 功能说明:
 * <p>
 * 
 * 开发人员: renhui <br>
 * 开发时间: 2013-12-24 <br>
 * <br>
 */
public class TinyStatement implements Statement {
	protected Map<Shard, Statement> statementMap = new ConcurrentHashMap<Shard, Statement>();
	protected final TinyConnection tinyConnection;
	protected RouterManager routerManager = RouterManagerBeanFactory
			.getManager();
	protected StatementProcessor statementProcessor = null;
	protected final Router router;
	protected boolean isClosed;
	protected int maxRows;
	protected boolean escapeProcessing = true;
	protected int queryTimeout = 5;
	protected ResultSet resultSet;
	protected int updateCount;
	protected final boolean closedByResultSet;
	protected final int resultSetType;
	protected final int resultSetConcurrency;
	protected boolean cancelled;
	protected int fetchSize = 100;
	protected boolean autoCommit = true;

	protected Logger logger = LoggerFactory.getLogger(TinyStatement.class);

	public TinyStatement(Router router, TinyConnection tinyConnection,
			int resultSetType, int resultSetConcurrency,
			boolean closedByResultSet, boolean autoCommit) {
		Assert.assertNotNull(tinyConnection, "tinyConnection must not null");
		this.router = router;
		this.tinyConnection = tinyConnection;
		this.closedByResultSet = closedByResultSet;
		this.resultSetType = resultSetType;
		this.resultSetConcurrency = resultSetConcurrency;
		this.autoCommit = autoCommit;
	}

	public ResultSet executeQuery(String sql) throws SQLException {
		statementProcessor = null;
		checkClosed();
		closeOldResultSet();
		Partition partition = routerManager.getPartition(router, sql);
		List<RealStatementExecutor> statements = getStatementsBySql(partition,
				sql);
		if (statements.size() == 1) {
			resultSet = statements.get(0).executeQuery();
			return new TinyResultSetWrapper(sql, resultSet, this,
					tinyConnection);
		} else if (statements.size() > 1) {
			List<ResultSet> resultSetList = new ArrayList<ResultSet>();
			List<ResultSetExecutor> resultSetExecutors = new ArrayList<ResultSetExecutor>();
			for (RealStatementExecutor statement : statements) {
				ResultSet realResultSet = statement.executeQuery();
				resultSetExecutors.add(new ResultSetExecutor(realResultSet,
						statement.getExecuteSql(), statement.getOriginalSql(),
						statement.getShard()));
				resultSetList.add(realResultSet);
			}
			if (statementProcessor != null) {
				return statementProcessor.combineResult(statements.get(0)
						.getExecuteSql(), resultSetList);
			} else {
				resultSet = new TinyResultSetMultiple(sql, router,
						resultSetExecutors, this, tinyConnection);
			}
			return resultSet;
		}
		return null;
	}

	public int executeUpdate(String sql) throws SQLException {
		checkClosed();
		closeOldResultSet();
		Partition partition = routerManager.getPartition(router, sql);
		List<RealStatementExecutor> statements = getStatementsBySql(partition,
				sql);
		if (statements.size() == 1) {
			updateCount = statements.get(0).executeUpdate();
			return updateCount;
		} else if (statements.size() > 1) {
			if (partition.getMode() == Partition.MODE_PRIMARY_SLAVE
					&& tinyConnection.getAutoCommit()) {
				throw new RuntimeException(
						"primary slave mode exist one more write database,the connection autocommit must set false");
			}
			for (RealStatementExecutor statement : statements) {
				updateCount += statement.executeUpdate();
			}
			return updateCount;
		}
		return 0;
	}

	protected Statement getStatement(Shard shard) throws SQLException {
		Statement statement = statementMap.get(shard);
		if (tinyConnection.getAutoCommit() != autoCommit) {// 有调用过tinyconnection.setAutoCommit(),重写创建statement
			logger.logMessage(
					LogLevel.DEBUG,
					"autoCommit has change,original:{0}，now:{1},create new statement",
					autoCommit, tinyConnection.getAutoCommit());
			statement = shard.getConnection(tinyConnection).createStatement(
					resultSetType, resultSetConcurrency,
					getResultSetHoldability());
			setStatementProperties(statement);
			statementMap.put(shard, statement);
		} else {
			if (statement == null) {
				statement = shard.getConnection(tinyConnection)
						.createStatement(resultSetType, resultSetConcurrency,
								getResultSetHoldability());
				setStatementProperties(statement);
				statementMap.put(shard, statement);
			}
		}

		return statement;
	}

	protected void setStatementProperties(Statement statement)
			throws SQLException {
		statement.setMaxRows(maxRows);
		statement.setEscapeProcessing(escapeProcessing);
		statement.setQueryTimeout(queryTimeout);
		statement.setFetchSize(fetchSize);
	}

	public void close() throws SQLException {
		StringBuffer buffer = new StringBuffer();
		boolean noError = true;
		for (Statement statement : statementMap.values()) {
			try {
				statement.close();
			} catch (SQLException e) {
				buffer.append(String
						.format("statement close error,errorcode:%s,sqlstate:%s,message:%s \n",
								e.getErrorCode(), e.getSQLState(),
								e.getMessage()));
				noError = false;
				logger.errorMessage("statement close error", e);
			}

		}
		statementMap.clear();
		this.isClosed = true;
		if (!noError) {
			throw new SQLException(buffer.toString());
		}
	}

	/**
	 * INTERNAL. Close and old result set if there is still one open.
	 */
	protected void closeOldResultSet() throws SQLException {
		try {
			if (!closedByResultSet) {
				if (resultSet != null) {
					resultSet.close();
				}
			}
		} finally {
			cancelled = false;
			resultSet = null;
			updateCount = 0;
		}
	}

	/**
	 * Check whether the statement was cancelled.
	 * 
	 * @return true if yes
	 */
	public boolean wasCancelled() {
		return cancelled;
	}

	protected void checkClosed() throws SQLException {
		tinyConnection.checkClosed();
		if (isClosed) {
			throw new SQLException("statement is closed");
		}
	}

	public int getMaxFieldSize() throws SQLException {
		checkClosed();
		return 0;
	}

	public void setMaxFieldSize(int max) throws SQLException {
		checkClosed();
	}

	public int getMaxRows() throws SQLException {
		return maxRows;
	}

	public void setMaxRows(int max) throws SQLException {
		checkClosed();
		if (maxRows < 0) {
			throw new SQLException("not valid value for maxRows:" + maxRows);
		}
		this.maxRows = max;
	}

	public void setEscapeProcessing(boolean enable) throws SQLException {
		checkClosed();
		this.escapeProcessing = enable;
	}

	public int getQueryTimeout() throws SQLException {
		checkClosed();
		return queryTimeout;
	}

	public void setQueryTimeout(int seconds) throws SQLException {
		checkClosed();
		this.queryTimeout = seconds;
	}

	public void cancel() throws SQLException {
		checkClosed();
		for (Statement statement : statementMap.values()) {
			statement.cancel();
		}
		cancelled = true;

	}

	public SQLWarning getWarnings() throws SQLException {
		checkClosed();
		return null;
	}

	public void clearWarnings() throws SQLException {
		checkClosed();
	}

	/**
	 * Sets the name of the cursor. This call is ignored.
	 */
	public void setCursorName(String name) throws SQLException {
		checkClosed();
	}

	public boolean execute(String sql) throws SQLException {
		org.tinygroup.jsqlparser.statement.Statement statement = routerManager
				.getSqlStatement(sql);
		boolean returnsResultSet = false;
		if (statement instanceof Select) {
			returnsResultSet = true;
			executeQuery(sql);
		} else {
			returnsResultSet = false;
			executeUpdate(sql);
		}
		return returnsResultSet;
	}

	private Shard getShard(String sql, Partition partition) throws SQLException {
		Collection<Shard> shards = routerManager.getShards(partition, sql,
				getPreparedParams());
		if (shards.size() == 0) {
			throw new SQLException("没有可用的数据库连接。");
		}
		Iterator<Shard> iterator = shards.iterator();
		Shard shard = iterator.next();
		return shard;
	}

	private List<Shard> getPrimarySlaveShard(String sql, Partition partition)
			throws SQLException {
		org.tinygroup.jsqlparser.statement.Statement statement = routerManager
				.getSqlStatement(sql);
		List<Shard> shards = new ArrayList<Shard>();
		if (!(statement instanceof Select)) {
			shards.addAll(routerManager.getShardBalance().getWritableShard(
					partition));
		} else {
			if (!tinyConnection.getAutoCommit()) {// 从写的列表中随机选择一个进行读取
				Shard shard = routerManager.getShardBalance().getReadShardWithTransaction(partition);
				shards.add(shard);
			}else{
				Shard shard = routerManager.getShardBalance().getReadableShard(
						partition);
				shards.add(shard);
			}
		}
		return shards;
	}


	public ResultSet getResultSet() throws SQLException {
		checkClosed();
		return resultSet;
	}

	public int getUpdateCount() throws SQLException {
		checkClosed();
		return updateCount;
	}

	public boolean getMoreResults() throws SQLException {
		checkClosed();
		closeOldResultSet();
		return false;
	}

	public void setFetchDirection(int direction) throws SQLException {
		checkClosed();
	}

	public int getFetchDirection() throws SQLException {
		checkClosed();
		return ResultSet.FETCH_FORWARD;
	}

	public void setFetchSize(int rows) throws SQLException {
		checkClosed();
		if (rows < 0 || (rows > 0 && maxRows > 0 && rows > maxRows)) {
			throw new SQLException("invalid value for rows:" + rows);
		}
		if (rows == 0) {
			rows = 100;
		}
		this.fetchSize = rows;

	}

	public int getFetchSize() throws SQLException {
		checkClosed();
		return fetchSize;
	}

	public int getResultSetConcurrency() throws SQLException {
		checkClosed();
		return resultSetConcurrency;
	}

	public int getResultSetType() throws SQLException {
		checkClosed();
		return resultSetType;
	}

	public void addBatch(String sql) throws SQLException {
		checkClosed();
		Partition partition = routerManager.getPartition(router, sql);
		List<RealStatementExecutor> statements = getStatementsBySql(partition,
				sql);
		for (RealStatementExecutor statement : statements) {
			statement.addBatch();
		}
	}

	/**
	 * 根据sql获取需要执行的statements列表
	 * 
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	protected List<RealStatementExecutor> getStatementsBySql(
			Partition partition, String sql) throws SQLException {
		List<RealStatementExecutor> statements = new ArrayList<RealStatementExecutor>();
		if (partition.getMode() == Partition.MODE_PRIMARY_SLAVE) {
			List<Shard> shards = getPrimarySlaveShard(sql, partition);
			for (Shard shard : shards) {
				Statement realStatement = getStatement(shard);
				String realSql = routerManager.getSql(partition, shard, sql,
						getPreparedParams());
				statements.add(new RealStatementExecutor(realStatement,
						realSql, sql, shard, partition));
			}

		} else {
			Shard firstShard = partition.getShards().get(0);// 获取第一个分片
			// 获取实际的表名
			String transSql = DbRouterUtil.transformInsertSql(sql, router,
					firstShard.getTableMappingMap(),
					tinyConnection.getMetaData());// 如果是insert语句，那么检测是否有主键字段。
			Collection<Shard> shards = routerManager.getShards(partition,
					transSql, getPreparedParams());
			for (StatementProcessor processor : routerManager
					.getStatementProcessorList()) {
				if (processor.isMatch(transSql)) {
					statementProcessor = processor;
					transSql = processor.getSql(transSql);
					break;
				}
			}
			if (shards.size() == 0) {// 如果一个都没有符合的，就匹配所有的shard。
				shards = partition.getShards();
			}
			Iterator<Shard> iterator = shards.iterator();
			while (iterator.hasNext()) {
				Shard shard = iterator.next();
				Statement realStatement = getStatement(shard);
				String realSql = routerManager.getSql(partition, shard,
						transSql, getPreparedParams());// 变化表名等
				statements.add(new RealStatementExecutor(realStatement,
						realSql, sql, shard, partition));
			}
		}
		return statements;
	}

	protected Object[] getPreparedParams() {
		return new Object[0];
	}

	public void clearBatch() throws SQLException {
		checkClosed();
		for (Statement statement : statementMap.values()) {
			statement.clearBatch();
		}
	}

	public int[] executeBatch() throws SQLException {
		checkClosed();
		List<Integer> affectList = new ArrayList<Integer>();
		for (Statement statement : statementMap.values()) {
			int[] affects = statement.executeBatch();
			for (int affect : affects) {
				affectList.add(affect);
			}
		}
		int[] results = new int[affectList.size()];
		for (int i = 0; i < results.length; i++) {
			results[i] = affectList.get(i);
		}
		return results;
	}

	public Connection getConnection() throws SQLException {
		return tinyConnection;
	}

	public boolean getMoreResults(int current) throws SQLException {
		switch (current) {
		case Statement.CLOSE_CURRENT_RESULT:
		case Statement.CLOSE_ALL_RESULTS:
			checkClosed();
			closeOldResultSet();
			break;
		case Statement.KEEP_CURRENT_RESULT:
			break;
		default:
			throw new SQLException("invalid value for current:" + current);
		}
		return false;
	}

	public ResultSet getGeneratedKeys() throws SQLException {
		// TODO
		routerManager.getPrimaryKey(router, "IDENTITY");// 键值
		throw new SQLException("not support generatedKeys");
	}

	public int executeUpdate(String sql, int autoGeneratedKeys)
			throws SQLException {
		return executeUpdate(sql);
	}

	public int executeUpdate(String sql, int[] columnIndexes)
			throws SQLException {
		return executeUpdate(sql);
	}

	public int executeUpdate(String sql, String[] columnNames)
			throws SQLException {
		return executeUpdate(sql);
	}

	public boolean execute(String sql, int autoGeneratedKeys)
			throws SQLException {
		return execute(sql);
	}

	public boolean execute(String sql, int[] columnIndexes) throws SQLException {
		return execute(sql);
	}

	public boolean execute(String sql, String[] columnNames)
			throws SQLException {
		return execute(sql);
	}

	public int getResultSetHoldability() throws SQLException {
		checkClosed();
		return ResultSet.HOLD_CURSORS_OVER_COMMIT;
	}

	class RealStatementExecutor {
		private Statement realStatement;
		private String executeSql;
		private String originalSql;
		private Shard shard;
		private Partition partition;

		public RealStatementExecutor(Statement realStatement,
				String executeSql, String originalSql, Shard shard,
				Partition partition) {
			super();
			this.realStatement = realStatement;
			this.executeSql = executeSql;
			this.originalSql = originalSql;
			this.partition = partition;
			this.shard = shard;
		}

		public void addBatch() throws SQLException {
			realStatement.addBatch(executeSql);
		}

		public Statement getRealStatement() {
			return realStatement;
		}

		public String getExecuteSql() {
			return executeSql;
		}

		public String getOriginalSql() {
			return originalSql;
		}

		public Shard getShard() {
			return shard;
		}

		public Partition getPartition() {
			return partition;
		}

		public ResultSet executeQuery() throws SQLException {
			logger.logMessage(LogLevel.DEBUG, "{0}:{1}", shard.getId(),
					originalSql);
			if (realStatement instanceof PreparedStatement) {
				PreparedStatement prepared = (PreparedStatement) realStatement;
				return prepared.executeQuery();
			}
			logger.logMessage(LogLevel.DEBUG, "{0}:{1}", shard.getId(),
					executeSql);
			return realStatement.executeQuery(executeSql);

		}

		public int executeUpdate() throws SQLException {
			logger.logMessage(LogLevel.DEBUG, "{0}:{1}", shard.getId(),
					originalSql);
			if (realStatement instanceof PreparedStatement) {
				PreparedStatement prepared = (PreparedStatement) realStatement;
				return prepared.executeUpdate();
			}
			logger.logMessage(LogLevel.DEBUG, "{0}:{1}", shard.getId(),
					executeSql);
			return realStatement.executeUpdate(executeSql);
		}
	}

	class ResultSetExecutor {

		private ResultSet resultSet;

		private String executeSql;

		private String originalSql;

		private boolean isAfterLast;

		private boolean isBeforeFirst;

		private OrderByProcessor orderByProcessor;

		private Shard shard;

		public ResultSetExecutor(ResultSet resultSet, String executeSql,
				String originalSql, Shard shard) throws SQLException {
			super();
			this.resultSet = resultSet;
			this.executeSql = executeSql;
			this.originalSql = originalSql;
			this.shard = shard;
			org.tinygroup.jsqlparser.statement.Statement sqlStatement = routerManager
					.getSqlStatement(executeSql);
			if (sqlStatement instanceof Select) {
				Select select = (Select) sqlStatement;
				SelectBody body = select.getSelectBody();
				if (body instanceof PlainSelect) {
					PlainSelect plainSelect = (PlainSelect) body;
					orderByProcessor = new OrderByProcessor(plainSelect,
							resultSet);
				}
			} else {
				throw new RuntimeException("must be a query sql");
			}
		}

		public SortOrder getSortOrder() {
			if (orderByProcessor == null) {
				return null;
			}
			return orderByProcessor.getSortOrder();
		}

		public ResultSet getResultSet() {
			return resultSet;
		}

		public String getExecuteSql() {
			return executeSql;
		}

		public boolean[] getOrderTypes() {
			if (orderByProcessor != null) {
				return orderByProcessor.getOrderTypes();
			}
			return null;
		}

		public int[] getOrderByIndexs() {
			if (orderByProcessor != null) {
				return orderByProcessor.getOrderByIndexs();
			}
			return null;
		}

		public boolean next() throws SQLException {
			if (!isAfterLast) {
				return resultSet.next();
			}
			return false;

		}

		public boolean previous() throws SQLException {
			if (!isBeforeFirst) {
				return resultSet.previous();
			}
			return false;
		}

		public OrderByValues getOrderByValuesFromResultSet()
				throws SQLException {
			orderByProcessor.setValues(resultSet);
			return orderByProcessor.getValueCache();
		}

		public OrderByValues getValueCache() {
			return orderByProcessor.getValueCache();
		}

		public void setValueCache(OrderByValues valueCache) {
			orderByProcessor.setValueCache(valueCache);
		}

		public boolean isAfterLast() {
			return isAfterLast;
		}

		public void setAfterLast(boolean isAfterLast) {
			this.isAfterLast = isAfterLast;
		}

		public boolean isBeforeFirst() {
			return isBeforeFirst;
		}

		public void setBeforeFirst(boolean isBeforeFirst) {
			this.isBeforeFirst = isBeforeFirst;
		}

		public void beforeFirst() throws SQLException {
			resultSet.beforeFirst();
			orderByProcessor.clearValueCache();
			isBeforeFirst = true;
			isAfterLast = false;
		}

		public void afterLast() throws SQLException {
			resultSet.afterLast();
			orderByProcessor.clearValueCache();
			isAfterLast = true;
			isBeforeFirst = false;
		}

		public void first() throws SQLException {
			resultSet.first();
			orderByProcessor.clearValueCache();
			isAfterLast = false;
			isBeforeFirst = false;
		}

		public void last() throws SQLException {
			resultSet.last();
			orderByProcessor.clearValueCache();
			isAfterLast = false;
			isBeforeFirst = false;
		}

		public Shard getShard() {
			return shard;
		}

		public String getOriginalSql() {
			return originalSql;
		}

	}

}
