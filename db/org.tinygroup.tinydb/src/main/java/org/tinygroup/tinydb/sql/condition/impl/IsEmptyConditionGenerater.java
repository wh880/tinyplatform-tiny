package org.tinygroup.tinydb.sql.condition.impl;

import java.util.List;


/**
 * 是否空字符串比较操作
 * @author renhui
 *
 */
public class IsEmptyConditionGenerater extends AbstractConditionGenerater {


	public String generateCondition(String columnName) {
		return columnName+"=''";
	}

	public String getConditionMode() {
		return "isEmpty";
	}

	@Override
	public void paramValueProcess(List<Object> params) {//不用加入参数
	}
}
