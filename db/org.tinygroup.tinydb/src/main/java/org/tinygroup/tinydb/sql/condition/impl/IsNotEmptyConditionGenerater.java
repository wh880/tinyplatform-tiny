package org.tinygroup.tinydb.sql.condition.impl;

import java.util.List;


/**
 * 非空字符串比较操作
 * @author renhui
 *
 */
public class IsNotEmptyConditionGenerater extends AbstractConditionGenerater {


	public String generateCondition(String columnName) {
		return columnName+"!=''";
	}

	public String getConditionMode() {
		return "isNotEmpty";
	}

	@Override
	public void paramValueProcess(List<Object> params) {//不用加入参数
	}
}
