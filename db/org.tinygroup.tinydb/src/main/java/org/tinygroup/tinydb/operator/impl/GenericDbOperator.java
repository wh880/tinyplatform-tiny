package org.tinygroup.tinydb.operator.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlRowSetResultSetExtractor;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.tinygroup.dynamicdatasource.ConnectionTrace;
import org.tinygroup.dynamicdatasource.DynamicDataSource;
import org.tinygroup.tinydb.exception.TinyDbException;
import org.tinygroup.tinydb.operator.DBOperator;

public class GenericDbOperator<K> extends BeanDBSqlQueryOperator<K> implements
		DBOperator<K> {
	

	public GenericDbOperator(JdbcTemplate jdbcTemplate) {
		super(jdbcTemplate);
	}

	public SqlRowSet getSqlRowSet(String sql) throws TinyDbException {
		try {
			operatorDebugLogNoParam(sql);
			return (SqlRowSet) jdbcTemplate.query(sql,
					new SqlRowSetResultSetExtractor());
		} catch (DataAccessException e) {
			operatorErrorLogNoParam(sql, e);
			throw new TinyDbException(e.getRootCause());
		}
	}

	public SqlRowSet getSqlRowSet(String sql, Object... parameters)
			throws TinyDbException {
		try {
			operatorDebugLog(sql, parameters);
			return (SqlRowSet) jdbcTemplate.query(sql, parameters,
					new SqlRowSetResultSetExtractor());
		} catch (DataAccessException e) {
			operatorErrorLog(sql, e, parameters);
			throw new TinyDbException(e.getRootCause());
		}
	}

	public SqlRowSet getSqlRowSet(String sql, List<Object> parameters)
			throws TinyDbException {
		Object[] params = checkNullParamList(parameters);
		try {
			operatorDebugLog(sql, params);
			return (SqlRowSet) jdbcTemplate.query(sql, params,
					new SqlRowSetResultSetExtractor());
		} catch (DataAccessException e) {
			operatorErrorLog(sql, e, params);
			throw new TinyDbException(e.getRootCause());
		}
	}

	public SqlRowSet getSqlRowSet(String sql, Map<String, Object> parameters)
			throws TinyDbException {
		StringBuffer buf=new StringBuffer();
		List<Object> paramList=getParamArray(sql, parameters, buf);
		Object[] params = checkNullParamList(paramList);
		String tempSql=buf.toString();
		try {
			operatorDebugLog(tempSql, params);
			return (SqlRowSet) jdbcTemplate.query(tempSql, params,
					new SqlRowSetResultSetExtractor());
		} catch (DataAccessException e) {
			operatorErrorLog(tempSql, e, params);
			throw new TinyDbException(e.getRootCause());
		}
	}

	public Collection<ConnectionTrace> queryAllActiveConnection() {
		DynamicDataSource dataSource=(DynamicDataSource)jdbcTemplate.getDataSource();
		return dataSource.getConnectionTraces();
	}
}
