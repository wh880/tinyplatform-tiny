package org.tinygroup.database.view.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.tinygroup.commons.tools.StringUtil;
import org.tinygroup.database.config.table.Table;
import org.tinygroup.database.config.table.TableField;
import org.tinygroup.database.config.view.View;
import org.tinygroup.database.config.view.ViewCondition;
import org.tinygroup.database.config.view.ViewField;
import org.tinygroup.database.config.view.ViewTable;
import org.tinygroup.database.util.DataBaseUtil;
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

	public ViewSqlCreator(View view) {
		this.view = view;
		List<ViewTable> tables=view.getTableList();
		for (ViewTable viewTable : tables) {
			String tableAlias = viewTable.getTableAlias();
			String tableId = viewTable.getTableId();
			Table table= DataBaseUtil.getTableById(tableId);
			String tableName = table.getName();
			if(!StringUtil.isBlank(tableAlias)){
				tableName=tableAlias;
			}
			tableNames.put(viewTable.getId(), tableName);
			viewTables.put(viewTable.getId(), table);
		}
		List<ViewField> fields=view.getFieldList();
		for (ViewField viewField : fields) {
			String fieldAlias = viewField.getAlias();
			String viewTableId = viewField.getViewTable();//
			Table table=viewTables.get(viewTableId);
			TableField tableField = getTableField(viewField.getTableFieldId(), table);
			StandardField tableFieldStd = MetadataUtil.getStandardField(tableField.getStandardFieldId());
			String tableFieldName = DataBaseUtil.getDataBaseName( tableFieldStd.getName());
			if(!StringUtil.isBlank(fieldAlias)){
				tableFieldName=fieldAlias;
			}
			fieldNames.put(viewField.getId(), tableFieldName);
			tableFields.put(viewField.getId(), tableField);
		}
		
		
	}
	
	
	public String getCreateSql(){
		
		StringBuffer buffer=new StringBuffer();
		
		appendHead(view.getName(), buffer);
		
		appendFields(buffer);
		
		appendTables(buffer);
		
		appendCondition(buffer);
		
		
		return buffer.toString();
		
		
		
		
	}
	
	private void appendCondition(StringBuffer buffer) {
		if (view.getConditionList() == null
				|| view.getConditionList().size() == 0)
			return;
		buffer.append(" WHERE ");
		
		List<ViewCondition> conditions=view.getConditionList();
		for (int i = 0; i < conditions.size(); i++) {
			ViewCondition condition=conditions.get(i);
			
			
			
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
			String viewTableId = field.getViewTable();//
			String tableFieldName = fieldNames.get(field.getId());
			String fieldName = "";
			if (StringUtil.isBlank(fieldAlias)) {
				fieldName = tableNames.get(viewTableId) + "." + tableFieldName;
			} else {
				fieldName = tableNames.get(viewTableId) + "." + tableFieldName + " AS "
						+ fieldAlias;
			}
			buffer.append(fieldName);
			if(i<fields.size()-1){
				buffer.append(",");
			}
		}
		
	}
	private ViewTable getViewTable(String viewTableId,View view){
		for(ViewTable vt : view.getTableList()){
			if(vt.getId().equals(viewTableId))
				return vt;
		}
		return null;
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
