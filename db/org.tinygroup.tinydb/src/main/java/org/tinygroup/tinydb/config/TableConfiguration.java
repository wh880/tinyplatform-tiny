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
package org.tinygroup.tinydb.config;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.tinygroup.tinydb.exception.TinyDbRuntimeException;

/**
 * 表信息
 * 
 * @author luoguo
 * 
 */
public class TableConfiguration implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 474364262682757321L;
	/**
	 * 表名
	 */
	private String name;
	
	private String schema;
	/**
	 * 字段信息列表
	 */
	private List<ColumnConfiguration> columns = new ArrayList<ColumnConfiguration>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ColumnConfiguration> getColumns() {
		return columns;
	}

	public void setColumns(List<ColumnConfiguration> columns) {
		this.columns = columns;
	}
	

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public void setPrimaryKey(String columnName) {
		boolean flag = false;
		for (ColumnConfiguration column : columns) {
			if (column.getColumnName().equals(columnName.toUpperCase())) {
				column.setPrimaryKey(true);
				flag = true;
				break;
			}
		}
		if (!flag) {
			throw new TinyDbRuntimeException("表格主键字段" + columnName + "不存在");
		}
	}

	public ColumnConfiguration getPrimaryKey() {
		for (ColumnConfiguration column : columns) {
			if (column.isPrimaryKey()) {
				return column;
			}
		}
		throw new TinyDbRuntimeException("找不到主键字段！");
	}
	
	public void addColumn(ColumnConfiguration column){
		columns.add(column);
	}
	
	public void removeColumn(ColumnConfiguration column){
		columns.remove(column);
	}

}
