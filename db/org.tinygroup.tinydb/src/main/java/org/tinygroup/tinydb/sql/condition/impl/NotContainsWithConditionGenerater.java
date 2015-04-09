package org.tinygroup.tinydb.sql.condition.impl;

import java.util.List;

/**
 * 不包含操作
 * 
 * @author renhui
 * 
 */
public class NotContainsWithConditionGenerater extends
		AbstractConditionGenerater {

	public String generateCondition(String columnName) {
		return generateCondition(columnName, " not like ");
	}

	public String getConditionMode() {
		return "notContainsWith";
	}

	@Override
	public void paramValueProcess(List<Object> params) {
		params.add("%" + value.toString() + "%");
	}

}
