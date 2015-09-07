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
package org.tinygroup.databasebuinstaller.impl;

import org.tinygroup.database.config.table.Table;
import org.tinygroup.database.table.TableProcessor;
import org.tinygroup.logger.LogLevel;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 功能说明:数据库表新建、字段更新、删除操作
 * 
 * 开发人员: renhui <br>
 * 开发时间: 2013-8-15 <br>
 * <br>
 */
public class TableInstallProcessor extends AbstractInstallProcessor {

	private List<Table> tableList = new ArrayList<Table>();
	private TableProcessor tableProcessor;

	public TableProcessor getTableProcessor() {
		return tableProcessor;
	}

	public void setTableProcessor(TableProcessor tableProcessor) {
		this.tableProcessor = tableProcessor;
	}

	private void deal(String language, Table table, List<String> sqls,
			Connection connect,boolean isFull) throws SQLException {
		if (tableList.contains(table))
			return;
		tableList.add(table);
		installTable(language, table, sqls, connect,isFull);
	}

	private void installTable(String language, Table table, List<String> sqls,
			Connection connect,boolean isFull) throws SQLException {
		logger.logMessage(LogLevel.INFO, "开始生成表格语句,表格 包:{0},名:{1}",
				table.getPackageName(), table.getName());
		List<String> tableSqls = null;
		//非全量且表格已经存在,需要生成增量sql
		if(!isFull && tableProcessor.checkTableExist(table, language, connect)) {
			tableSqls = tableProcessor.getUpdateSql(table,
					table.getPackageName(), language, connect);
		} else {
			tableSqls = tableProcessor.getCreateSql(table,
					table.getPackageName(), language);
		}
		if (tableSqls.size() != 0) {
			logger.logMessage(LogLevel.INFO, "生成sql:{0}", tableSqls);
		} else {
			logger.logMessage(LogLevel.INFO, "无需生成Sql");
		}
		sqls.addAll(tableSqls);
		logger.logMessage(LogLevel.INFO, "生成表格语句完成,表格 包:{0},名:{1}",
				table.getPackageName(), table.getName());
	}
	
	private List<String> getSqls(String language, Connection con,boolean isFull)
			throws SQLException {
		logger.logMessage(LogLevel.INFO, "开始获取数据库表安装操作执行语句");
		List<Table> list = tableProcessor.getTables();
		List<String> sqls = new ArrayList<String>();
		for (Table table : list) {
			deal(language, table, sqls, con,isFull);
		}
		logger.logMessage(LogLevel.INFO, "获取数据库表安装操作执行语句结束");
		return sqls;
	}

	public int getOrder() {
		return HIGHEST_PRECEDENCE;
	}

	public List<String> getDealSqls(String language, Connection con)
			throws SQLException {
		return getUpdateSqls(language, con);
	}
	
	public List<String> getFullSqls(String language, Connection con) throws SQLException{
		return getSqls(language, con,true);
	}

	public List<String> getUpdateSqls(String language, Connection con)
			throws SQLException {
		return getSqls(language, con,false);
	}
	
}
