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
package org.tinygroup.database.table;

import org.tinygroup.database.config.table.Table;
import org.tinygroup.database.config.table.Tables;

import java.sql.DatabaseMetaData;
import java.util.List;

/**
 * 与数据表相关的处理
 * 
 * @author luoguo
 * 
 */
public interface TableProcessor {
	String BEAN_NAME="tableProcessor";

	void addTables(Tables tables);
	
	void removeTables(Tables tables);
	
	void addTable(Table table);
	
	void removeTable(Table table);

	Table getTable(String packageName, String name);

	Table getTable(String name);

	Table getTableById(String id);

	List<Table> getTables();

	List<String> getCreateSql(String name, String packageName, String language);

	List<String> getCreateSql(String name, String language);

	List<String> getCreateSql(Table table, String packageName, String language);

	List<String> getCreateSql(Table table, String language);

	List<String> getUpdateSql(String name, String packageName,
			DatabaseMetaData metadata,String catalog, String language);

	List<String> getUpdateSql(Table table, String packageName,
			DatabaseMetaData metadata,String catalog, String language);

	String getDropSql(String name, String packageName, String language);

	String getDropSql(Table table, String packageName, String language);

	boolean checkTableExist(Table table, String catalog,
			DatabaseMetaData metadata, String language);

}
