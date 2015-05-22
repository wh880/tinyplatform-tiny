package org.tinygroup.tinysqldsl.base;

import java.util.ArrayList;

import java.util.List;

import java.util.HashMap;

import java.util.Map;

/**
 * 
 * @author renhui
 *
 */
public class InsertContext {

	private Map<String, Object> params=new HashMap<String, Object>();
	
	private List<String> columnNames=new ArrayList<String>();
	
	private String schema;
	
	private String tableName;

	public Map<String, Object> getParams() {
		return params;
	}

	public void putParam(String columnName,Object value){
		params.put(columnName, value);
	}

	public List<String> getColumnNames() {
		return columnNames;
	}

	public void addColumnName(String columnName){
		columnNames.add(columnName);
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
}
