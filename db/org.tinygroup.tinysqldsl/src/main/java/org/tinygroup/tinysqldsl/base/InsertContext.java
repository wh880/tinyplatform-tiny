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
package org.tinygroup.tinysqldsl.base;

import org.apache.commons.collections.map.CaseInsensitiveMap;
import org.tinygroup.tinysqldsl.expression.Expression;
import org.tinygroup.tinysqldsl.expression.relational.ExpressionList;
import org.tinygroup.tinysqldsl.insert.InsertBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
			Object paramValue=value.getValue();
			Expression expression=value.getExpression();
			if(expression instanceof NamedCondition){
				 paramValue=((NamedCondition)expression).getValue();
			}
			if(paramValue!=null){
				Column column = value.getColumn();
				columns.add(column);
				addColumnName(column.getColumnName());
				putParam(column.getColumnName(), paramValue);
				itemsList.addExpression(expression);
				this.values.add(value);
			}
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
	
	public String[] getColumnNameArray() {
		return columnNames.toArray(new String[0]);
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
