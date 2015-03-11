package org.tinygroup.tinydb.sql.condition.impl;

import java.util.List;

import org.tinygroup.tinydb.sql.condition.ConditionGenerater;

public abstract class AbstractConditionGenerater implements ConditionGenerater {

	public String generateCondition(String columnName,String symbol) {
		return columnName + " " + symbol + " ? ";
	}

	public void paramValueProcess(Object value, List<Object> params) {
		params.add(value);
	}

}
