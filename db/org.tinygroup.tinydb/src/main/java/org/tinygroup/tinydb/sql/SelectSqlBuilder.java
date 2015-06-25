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
package org.tinygroup.tinydb.sql;

import org.tinygroup.commons.tools.StringUtil;

public class SelectSqlBuilder {
	
	private StringBuffer select;

	public SelectSqlBuilder(StringBuffer select) {
		super();
		this.select = select;
	}
	

	public void appendSelectItem(String selectItem){
		if(StringUtil.isBlank(selectItem)){
			selectItem="*";
		}
		select.append("select ").append(selectItem).append(" from ");
	}
	
	public void appendTable(String tableName){
		select.append(tableName);
	}
	
	public void appendCondition(String conditionSql){
		if(!StringUtil.isBlank(conditionSql)){
			select.append(" where ").append(conditionSql);
		}
	}
	
	public void appendGroupBy(String groupSql){
		if(!StringUtil.isBlank(groupSql)){
			select.append(" group by ").append(groupSql);
		}
	}
	
	public void appendOrderBy(String orderBySql){
		if(!StringUtil.isBlank(orderBySql)){
			select.append(" order by ").append(orderBySql);
		}
	}
	
	public String toSelectSql(){
		return select.toString();
	}
}
