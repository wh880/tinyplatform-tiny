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
