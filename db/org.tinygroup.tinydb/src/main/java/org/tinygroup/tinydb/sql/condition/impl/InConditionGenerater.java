package org.tinygroup.tinydb.sql.condition.impl;

import org.tinygroup.commons.tools.ArrayUtil;

import java.util.Collections;
import java.util.List;


/**
 * in 操作
 * @author renhui
 *
 */
public class InConditionGenerater extends AbstractConditionGenerater {

	public String generateCondition(String columnName) {
		Object[] values=ArrayUtil.toArray(value);
		if(!ArrayUtil.isEmptyArray(values)){
			StringBuilder builder=new StringBuilder(columnName);
			builder.append(" in (");
			for (int i = 0; i < values.length; i++) {
				 builder.append("?");
				 if(i<values.length-1){
					 builder.append(",");
				 }
			}
			builder.append(")");
			return builder.toString();
		}
		throw new RuntimeException("InConditionGenerater条件的值不能为null");
		
	}

	public String getConditionMode() {
		return "in";
	}

	@Override
	public void paramValueProcess(List<Object> params) {
		Object[] values=ArrayUtil.toArray(value);
		Collections.addAll(params, values);
	}
	
}
