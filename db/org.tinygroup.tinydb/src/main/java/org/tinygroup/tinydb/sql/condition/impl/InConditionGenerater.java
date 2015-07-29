/**
 *  Copyright (c) 1997-2013, www.tinygroup.org (luo_guo@icloud.com).
 *
 *  Licensed under the GPL, Version 3.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.gnu.org/licenses/gpl.html
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
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
