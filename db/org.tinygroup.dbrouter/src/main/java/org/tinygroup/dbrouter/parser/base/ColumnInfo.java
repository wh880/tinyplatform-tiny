package org.tinygroup.dbrouter.parser.base;

import java.util.HashMap;
import java.util.Map;

import org.tinygroup.jsqlparser.schema.Column;

/**
 * 存储sql解析后相关的列信息
 * @author renhui
 *
 */
public class ColumnInfo {

	private String table;
	private String name;

	private Map<String, Object> attributes = new HashMap<String, Object>();

	public ColumnInfo() {

	}

	public ColumnInfo(String table, String name) {
		this.table = table;
		this.name = name;
	}
	
	public ColumnInfo(Column column){
		this.table=column.getTable().getName();
		this.name=column.getColumnName();
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Map<String, Object> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	public int hashCode() {
		int tableHashCode = table != null ? table.toLowerCase().hashCode() : 0;
		int nameHashCode = name != null ? name.toLowerCase().hashCode() : 0;
		return tableHashCode + nameHashCode;
	}

	public String toString() {
		if (table != null) {
			return table + "." + name;
		}

		return name;
	}

	public boolean equals(Object obj) {
		ColumnInfo column = (ColumnInfo) obj;

		if (table == null) {
			if (column.getTable() != null) {
				return false;
			}
		} else {
			if (!table.equalsIgnoreCase(column.getTable())) {
				return false;
			}
		}

		if (name == null) {
			if (column.getName() != null) {
				return false;
			}
		} else {
			if (!name.equalsIgnoreCase(column.getName())) {
				return false;
			}
		}
		
		return true;
	}
}