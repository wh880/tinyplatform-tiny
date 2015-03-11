package org.tinygroup.tinydb.sql.condition.impl;

import java.util.List;

/**
 * 包含 操作
 * @author renhui
 *
 */
public class ContainsWithConditionGenerater extends AbstractConditionGenerater {

	public String generateCondition(String columnName) {
		return generateCondition(columnName, " like ");
	}

	public void paramValueProcess(Object value,List<Object> params) {
		params.add("%"+value.toString()+"%");
	}

	public String getConditionMode() {
		return "containsWith";
	}

}
