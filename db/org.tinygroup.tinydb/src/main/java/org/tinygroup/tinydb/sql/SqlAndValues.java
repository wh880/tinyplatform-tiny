package org.tinygroup.tinydb.sql;

import java.util.List;


/**
 * 封装sql和对应的参数值
 * @author renhui
 *
 */
public class SqlAndValues {

	private String sql;
	
	private List<Object> values;
	
	public SqlAndValues(String sql, List<Object> values) {
		super();
		this.sql = sql;
		this.values = values;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public List<Object> getValues() {
		return values;
	}

	public void setValues(List<Object> values) {
		this.values = values;
	}
}
