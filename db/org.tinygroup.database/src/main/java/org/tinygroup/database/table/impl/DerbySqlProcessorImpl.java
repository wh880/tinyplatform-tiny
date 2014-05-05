/**
 *  Copyright (c) 1997-2013, tinygroup.org (luo_guo@live.cn).
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
 * --------------------------------------------------------------------------
 *  版权 (c) 1997-2013, tinygroup.org (luo_guo@live.cn).
 *
 *  本开源软件遵循 GPL 3.0 协议;
 *  如果您不遵循此协议，则不被允许使用此文件。
 *  你可以从下面的地址获取完整的协议文本
 *
 *       http://www.gnu.org/licenses/gpl.html
 */
package org.tinygroup.database.table.impl;

import org.tinygroup.database.config.table.Table;
import org.tinygroup.database.config.table.TableField;
import org.tinygroup.metadata.config.stdfield.StandardField;
import org.tinygroup.metadata.util.MetadataUtil;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DerbySqlProcessorImpl extends SqlProcessorImpl {

	protected String getDatabaseType() {
		return "derby";
	}

	String appendIncrease() {
		return " auto_increment ";
	}
	
	public boolean checkTableExist(Table table, String catalog,
			DatabaseMetaData metadata) {

		try {
			String schema = table.getSchema();
			if(schema == null ||"".equals(schema)){
				schema = metadata.getUserName();
			}
			ResultSet r = metadata.getTables(catalog, schema.toUpperCase(),
					"%", new String[] { "TABLE" });
			
//			ResultSet r = metadata.getTables(catalog, schema,schema.toUpperCase()+"."+table.getNameWithOutSchema()
//					DataBaseNameUtil.getTableNameFormat(table
//							.getNameWithOutSchema()), new String[] { "TABLE" });
			
			while (r.next()) {
				String tablename = r.getString("TABLE_NAME");
				if(table.getNameWithOutSchema().toUpperCase().equals(tablename)){
					return true;
				}
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return false;
	}

	
	protected List<String> dealExistFields(
			Map<String, TableField> existInTable,
			Map<String, Map<String, String>> dbColumns, Table table) {
		List<String>  existUpdateList = new ArrayList<String>();
		for(String fieldName:existInTable.keySet()){
			TableField field = existInTable.get(fieldName);
			if(field.getPrimary()){
				continue;
			}
			StandardField standardField = MetadataUtil.getStandardField(field
					.getStandardFieldId());
			Map<String, String> attribute = dbColumns.get(fieldName);
			String tableDataType = MetadataUtil.getStandardFieldType(
					standardField.getId(), getDatabaseType());
			String dbColumnType = getDbColumnType(attribute).replaceAll(" ", "").toLowerCase();
			if(dbColumnType.indexOf(tableDataType.replaceAll(" ", "").toLowerCase())==-1){
				existUpdateList.add( String.format("ALTER TABLE %s ALTER %s SET DATA TYPE %s", table.getName(),fieldName,tableDataType));
			}
			
//			StandardField standardField = MetadataUtil.getStandardField(field
//					.getStandardFieldId());
//			//如果数据库中字段允许为空，但table中不允许为空
			if(field.getNotNull()&&
					Integer.parseInt( attribute.get(NULLABLE))==DatabaseMetaData.columnNullable ){
				existUpdateList.add(String.format("ALTER TABLE %s ALTER  %s NOT NULL",
						table.getName(), fieldName));
			}else if(!field.getNotNull()&&
					Integer.parseInt(attribute.get(NULLABLE))==DatabaseMetaData.columnNoNulls){
				existUpdateList.add(String.format("ALTER TABLE %s ALTER  %s NULL",
						table.getName(), fieldName));
			}
			
		}
		return existUpdateList;
	}

}
