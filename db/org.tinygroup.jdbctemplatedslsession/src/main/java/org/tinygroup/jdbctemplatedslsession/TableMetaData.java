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
package org.tinygroup.jdbctemplatedslsession;

import org.tinygroup.commons.tools.ArrayUtil;
import org.tinygroup.commons.tools.CollectionUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 表格元数据对象
 * 
 * @author renhui
 * 
 */
public class TableMetaData {

	private String catalogName;
	private String schemaName;
	private String tableName;
	private String[] keyNames;

	private List<ColumnMetaData> columnMetaDatas = new ArrayList<ColumnMetaData>();

	public String getCatalogName() {
		return catalogName;
	}

	public void setCatalogName(String catalogName) {
		this.catalogName = catalogName;
	}

	public String getSchemaName() {
		return schemaName;
	}

	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String[] getKeyNames() {
		return keyNames;
	}

	public void setKeyNames(String[] keyNames) {
		this.keyNames = keyNames;
	}

	public List<ColumnMetaData> getColumnMetaDatas() {
		return columnMetaDatas;
	}

	public void setColumnMetaDatas(List<ColumnMetaData> columnMetaDatas) {
		this.columnMetaDatas = columnMetaDatas;
	}

	public boolean isKeyName(String columnName) {
		if (!ArrayUtil.isEmptyArray(keyNames)) {
			for (String keyName : keyNames) {
				if (keyName.equalsIgnoreCase(columnName)) {
					return true;
				}
			}
		}
		return false;
	}

	public Object getDefaultValue(String columnName) {
		if (!CollectionUtil.isEmpty(columnMetaDatas)) {
			for (ColumnMetaData columnMetaData : columnMetaDatas) {
				if (columnMetaData.getParameterName().equalsIgnoreCase(
						columnName)) {
					return columnMetaData.getDefaultValue();
				}
			}
		}
		return null;
	}

}
