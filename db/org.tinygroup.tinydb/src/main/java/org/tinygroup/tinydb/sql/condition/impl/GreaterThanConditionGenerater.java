package org.tinygroup.tinydb.sql.condition.impl;


/**
 * >操作
 * @author renhui
 *
 */
public class GreaterThanConditionGenerater extends AbstractConditionGenerater {

	public String generateCondition(String columnName) {
		return generateCondition(columnName, ">");
	}

	public String getConditionMode() {
		return "greaterThan";
	}

}
