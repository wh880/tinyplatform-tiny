package org.tinygroup.tinydb.sql.condition.impl;

import java.util.List;

import org.tinygroup.commons.tools.ArrayUtil;

/**
 * between and 操作
 * @author renhui
 *
 */
public class BetweenAndConditionGenerater extends AbstractConditionGenerater {

	public String generateCondition(String columnName) {
		return columnName+" between ? and ? ";
	}

	public void paramValueProcess(Object value,List<Object> params) {
		Object[] values=(Object[])value;
	    if(!ArrayUtil.isEmptyArray(values)){
	    	for (Object param : values) {
	    		params.add(param);
			}
	    }
	}

	public String getConditionMode() {
		return "betweenAnd";
	}

}
