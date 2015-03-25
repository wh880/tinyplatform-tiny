package org.tinygroup.tinydbdsl.impl;

import javax.sql.DataSource;

import org.tinygroup.tinydbdsl.Delete;
import org.tinygroup.tinydbdsl.DslSqlExecute;
import org.tinygroup.tinydbdsl.Insert;
import org.tinygroup.tinydbdsl.Update;

/**
 * 简单的sql执行器
 * 
 * @author renhui
 * 
 */
public class SimpleDslSqlExecute extends AbstractDslSqlOperater implements DslSqlExecute {

	public SimpleDslSqlExecute(DataSource dataSource, Insert insert) {
		super(dataSource, insert);
	}

	public SimpleDslSqlExecute(DataSource dataSource, Update update) {
		super(dataSource, update);
	}

	public SimpleDslSqlExecute(DataSource dataSource, Delete delete) {
		super(dataSource, delete);
	}

	public int execute() {
		return jdbcTemplate.update(sql, values.toArray());
	}

}
