package org.tinygroup.tinydb.sql.condition.impl;

import java.util.List;

import org.tinygroup.commons.tools.ArrayUtil;

/**
 * not between and 操作
 * @author renhui
 *
 */
public class NotBetweenAndConditionGenerater extends AbstractConditionGenerater {

	public String generateCondition(String columnName) {
		return columnName+" not between ? and ? ";
	}

	public void paramValueProcess(List<Object> params) {
		Object[] values=(Object[])value;
	    if(!ArrayUtil.isEmptyArray(values)){
	    	for (Object param : values) {
	    		params.add(param);
			}
	    }
	}

	public String getConditionMode() {
		return "notBetweenAnd";
	}

}
