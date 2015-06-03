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
package org.tinygroup.database.table.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.tinygroup.commons.tools.CollectionUtil;
import org.tinygroup.database.ProcessorManager;
import org.tinygroup.database.config.table.ForeignReference;
import org.tinygroup.database.config.table.Table;
import org.tinygroup.database.config.table.Tables;
import org.tinygroup.database.table.TableProcessor;
import org.tinygroup.database.table.TableSqlProcessor;
import org.tinygroup.metadata.util.MetadataUtil;

public class TableProcessorImpl implements TableProcessor {
	// 存储所有表信息
	private Map<String, Map<String, Table>> tableMap = new HashMap<String, Map<String, Table>>();
	private ProcessorManager processorManager;
	private List<Table> orderTables = new ArrayList<Table>();
	private Map<String, Table> idMap = new HashMap<String, Table>();
	private Map<String, Boolean> tableInited = new HashMap<String, Boolean>();

	public ProcessorManager getProcessorManager() {
		return processorManager;
	}

	public void setProcessorManager(ProcessorManager processorManager) {
		this.processorManager = processorManager;
	}

	public void addTables(Tables tables) {
		String packageName = MetadataUtil.passNull(tables.getPackageName());
		if (!tableMap.containsKey(packageName)) {
			tableMap.put(packageName, new HashMap<String, Table>());
		}
		for (Table table : tables.getTableList()) {
			addTable(table);
		}
	}

	public void removeTables(Tables tables) {
		String packageName = MetadataUtil.passNull(tables.getPackageName());
		Map<String, Table> nameMap = tableMap.get(packageName);
		if (!CollectionUtil.isEmpty(nameMap)) {
			for (Table table : tables.getTableList()) {
				removeTable(table);
			}
		}
	}

	public void addTable(Table table) {
		String packageName = MetadataUtil.passNull(table.getPackageName());
		if (!tableMap.containsKey(packageName)) {
			tableMap.put(packageName, new HashMap<String, Table>());
		}
		Map<String, Table> nameMap = tableMap.get(packageName);
		nameMap.put(table.getName(), table);
		idMap.put(table.getId(), table);
	}

	public void removeTable(Table table) {
		String packageName = MetadataUtil.passNull(table.getPackageName());
		Map<String, Table> nameMap = tableMap.get(packageName);
		if (!CollectionUtil.isEmpty(nameMap)) {
			nameMap.remove(table.getName());
		}
		idMap.remove(table.getId());
	}

	public Table getTable(String packageName, String name) {
		if (packageName != null) {
			Map<String, Table> packageMap = tableMap.get(packageName);
			if (packageMap != null) {
				Table table = packageMap.get(name);
				if (table != null) {
					return table;
				}
			}
		}
		for (String pkgName : tableMap.keySet()) {
			Map<String, Table> packageMap = tableMap.get(pkgName);
			if (packageMap != null) {
				Table table = packageMap.get(name);
				if (table != null) {
					return table;
				}
			}
		}
		throw new RuntimeException("未找到package:" + packageName + ",name:"
				+ name + "的表格");
	}

	public Table getTable(String name) {
		return getTable(null, name);
	}

	public List<String> getCreateSql(String name, String language) {
		return getCreateSql(name, null, language);
	}

	public List<String> getCreateSql(String name, String packageName,
			String language) {
		Table table = getTable(packageName, name);
		return getCreateSql(table, language);
	}

	public List<String> getCreateSql(Table table, String packageName,
			String language) {
		table.setPackageName(packageName);
		return getCreateSql(table, language);
	}

	public List<String> getCreateSql(Table table, String language) {
		TableSqlProcessor sqlProcessor = getSqlProcessor(language);
		return sqlProcessor.getCreateSql(table, table.getPackageName());
	}

	public Table getTableById(String id) {
		return idMap.get(id);
	}

	public List<Table> getTables() {
		if (!CollectionUtil.isEmpty(orderTables)) {
			return orderTables;
		}
		for (Table table : idMap.values()) {
			addOrderTable(table);
		}
		return orderTables;
	}

	private void addOrderTable(Table table) {
		List<ForeignReference> references = table.getForeignReferences();
		for (ForeignReference foreignReference : references) {
			Table foreignTable = idMap.get(foreignReference.getMainTable());
			addOrderTable(foreignTable);
		}
		Boolean inited = tableInited.get(table.getId());
		if (inited == null || inited.equals(Boolean.FALSE)) {
			orderTables.add(table);
			tableInited.put(table.getId(), true);
		}
	}

	public List<String> getUpdateSql(String name, String packageName,
			String language, Connection connection) throws SQLException {
		Table table = getTable(packageName, name);
		return getUpdateSql(table, packageName, language, connection);
	}

	public List<String> getUpdateSql(Table table, String packageName,
			String language, Connection connection) throws SQLException {
		TableSqlProcessor sqlProcessor = getSqlProcessor(language);
		return sqlProcessor.getUpdateSql(table, packageName, connection);
	}

	public String getDropSql(String name, String packageName, String language) {
		Table table = getTable(packageName, name);
		return getDropSql(table, packageName, language);
	}

	public String getDropSql(Table table, String packageName, String language) {
		TableSqlProcessor sqlProcessor = getSqlProcessor(language);
		return sqlProcessor.getDropSql(table, packageName);
	}

	public boolean checkTableExist(Table table, String language,
			Connection connection) throws SQLException {
		TableSqlProcessor sqlProcessor = getSqlProcessor(language);
		return sqlProcessor.checkTableExist(table, connection);
	}

	private TableSqlProcessor getSqlProcessor(String language) {
		TableSqlProcessor sqlProcessor = (TableSqlProcessor) processorManager
				.getProcessor(language, "table");
		return sqlProcessor;
	}

	public List<String> getCreateSqls(String language) {
		List<String> sqls = new ArrayList<String>();
		List<Table> tables = getTables();
		for (Table table : tables) {
			sqls.addAll(getCreateSql(table, language));
		}
		return sqls;
	}
}
