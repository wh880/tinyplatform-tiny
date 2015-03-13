package org.tinygroup.tinydb.sql.condition.impl;


/**
 * <=操作
 * @author renhui
 *
 */
public class LessOrEqualsConditionGenerater extends AbstractConditionGenerater {

	public String generateCondition(String columnName) {
		return generateCondition(columnName, "<=");
	}

	public String getConditionMode() {
		return "lessOrEquals";
	}

}
