package org.tinygroup.tinydb.sql.condition.impl;


/**
 * 相等操作
 * @author renhui
 *
 */
public class EqualsConditionGenerater extends AbstractConditionGenerater {


	public String generateCondition(String columnName) {
		return generateCondition(columnName, "=");
	}

	public String getConditionMode() {
		return "equals";
	}

}
