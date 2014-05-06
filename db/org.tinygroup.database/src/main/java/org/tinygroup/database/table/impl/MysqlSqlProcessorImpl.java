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

import java.sql.DatabaseMetaData;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.tinygroup.database.config.table.Table;
import org.tinygroup.database.config.table.TableField;
import org.tinygroup.metadata.config.stdfield.StandardField;
import org.tinygroup.metadata.util.MetadataUtil;

public class MysqlSqlProcessorImpl extends SqlProcessorImpl {

	protected String getDatabaseType() {
		return "mysql";
	}

	String appendIncrease() {
		return " auto_increment ";
	}
	protected List<String> dealExistFields(Map<String, TableField> existInTable,Map<String, Map<String, String>> dbColumns, Table table){	
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
				existUpdateList.add( String.format("ALTER TABLE %s CHANGE %s %s %s", table.getName(),fieldName, fieldName,tableDataType));
			}
			
//			StandardField standardField = MetadataUtil.getStandardField(field
//					.getStandardFieldId());
//			//如果数据库中字段允许为空，但table中不允许为空
			if(field.getNotNull()&&
					Integer.parseInt( attribute.get(NULLABLE))==DatabaseMetaData.columnNullable ){
				existUpdateList.add(String.format("ALTER TABLE %s CHANGE %s %s %s NOT NULL",
						table.getName(), fieldName, fieldName,tableDataType));
			}else if(!field.getNotNull()&&
					Integer.parseInt(attribute.get(NULLABLE))==DatabaseMetaData.columnNoNulls ){
				existUpdateList.add(String.format("ALTER TABLE %s CHANGE %s %s %s NULL",
						table.getName(), fieldName, fieldName,tableDataType));
			}
			
		}
		return existUpdateList;
	}
	
	protected List<String> checkTableColumn(
			Map<String, Map<String, String>> columns,
			Map<String, TableField> fieldDbNames,
			Map<String, TableField> existInTable) {
		List<String> dropFields = new ArrayList<String>();
		for (String colum : columns.keySet()) {
			// 遍历当前表格所有列
			// 若存在于map，则不处理，切从map中删除该key
			// 若不存在于map，则从表格中删除该列
			String temp = colum.toUpperCase();
			if (fieldDbNames.containsKey(temp)) {
				existInTable.put(temp, fieldDbNames.get(temp));
				fieldDbNames.remove(temp);
				continue;
			}
			dropFields.add(colum);
		}
		return dropFields;
	}
}
