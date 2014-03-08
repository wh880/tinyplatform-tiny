package org.tinygroup.dbrouterjdbc3.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.tinygroup.dbrouter.config.Partition;
import org.tinygroup.dbrouter.config.Shard;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;

public class RealStatementExecutor {
	private Statement realStatement;
	private String executeSql;
	private String originalSql;
	private Shard shard;
	private Partition partition;
	private Logger logger = LoggerFactory.getLogger(TinyStatement.class);

	public RealStatementExecutor(Statement realStatement, String executeSql,
			String originalSql, Shard shard, Partition partition) {
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
		logger.logMessage(LogLevel.DEBUG, "{0}:{1}", shard.getId(), originalSql);
		if (realStatement instanceof PreparedStatement) {
			PreparedStatement prepared = (PreparedStatement) realStatement;
			return prepared.executeQuery();
		}
		logger.logMessage(LogLevel.DEBUG, "{0}:{1}", shard.getId(), executeSql);
		return realStatement.executeQuery(executeSql);

	}

	public int executeUpdate() throws SQLException {
		logger.logMessage(LogLevel.DEBUG, "{0}:{1}", shard.getId(), originalSql);
		if (realStatement instanceof PreparedStatement) {
			PreparedStatement prepared = (PreparedStatement) realStatement;
			return prepared.executeUpdate();
		}
		logger.logMessage(LogLevel.DEBUG, "{0}:{1}", shard.getId(), executeSql);
		return realStatement.executeUpdate(executeSql);
	}
}
