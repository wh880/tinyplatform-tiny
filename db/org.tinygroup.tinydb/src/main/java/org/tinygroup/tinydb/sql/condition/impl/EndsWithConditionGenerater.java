package org.tinygroup.tinydb.sql.condition.impl;

import java.util.List;

/**
 * ends with 操作
 * @author renhui
 *
 */
public class EndsWithConditionGenerater extends AbstractConditionGenerater {

	public String generateCondition(String columnName) {
		return generateCondition(columnName, " like ");
	}

	public void paramValueProcess(List<Object> params) {
		params.add("%"+value.toString());
	}

	public String getConditionMode() {
		return "endsWith";
	}

}
