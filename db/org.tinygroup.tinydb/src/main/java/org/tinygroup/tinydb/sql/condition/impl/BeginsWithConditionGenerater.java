package org.tinygroup.tinydb.sql.condition.impl;

import java.util.List;

/**
 * between and 操作
 * @author renhui
 *
 */
public class BeginsWithConditionGenerater extends AbstractConditionGenerater {

	public String generateCondition(String columnName) {
		return generateCondition(columnName, " like ");
	}

	public void paramValueProcess(List<Object> params) {
		params.add(value.toString()+"%");
	}

	public String getConditionMode() {
		return "beginsWith";
	}

}
