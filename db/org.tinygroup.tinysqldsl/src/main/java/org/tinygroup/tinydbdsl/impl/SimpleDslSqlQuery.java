package org.tinygroup.tinydbdsl.impl;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.tinygroup.tinydbdsl.ComplexSelect;
import org.tinygroup.tinydbdsl.DslSqlQuery;
import org.tinygroup.tinydbdsl.Select;

/**
 * 数据查询接口的实现
 * 
 * @author renhui
 * 
 * @param <T>
 */
public class SimpleDslSqlQuery extends AbstractDslSqlOperater implements
		DslSqlQuery {

	public SimpleDslSqlQuery(DataSource dataSource, Select select) {
		super(dataSource, select);
	}

	private SimpleDslSqlQuery(DataSource dataSource, ComplexSelect complexSelect) {
		super(dataSource, complexSelect);
	}

	@SuppressWarnings("unchecked")
	public <T> T fetchOneResult(Class<T> requiredType) {
		return (T) jdbcTemplate.queryForObject(sql, values.toArray(),
				new BeanPropertyRowMapper(requiredType));
	}

	@SuppressWarnings("unchecked")
	public <T> T[] fetchArray(Class<T> requiredType) {
		List<T> records = fetchList(requiredType);
		return (T[]) records.toArray();
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> fetchList(Class<T> requiredType) {
		return jdbcTemplate.query(sql, values.toArray(),
				new BeanPropertyRowMapper(requiredType));
	}

	public SqlRowSet fetchResult() {
		return jdbcTemplate.queryForRowSet(sql, values.toArray());
	}

}
