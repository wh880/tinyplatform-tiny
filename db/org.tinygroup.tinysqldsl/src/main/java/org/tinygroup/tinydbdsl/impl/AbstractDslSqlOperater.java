package org.tinygroup.tinydbdsl.impl;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.tinygroup.tinydbdsl.base.Statement;

public class AbstractDslSqlOperater{

	protected JdbcTemplate jdbcTemplate = new JdbcTemplate();
	
	protected String sql;
	
	protected List<Object> values;
	
	public AbstractDslSqlOperater(DataSource dataSource,Statement statement){
		jdbcTemplate.setDataSource(dataSource);
		this.sql=statement.sql();
		this.values=statement.getValues();
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public String getSql() {
		return sql;
	}

	public List<Object> getValues() {
		return values;
	}
}
