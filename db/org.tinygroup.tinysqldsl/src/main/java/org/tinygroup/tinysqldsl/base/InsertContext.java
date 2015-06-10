package org.tinygroup.tinysqldsl.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.CaseInsensitiveMap;
import org.tinygroup.tinysqldsl.expression.relational.ExpressionList;
import org.tinygroup.tinysqldsl.insert.InsertBody;

/**
 * 插入操作的上下文
 * @author renhui
 *
 */
public class InsertContext {

	private Map<String, Object> params=new CaseInsensitiveMap();
	
	private List<Object> paramValues=new ArrayList<Object>();
	
	private List<String> columnNames=new ArrayList<String>();
	
	private List<Column> columns=new ArrayList<Column>();
	
	private ExpressionList itemsList = new ExpressionList();
	
	private SelectBody selectBody;
	
	private Table table;
	
	private String schema;
	
	private String tableName;
	
	private boolean useValues = true;
	
	private List<Value> values=new ArrayList<Value>();
	
	public InsertContext() {
		super();
	}
	
	public void setTable(Table table){
	   this.table=table;
	}
	
	public Table getTable() {
		return table;
	}

	public void addValues(Value... values){
		for (Value value : values) {
			Column column = value.getColumn();
			columns.add(column);
			addColumnName(column.getColumnName());
			putParam(column.getColumnName(), value.getValue());
			itemsList.addExpression(value.getExpression());
			this.values.add(value);
		}
	}
	
	public Object[] getParamValues(){
		return paramValues.toArray();
	}
	
	public Map<String, Object> getParams() {
		return params;
	}
	
	public void setUseValues(boolean useValues) {
		this.useValues = useValues;
	}

	private void putParam(String columnName,Object value){
		params.put(columnName, value);
		paramValues.add(value);
	}

	public List<String> getColumnNames() {
		return columnNames;
	}

	private void addColumnName(String columnName){
		columnNames.add(columnName);
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	public void setSelectBody(SelectBody selectBody) {
		this.selectBody = selectBody;
	}
	
	public boolean existParam(String columnName){
		return params.containsKey(columnName);
	}
	
	public Object getParamValue(String columnName){
		return params.get(columnName);
	}
	
	public InsertContext copyContext(){
		InsertContext context=new InsertContext();
		context.setSchema(schema);
		context.setTable(table);
		context.setTableName(tableName);
		context.setUseValues(true);
		context.addValues(values.toArray(new Value[0]));
		return context;
	}
	

	public InsertBody createInsert(){
		InsertBody insertBody=new InsertBody();
		insertBody.setTable(table);
		insertBody.setColumns(columns);
		if(useValues){
			insertBody.setItemsList(itemsList);
		}else{
			insertBody.setSelectBody(selectBody);
		}
		return insertBody;
	}

	public void setColumns(List<Column> columns) {
		this.columns=columns;
	}

	public void setItemsList(ExpressionList itemsList) {
		this.itemsList=itemsList;
	}
}
