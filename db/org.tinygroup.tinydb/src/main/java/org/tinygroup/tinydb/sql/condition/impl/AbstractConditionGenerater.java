package org.tinygroup.tinydb.sql.condition.impl;

import java.util.List;

import org.tinygroup.tinydb.sql.condition.ConditionGenerater;

/**
 * 抽象的条件比较操作
 * @author renhui
 *
 */
public abstract class AbstractConditionGenerater implements ConditionGenerater {
	
	protected Object value;

	public String generateCondition(String columnName,String symbol) {
		return columnName + " " + symbol + " ? ";
	}

	public void paramValueProcess(List<Object> params) {
		params.add(value);
	}

	public void setValue(Object value) {
		this.value=value;
	}
}
