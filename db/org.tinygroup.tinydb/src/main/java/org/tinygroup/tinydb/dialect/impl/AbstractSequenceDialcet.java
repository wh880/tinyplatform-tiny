package org.tinygroup.tinydb.dialect.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.JdbcUtils;

public abstract class AbstractSequenceDialcet extends AbstractDialect {
	/**
	 * Executes the SQL as specified by {@link #getSequenceQuery()}.
	 */
	public int getNextKey() throws DataAccessException {
		Connection con = DataSourceUtils.getConnection(getDataSource());
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.createStatement();
			DataSourceUtils.applyTransactionTimeout(stmt, getDataSource());
			rs = stmt.executeQuery(getSequenceQuery());
			if (rs.next()) {
				return rs.getInt(1);
			}
			else {
				throw new DataAccessResourceFailureException("Sequence query did not return a result");
			}
		}
		catch (SQLException ex) {
			throw new DataAccessResourceFailureException("Could not obtain sequence value", ex);
		}
		finally {
			JdbcUtils.closeResultSet(rs);
			JdbcUtils.closeStatement(stmt);
			DataSourceUtils.releaseConnection(con, getDataSource());
		}
	}

	/**
	 * Return the database-specific query to use for retrieving a sequence value.
	 * <p>The provided SQL is supposed to result in a single row with a single
	 * column that allows for extracting a <code>long</code> value.
	 */
	protected abstract String getSequenceQuery();

}
