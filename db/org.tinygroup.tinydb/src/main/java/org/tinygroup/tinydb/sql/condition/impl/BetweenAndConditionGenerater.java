/**
 *  Copyright (c) 1997-2013, www.tinygroup.org (tinygroup@126.com).
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

import java.util.List;

/**
 * between and 操作
 * @author renhui
 *
 */
public class BetweenAndConditionGenerater extends AbstractConditionGenerater {

	public String generateCondition(String columnName) {
		return columnName+" between ? and ? ";
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
		return "betweenAnd";
	}

}
