package org.tinygroup.tinydb;

import java.io.Serializable;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class Field implements Serializable {

	private String name;

	private int type;

	private int precision;

	private int scale;

	private int index;// 字段位置,1为第一个显示字段

	public Field() {
		super();
	}

	public Field(String name, int type, int precision, int scale,int index) {
		super();
		this.name = name;
		this.type = type;
		this.precision = precision;
		this.scale = scale;
		this.index=index;
	}

	public Field(String name, ResultSetMetaData metaData, int index)
			throws SQLException {
		this.name = name;
		this.precision = metaData.getPrecision(index);
		this.type = metaData.getColumnType(index);
		this.scale = metaData.getScale(index);
		this.index=index;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setType(int type) {
		this.type = type;
	}

	public void setPrecision(int precision) {
		this.precision = precision;
	}

	public void setScale(int scale) {
		this.scale = scale;
	}

	public String getName() {
		return name;
	}

	public int getType() {
		return type;
	}

	public int getPrecision() {
		return precision;
	}

	public int getScale() {
		return scale;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

}