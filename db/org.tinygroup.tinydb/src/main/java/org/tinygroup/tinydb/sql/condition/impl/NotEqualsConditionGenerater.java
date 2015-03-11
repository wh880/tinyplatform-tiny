package org.tinygroup.tinydb.sql.condition.impl;


/**
 * 不相等操作
 * @author renhui
 *
 */
public class NotEqualsConditionGenerater extends AbstractConditionGenerater {


	public String generateCondition(String columnName) {
		return generateCondition(columnName, "!=");
	}

	public String getConditionMode() {
		return "notEquals";
	}

}
