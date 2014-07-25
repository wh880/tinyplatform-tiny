/**
 *  Copyright (c) 1997-2013, www.tinygroup.org (luo_guo@icloud.com).
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
 */
package org.tinygroup.tinydb.operator.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameterValue;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.tinydb.Bean;
import org.tinygroup.tinydb.BeanDbNameConverter;
import org.tinygroup.tinydb.dialect.Dialect;
import org.tinygroup.tinydb.exception.DBRuntimeException;
import org.tinygroup.tinydb.impl.DefaultNameConverter;
import org.tinygroup.tinydb.jdbctemplate.BatchPreparedStatementSetterImpl;
import org.tinygroup.tinydb.jdbctemplate.SqlParamValuesBatchStatementSetterImpl;
import org.tinygroup.tinydb.jdbctemplate.TinydbResultExtractor;
import org.tinygroup.tinydb.operator.TransactionCallBack;
import org.tinygroup.tinydb.operator.TransactionOperator;

public class DBSpringBaseOperator implements TransactionOperator {

	private JdbcTemplate jdbcTemplate;
	private Dialect dialect;
	private TransactionStatus status;
	private PlatformTransactionManager transactionManager;
	private TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
	private Logger logger = LoggerFactory.getLogger(DBSpringBaseOperator.class);

	protected BeanDbNameConverter beanDbNameConverter = new DefaultNameConverter();

	public BeanDbNameConverter getBeanDbNameConverter() {
		return beanDbNameConverter;
	}

	public void setBeanDbNameConverter(BeanDbNameConverter beanDbNameConverter) {
		this.beanDbNameConverter = beanDbNameConverter;
	}

	public Dialect getDialect() {
		return dialect;
	}

	public void setDialect(Dialect dialect) {
		this.dialect = dialect;
	}

	public int account(String sql) throws SQLException {
		int ret = 0;
		SqlRowSet sqlRowset = jdbcTemplate.queryForRowSet(sql);
		if (sqlRowset.next()) {
			ret = sqlRowset.getInt(1);
		}
		return ret;
	}

	public DBSpringBaseOperator(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public int executeByList(String sql, List<Object> parameters,
			List<Integer> dataTypes) {
		int[] types = getDataTypes(dataTypes);
		if (types != null && types.length > 0) {
			return jdbcTemplate.update(sql, parameters.toArray(), types);
		} else {
			return jdbcTemplate.update(sql, parameters.toArray());
		}

	}

	public int executeBySqlParameterValues(String sql,
			SqlParameterValue[] values) {
		return jdbcTemplate.update(sql, values);
	}

	public int executeBySqlParameterValue(String sql, SqlParameterValue value) {
		SqlParameterValue[] values = new SqlParameterValue[1];
		values[0] = value;
		return jdbcTemplate.update(sql, values);
	}

	/**
	 * 
	 * 根据参数中sql语句进行批处理操作
	 * 
	 * @param sql
	 * @param parameters
	 * @param dataTypes
	 *            key代表未赋参数值的sql语句 value代表sql语句中的参数列表信息
	 */
	protected int[] executeBatchByList(String sql,
			List<List<Object>> parameters, List<Integer> dataTypes) {
		return jdbcTemplate.batchUpdate(sql,
				new BatchPreparedStatementSetterImpl(parameters, dataTypes));
	}

	/**
	 * 
	 * 根据参数中sql语句进行批处理操作
	 * 
	 * @param sql
	 * @param parameters
	 *            key代表未赋参数值的sql语句 value代表sql语句中的参数列表信息
	 */
	protected int[] executeBatchBySqlParamterValues(String sql,
			List<SqlParameterValue[]> parameters) {
		return jdbcTemplate.batchUpdate(sql,
				new SqlParamValuesBatchStatementSetterImpl(parameters));
	}
	
	public List<Bean> findBeansForPage(String sql, String beanType,
			String schema, int start, int limit){
		if (supportsLimit()) {
			return findBeansForDialectPage(sql, beanType, schema, start,
					limit);
		} else {
			return findBeansForCursorPage(sql, beanType, schema, start,
					limit);
		}
		
	}
	@SuppressWarnings("unchecked")
	protected List<Bean> findBeansForCursorPage(String sql, String beanType,
			String schema, int start, int limit) {
		List<Bean> beans = (List<Bean>) jdbcTemplate.query(sql,
				new TinydbResultExtractor(beanType, schema, start,
				limit, beanDbNameConverter));
		return beans;
	}
	
	@SuppressWarnings("unchecked")
	protected List<Bean> findBeansForDialectPage(String sql, String beanType,
			String schema, int start, int limit) {
		String tempSql = sql;
		tempSql = getLimitString(sql, start, limit);
		List<Bean> beans = (List<Bean>) jdbcTemplate.query(tempSql,new TinydbResultExtractor(beanType, schema,
				beanDbNameConverter));
		return beans;
	}

	public List<Bean> findBeansByListForPage(String sql, String beanType,
			String schema, int start, int limit, List<Object> parameters) {
		if (supportsLimit()) {
			return findBeansByListForDialectPage(sql, beanType, schema, start,
					limit, parameters);
		} else {
			return findBeansByListForCursorPage(sql, beanType, schema, start,
					limit, parameters);
		}
	}

	@SuppressWarnings("unchecked")
	protected List<Bean> findBeansByListForDialectPage(String sql,
			String beanType, String schema, int start, int limit,
			List<Object> parameters) {
		String tempSql = sql;
		tempSql = getLimitString(sql, start, limit);
		List<Bean> beans = (List<Bean>) jdbcTemplate.query(tempSql, parameters
				.toArray(), new TinydbResultExtractor(beanType, schema,
				beanDbNameConverter));
		return beans;
	}

	@SuppressWarnings("unchecked")
	protected List<Bean> findBeansByListForCursorPage(String sql, String beanType,
			String schema, int start, int limit, List<Object> parameters) {
		List<Bean> beans = (List<Bean>) jdbcTemplate.query(sql, parameters
				.toArray(), new TinydbResultExtractor(beanType, schema, start,
				limit, beanDbNameConverter));
		return beans;
	}

	public List<Bean> findBeansByMapForPage(String sql, String beanType,
			String schema, int start, int limit,
			Map<String, Object> parameters, List<Integer> dataTypes) {
		if (supportsLimit()) {
			return findBeansByMapForDialectPage(sql, beanType, schema, start,
					limit, parameters, dataTypes);
		} else {
			return findBeansByMapForCursorPage(sql, beanType, schema, start,
					limit, parameters, dataTypes);
		}
	}

	protected List<Bean> findBeansByMapForDialectPage(String sql, String beanType,
			String schema, int start, int limit,
			Map<String, Object> parameters, List<Integer> dataTypes) {
		String tempSql = sql;
		tempSql = getLimitString(sql, start, limit);
		return findBeansByMap(tempSql, beanType, schema, parameters, dataTypes);
	}

	@SuppressWarnings("unchecked")
	protected List<Bean> findBeansByMapForCursorPage(String sql, String beanType,
			String schema, int start, int limit,
			Map<String, Object> parameters, List<Integer> dataTypes) {
		StringBuffer buf = new StringBuffer();
		List<Object> paraList = getParamArray(sql, parameters, buf);
		int[] types = getDataTypes(dataTypes);
		if (types != null && types.length > 0) {
			return (List<Bean>) jdbcTemplate.query(buf.toString(), paraList
					.toArray(), types, new TinydbResultExtractor(beanType,
					schema, start, limit, beanDbNameConverter));
		} else {
			return (List<Bean>) jdbcTemplate.query(buf.toString(), paraList
					.toArray(), new TinydbResultExtractor(beanType, schema,
					start, limit, beanDbNameConverter));
		}
	}

	@SuppressWarnings("unchecked")
	public List<Bean> findBeans(String sql, String beanType, String schema)
			throws SQLException {
		return (List<Bean>) jdbcTemplate.query(sql, new TinydbResultExtractor(
				beanType, schema, beanDbNameConverter));
	}

	@SuppressWarnings("unchecked")
	public List<Bean> findBeans(String sql, String beanType, String schema,
			Object... parameters) {
		return (List<Bean>) jdbcTemplate
				.query(sql, parameters, new TinydbResultExtractor(beanType,
						schema, beanDbNameConverter));
	}

	@SuppressWarnings("unchecked")
	public List<Bean> findBeansByList(String sql, String beanType,
			String schema, List<Object> parameters) {
		return (List<Bean>) jdbcTemplate
				.query(sql, parameters.toArray(), new TinydbResultExtractor(
						beanType, schema, beanDbNameConverter));
	}

	
	@SuppressWarnings("rawtypes")
	public Object queryObject(String sql, String beanType, String schema,
			Object... parameters) {
		List results = (List) jdbcTemplate
				.query(sql, parameters, new TinydbResultExtractor(beanType,
						schema, beanDbNameConverter));
		return DataAccessUtils.requiredSingleResult(results);
	}

	private boolean supportsLimit() {
		if(dialect==null){
			return false;
		}
		return dialect.supportsLimit();
	}

	private String getLimitString(String sql, int start, int limit) {
		return dialect.getLimitString(sql, start, limit);
	}

	@SuppressWarnings("unchecked")
	public List<Bean> findBeansByMap(String sql, String beanType,
			String schema, Map<String, Object> parameters,
			List<Integer> dataTypes) {
		StringBuffer buf = new StringBuffer();
		List<Object> paraList = getParamArray(sql, parameters, buf);
		int[] types = getDataTypes(dataTypes);
		if (types != null && types.length > 0) {
			return (List<Bean>) jdbcTemplate.query(buf.toString(), paraList
					.toArray(), types, new TinydbResultExtractor(beanType,
					schema, beanDbNameConverter));
		} else {
			return (List<Bean>) jdbcTemplate.query(buf.toString(), paraList
					.toArray(), new TinydbResultExtractor(beanType, schema,
					beanDbNameConverter));
		}

	}

	@SuppressWarnings("rawtypes")
	public Object queryObjectByMap(String sql, String beanType, String schema,
			Map<String, Object> parameters, List<Integer> dataTypes) {
		StringBuffer buf = new StringBuffer();
		List<Object> paraList = getParamArray(sql, parameters, buf);
		int[] types = getDataTypes(dataTypes);
		if (types != null && types.length > 0) {
			List results = (List) jdbcTemplate.query(buf.toString(), paraList
					.toArray(), types, new TinydbResultExtractor(beanType,
					schema, beanDbNameConverter));
			return DataAccessUtils.requiredSingleResult(results);
		} else {
			List results = (List) jdbcTemplate.query(buf.toString(), paraList
					.toArray(), new TinydbResultExtractor(beanType, schema,
					beanDbNameConverter));
			return DataAccessUtils.requiredSingleResult(results);
		}

	}

	public int executeByMap(String sql, Map<String, Object> parameters,
			List<Integer> dataTypes) {
		StringBuffer buf = new StringBuffer();
		List<Object> paraList = getParamArray(sql, parameters, buf);
		int[] types = getDataTypes(dataTypes);
		if (types != null && types.length > 0) {
			return jdbcTemplate.update(sql, paraList.toArray(), types);
		} else {
			return jdbcTemplate.update(sql, paraList.toArray());
		}
	}

	public int executeByArray(String sql, Object... parameters) {
		return jdbcTemplate.update(sql, parameters);
	}

	public int queryForInt(String sql, Object... parameters) {
		return jdbcTemplate.queryForInt(sql, parameters);
	}

	public int queryForIntByList(String sql, List<Object> parameters) {
		return jdbcTemplate.queryForInt(sql, parameters.toArray());
	}

	public int queryForIntByMap(String sql, Map<String, Object> parameters) {
		StringBuffer buf = new StringBuffer();
		List<Object> paraList = getParamArray(sql, parameters, buf);
		return jdbcTemplate.queryForInt(sql, paraList.toArray());
	}

	private int[] getDataTypes(List<Integer> dataTypes) {
		int[] types = null;
		if (dataTypes != null) {
			types = new int[dataTypes.size()];
			for (int i = 0; i < dataTypes.size(); i++) {
				types[i] = dataTypes.get(i);
			}
		}
		return types;
	}

	private List<Object> getParamArray(String sql,
			Map<String, Object> parameters, StringBuffer buf) {
		ArrayList<Object> paraList = new ArrayList<Object>();
		String patternStr = "([\"](.*?)[\"])|([\'](.*?)[\'])|([@][a-zA-Z_$][\\w$]*)";
		Pattern pattern = Pattern.compile(patternStr);
		Matcher matcher = pattern.matcher(sql);
		int curpos = 0;
		while (matcher.find()) {
			String replaceStr = matcher.group();
			String variable = replaceStr.substring(1, replaceStr.length());
			if (!replaceStr.startsWith("\"") && !replaceStr.startsWith("'")
					&& parameters != null && parameters.containsKey(variable)) {
				buf.append(sql.substring(curpos, matcher.start()));
				curpos = matcher.end();
				paraList.add(parameters.get(variable));
				buf.append("?");
			}
			continue;
		}
		buf.append(sql.substring(curpos));
		return paraList;
	}

	public void beginTransaction() {
		try {
			if (status == null || status.isCompleted()) {
				status = this.getTransactionManager().getTransaction(
						transactionDefinition);
				if (status.isNewTransaction()) {
					logger.logMessage(LogLevel.INFO, "新开启一个事务");
				} else {
					logger.logMessage(LogLevel.INFO, "未开启新事务，将使用之前的事务");
				}

			}
		} catch (Exception e) {
			throw new DBRuntimeException(e);
		}

	}

	public void commitTransaction() {
		if (status != null && !status.isCompleted()) {
			try {
				this.getTransactionManager().commit(status);
			} catch (Exception e) {
				throw new DBRuntimeException(e);
			}
		}
	}

	public void rollbackTransaction() {
		if (status != null && !status.isCompleted()) {
			try {
				this.getTransactionManager().rollback(status);
			} catch (Exception e) {
				throw new DBRuntimeException(e);
			}
		}
	}

	public PlatformTransactionManager getTransactionManager() {
		if (transactionManager == null) {
			transactionManager = new DataSourceTransactionManager(
					jdbcTemplate.getDataSource());
		}
		return transactionManager;
	}

	public void setTransactionManager(
			PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

	public TransactionDefinition getTransactionDefinition() {
		return transactionDefinition;
	}

	public void setTransactionDefinition(
			TransactionDefinition transactionDefinition) {
		this.transactionDefinition = transactionDefinition;
	}

	public Object execute(TransactionCallBack callback) {
		TransactionStatus status = this.getTransactionManager().getTransaction(
				transactionDefinition);
		Object result = null;
		try {
			result = callback.callBack(status);
			this.getTransactionManager().commit(status);
			return result;
		} catch (Exception ex) {
			logger.errorMessage(ex.getMessage(), ex);
			this.getTransactionManager().rollback(status);
			throw new DBRuntimeException(ex);
		}
	}

}
