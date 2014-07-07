package org.tinygroup.database.view.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.tinygroup.commons.tools.CollectionUtil;
import org.tinygroup.commons.tools.StringUtil;
import org.tinygroup.database.config.table.Table;
import org.tinygroup.database.config.table.TableField;
import org.tinygroup.database.config.view.GroupByField;
import org.tinygroup.database.config.view.Having;
import org.tinygroup.database.config.view.OrderByField;
import org.tinygroup.database.config.view.View;
import org.tinygroup.database.config.view.ViewCondition;
import org.tinygroup.database.config.view.ViewField;
import org.tinygroup.database.config.view.ViewFieldRef;
import org.tinygroup.database.config.view.ViewHaving;
import org.tinygroup.database.config.view.ViewTable;
import org.tinygroup.database.util.DataBaseUtil;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.metadata.config.stdfield.StandardField;
import org.tinygroup.metadata.util.MetadataUtil;

/**
 * 生成view 创建、删除语句
 * 
 * @author renhui
 * 
 */
public class ViewSqlCreator {

	private View view;
	
	private Map<String, String> tableNames=new HashMap<String, String>();
	
	private Map<String, Table> viewTables=new HashMap<String, Table>();
	
	private Map<String, String> fieldNames=new HashMap<String, String>();
	
	private Map<String, TableField> tableFields=new HashMap<String, TableField>();
	
	private Map<String, String> fieldId2Name=new HashMap<String, String>();
	
	private Logger logger=LoggerFactory.getLogger(ViewSqlCreator.class);

	public ViewSqlCreator(View view) {
		this.view = view;
		List<ViewTable> tables=view.getTableList();
		for (ViewTable viewTable : tables) {
			String tableAlias = viewTable.getTableAlias();
			String tableId = viewTable.getTableId();
			String tableName=getViewTableName(tableId);
			if(!StringUtil.isBlank(tableAlias)){
				tableName=tableAlias;
			}
			tableNames.put(viewTable.getId(), tableName);
			Table table= DataBaseUtil.getTableById(tableId);
			viewTables.put(viewTable.getId(), table);
		}
		List<ViewField> fields=view.getFieldList();
		for (ViewField viewField : fields) {
			String viewTableId = viewField.getViewTable();//
			TableField tableField =getTableField(viewField);
			StandardField tableFieldStd = MetadataUtil.getStandardField(tableField.getStandardFieldId());
			String tableFieldName = DataBaseUtil.getDataBaseName( tableFieldStd.getName());
			String tableName=tableNames.get(viewTableId);
			fieldNames.put(viewField.getId(), tableName+"."+tableFieldName);
			tableFields.put(viewField.getId(), tableField);
			fieldId2Name.put(viewField.getTableFieldId(), tableFieldName);
		}
		
		
	}
	
	public TableField getTableField(ViewField field){
	  Table table=viewTables.get(field.getViewTable());
	  if(table!=null){
		  return getTableField(field.getTableFieldId(), table);
	  }else{
           View view=DataBaseUtil.getViewById(field.getTableFieldId());	  
		   ViewField viewField=view.getViewField(field.getTableFieldId());
		   return getTableField(viewField);
	  }
	}
	
	private String getViewTableName(String viewTableId){
		Table table= DataBaseUtil.getTableById(viewTableId);
		if(table!=null){
			 return table.getName();
		}else{
			View view=DataBaseUtil.getViewById(viewTableId);
			if (view == null) {
				throw new RuntimeException(String.format(
						"视图[id:%s]不存在,", viewTableId));
			}
			return view.getName();
		}
		
	}
	
	
	public String getCreateSql(){
		
		StringBuffer buffer=new StringBuffer();
		
		appendHead(view.getName(), buffer);
		
		appendFields(buffer);
		
		appendTables(buffer);
		
		appendCondition(buffer);
		
		appendGroupBy(buffer);
		
		appendHaving(buffer);
		
		appendOrderBy(buffer);
		
		buffer.append(";");
		
		logger.logMessage(LogLevel.DEBUG, "新建视图sql:{1}",buffer.toString());
		
		return buffer.toString();
		
		
	}
	
	private void appendOrderBy(StringBuffer buffer) {
		List<OrderByField> orderByFields=view.getOrderByFieldList();
		if (CollectionUtil.isEmpty(orderByFields)) {
			return;
		}

		StringBuffer orderByFieldStr = new StringBuffer();
		for (int i = 0; i < orderByFields.size(); i++) {
			OrderByField field=orderByFields.get(i);
			ViewFieldRef viewFieldRef = field.getField();
			
			String fieldName = getViewFieldRefName(viewFieldRef);
			orderByFieldStr.append(fieldName).append(" ").append(field.getDirection());
			if(i<orderByFields.size()-1){
				orderByFieldStr.append(",");
			}
		}
		buffer.append(" ORDER BY ");
		buffer.append(orderByFieldStr.toString());
	}


	private void appendHaving(StringBuffer buffer) {
		List<GroupByField> groupByFields=view.getGroupByFieldList();
		List<ViewHaving> havingList=view.getHavingList();
		if (CollectionUtil.isEmpty(groupByFields)) {
 			if(!CollectionUtil.isEmpty(havingList)){
                 logger.logMessage(LogLevel.ERROR, "只有存在groupby语句的情况下才能使用having语句");				
			}
			return;
		}
		buffer.append(" HAVING ");
		String havingStr = dealHavingList(havingList);
		buffer.append(havingStr);
	}


	private String dealHavingList(List<ViewHaving> havingList) {
		if (CollectionUtil.isEmpty(havingList)){
			return "";
		}
		StringBuffer havingBuffer=new StringBuffer();
		for (int i = 0; i < havingList.size(); i++) {
			ViewHaving having=havingList.get(i);
			String havingStr=getHaving(having);
			havingBuffer.append(havingStr);
			if(i<havingList.size()-1){
				havingBuffer.append(" AND ");
			}
		}
		return havingBuffer.toString();
	}

	private String getHaving(ViewHaving having) {
		String havingValue = "";
		if (having.getValueHaving() == null) {
			havingValue = having.getValue();
		} else {
			Having valueHaving = having.getValueHaving();
			havingValue = parseHaving(valueHaving);
		}
		String havingKey = parseHaving(having.getKeyHaving());
		return String.format(" %s %s %s ", havingKey, having.getOperator(),
				havingValue);
	}

	private String parseHaving(Having valueHaving) {
		ViewFieldRef field = valueHaving.getField();
		String fieldStr = getViewFieldRefName(field);
		String function = valueHaving.getAggregateFunction();
		String havingStr = "";
		if(function==null||"".equals(function)){
			havingStr = fieldStr;
		}else{
			havingStr = String.format(" %s( %s ) ", function,fieldStr);
		}
		return havingStr;
	}


	private void appendGroupBy(StringBuffer buffer) {
		List<GroupByField> groupByFields=view.getGroupByFieldList();
		if (CollectionUtil.isEmpty(groupByFields)) {
			return;
		}
		StringBuffer groupByStr=new StringBuffer();
		groupByStr.append(" GROUP BY ");
		for (int i = 0; i < groupByFields.size(); i++) {
			GroupByField field=groupByFields.get(i);
			if (field.getField() == null) {
				continue;
			}
			ViewFieldRef viewField = field.getField();
			groupByStr.append(getViewFieldRefName(viewField));
			if(i<groupByFields.size()-1){
				groupByStr.append(",");
			}
			
		}
		buffer.append(groupByStr.toString());
	}


	private void appendCondition(StringBuffer buffer) {
		if (view.getConditionList() == null
				|| view.getConditionList().size() == 0)
			return;
		buffer.append(" WHERE ");
		List<ViewCondition> conditions=view.getConditionList();
		String conditionStr =dealCondtionList(conditions);
		buffer.append(conditionStr);
	}


	private String dealCondtionList(List<ViewCondition> conditions) {
		
		if (CollectionUtil.isEmpty(conditions)) {
			return "";
		}
		StringBuffer conditionBuffer=new StringBuffer();
		for (int i = 0; i < conditions.size(); i++) {
			ViewCondition condition=conditions.get(i);
			String subCondition=getCondition(condition);
			conditionBuffer.append(subCondition);
			if(i<conditions.size()-1){
				conditionBuffer.append(" AND ");
			}
		}
		return conditionBuffer.toString();
	}

	private String getCondition(ViewCondition condition) {
		String conditionValue = "";
		if (condition.getValueField() == null) { //如果条件字段为空，则读取条件配置的固定值
			conditionValue = condition.getValue();
		} else {
			ViewFieldRef valueField = condition.getValueField();
			if( valueField.getViewFieldId() == null){
				conditionValue  = getViewFieldRefName(valueField);
			}else{
				conditionValue = fieldNames.get(valueField.getViewFieldId());
			}
		}
		String fieldName = "";
		ViewFieldRef keyField = condition.getKeyField();
		if( keyField.getViewFieldId() == null){
			fieldName  = getViewFieldRefName(keyField);
		}else{
			fieldName = fieldNames.get(keyField.getViewFieldId());
		}
		String conditionStr = fieldName + condition.getOperator()
				+ conditionValue;
		String subCondtionStr = dealCondtionList(condition.getConditionList());
		if ("".equals(subCondtionStr))
			return conditionStr;
		if (condition.getConditionList().size() > 1) {
			return String
					.format(" %s OR ( %s ) ", conditionStr, subCondtionStr);
		} else {
			return String.format(" %s OR %s ", conditionStr, subCondtionStr);
		}
	}
	

	private String getViewFieldRefName(ViewFieldRef valueField) {
		if( valueField.getViewFieldId() == null){
			String tableName = tableNames.get(valueField.getViewTableId()); //获取表格对应的表明
			String fieldName = fieldId2Name.get(valueField.getTableFieldId());
			return tableName+"."+fieldName;
		}else{
			return fieldNames.get(valueField.getViewFieldId());
		}
	}


	private void appendTables(StringBuffer buffer) {
		buffer.append(" FROM ");
		List<ViewTable> viewTables=view.getTableList();
		for (int i = 0; i < viewTables.size(); i++) {
			ViewTable viewTable=viewTables.get(i);
			String tableAlias = viewTable.getTableAlias();
			String tableId = viewTable.getTableId();
			String tableName = DataBaseUtil.getTableById(tableId).getName();
			if(StringUtil.isBlank(tableAlias)){
				buffer.append(tableName);
			}else{
				buffer.append(tableName).append(" ").append(tableAlias);
			}
			if(i<viewTables.size()-1){
				buffer.append(",");
			}
		}
		
	}


	private void appendFields(StringBuffer buffer) {
		buffer.append(" SELECT ");
		List<ViewField> fields=view.getFieldList();
		for (int i = 0; i < fields.size(); i++) {
			ViewField field=fields.get(i);
			String fieldAlias = field.getAlias();
			String tableFieldName = fieldNames.get(field.getId());
			String fieldName = "";
			if (StringUtil.isBlank(fieldAlias)) {
				fieldName = tableFieldName;
			} else {
				fieldName = tableFieldName + " AS "
						+ fieldAlias;
			}
			buffer.append(fieldName);
			if(i<fields.size()-1){
				buffer.append(",");
			}
		}
		
	}
	
	private TableField getTableField(String fieldId,Table table){
		for(TableField field:table.getFieldList()){
			if(field.getId().equals(fieldId))
				return field;
		}
		return null;
	}

	public String getDropSql(){
		return String.format("DROP VIEW %s", view.getName());
	}
	
	private void appendHead(String viewName, StringBuffer buffer) {
		buffer.append("CREATE OR REPLACE VIEW ");
		buffer.append(viewName).append(" ");
		buffer.append("AS ");
	}

}
