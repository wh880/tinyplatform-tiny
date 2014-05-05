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
package org.tinygroup.tinydb.impl;

import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.tinydb.BeanDbNameConverter;
import org.tinygroup.tinydb.BeanOperatorManager;
import org.tinygroup.tinydb.config.ColumnConfiguration;
import org.tinygroup.tinydb.config.SchemaConfig;
import org.tinygroup.tinydb.config.SchemaConfigContainer;
import org.tinygroup.tinydb.config.TableConfiguration;
import org.tinygroup.tinydb.exception.DBRuntimeException;
import org.tinygroup.tinydb.operator.DBOperator;
import org.tinygroup.tinydb.relation.Relation;
import org.tinygroup.tinydb.relation.Relations;
import org.tinygroup.tinydb.util.DataSourceFactory;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class BeanManagerImpl implements BeanOperatorManager {

	private SchemaConfigContainer schemaContainer  = new SchemaConfigContainer();
	private String mainSchema;
	/**
	 * Map<schema,Map<表名, TableConfiguration>>
	 */
	private Map<String, Map<String, TableConfiguration>> tableMap = new HashMap<String, Map<String, TableConfiguration>>();

	/**
	 * Map<schema,Map<beanType, java属性列表>>
	 */
	private Map<String, Map<String, List<String>>> propertiesMap = new HashMap<String, Map<String, List<String>>>();

	private String[] tableTypes = new String[] { "TABLE","VIEW" };// 只查询TABLE和VIEW类型的表

	private static Logger logger = LoggerFactory
			.getLogger(BeanManagerImpl.class);

	private BeanDbNameConverter nameConverter = new DefaultNameConverter();
	
	private Map<String, Relation> relationIdMap=new HashMap<String, Relation>();
	
	private Map<String, Relation> relationTypeMap=new HashMap<String, Relation>();

	/**
	 * 不以'_'开头，且不以'_'或者'_数字'结尾的表名
	 */
	private static Pattern tableNamePattern = Pattern
			.compile("^(?!_)(?!.*?(_[0-9]*)$)[a-zA-Z]+(_?[a-zA-Z0-9])+$");

	public DBOperator<?> getDbOperator(String ischema,String beanType) {
		DBOperator<?> operator = schemaContainer.getDbOperator(getRealSchema(ischema), beanType);
		operator.setRelation(getRelationByBeanType(beanType));
		operator.setManager(this);
		return operator;
	}

	public void registerSchemaConfig(SchemaConfig schemaConfig) {
		schemaContainer.addSchemaConfig(schemaConfig);
//		schemaMap.put(schemaConfig.getSchema(), schemaConfig.getBean());
//		schemaKeyTypeMap.put(schemaConfig.getSchema(), schemaConfig.getKeyType());
	}

	public TableConfiguration getTableConfiguration(String tableName,
			String schema) {
		String ischema = getRealSchema(schema);
		if (!tableMap.containsKey(ischema)
				|| !tableMap.get(ischema).containsKey(tableName)) {// 如果map中不包含该表格信息,则尝试初始化表格信息
			logger.logMessage(LogLevel.DEBUG, "表格:{0}不存在,尝试初始化表格信息", tableName);
			initBeanConfigration(null,
					nameConverter.dbTableNameToTypeName(tableName), ischema);
		}
		return tableMap.get(ischema).get(tableName);
	}

	public TableConfiguration getTableConfigurationByBean(String beanType,
			String schame) {
		String tableName = nameConverter.typeNameToDbTableName(beanType);
		return getTableConfiguration(tableName, schame);
	}

	public List<String> getBeanProperties(String beanType, String schema) {
		String ischema = getRealSchema(schema);
		if (!propertiesMap.containsKey(ischema)
				|| !propertiesMap.get(ischema).containsKey(beanType)) {
			logger.logMessage(LogLevel.DEBUG,
					"Bean:{0},schema:{1}属性列表不存在,尝试相关信息", beanType, ischema);
			initBeanConfigration(null, beanType, ischema);
		}
		return propertiesMap.get(ischema).get(beanType);
	}

	public void initBeansConfiguration() {
		Connection con = null;
		try {
			con = DataSourceFactory.getConnection(null);
			Map<String, List<String>> schemaBeanListMap = schemaContainer.getSchemaBeanListMap();
			for (String schame : schemaBeanListMap.keySet()) {
				for (String beanType : schemaBeanListMap.get(schame)) {
					initBeanConfigration(con, beanType, schame);
				}

			}
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					throw new DBRuntimeException(e);
				}
			}
		}

	}

	private void initBeanConfigration(Connection con, String beanType,
			String schema) {
		try {
			Connection c = con;
			if (c == null) {
				c = DataSourceFactory.getConnection(null);
			}
			String tableName = nameConverter.typeNameToDbTableName(beanType);
			initTableConfiguration(c, schema, tableName);
			initBeanProperties(beanType, schema);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private void initTableConfiguration(Connection con, String schema,
			String tableName) throws SQLException {
		logger.logMessage(LogLevel.INFO, "开始获取表格:{0}信息", tableName);
			
		
		if (tableMap.containsKey(tableName)){// 先判断map中是否已有表信息，若有则直接取出返回
			logger.logMessage(LogLevel.INFO, "表格:{0}已存在，无需重新获取", tableName);
			return;
		}
		DatabaseMetaData metaData = con.getMetaData();
		// 不对表名和schame作处理获取表信息
		TableConfiguration table = getTableConfiguration(tableName,
				metaData.getColumns(null, schema.trim(), tableName.trim(), "%"));
		ResultSet rset = metaData.getPrimaryKeys(null, schema.trim(),
				tableName.trim());
		if (table == null) {
			// 小写表名读取信息 with schema
			table = getTableConfiguration(tableName, metaData.getColumns(null,
					schema.trim().toLowerCase(),
					tableName.trim().toLowerCase(), "%"));
			rset.close();// 先关闭之前打开的
			rset = metaData.getPrimaryKeys(null, schema.trim().toLowerCase(),
					tableName.trim().toLowerCase());
		}
		if (table == null) {
			// 大写表名读取信息 with schema
			table = getTableConfiguration(tableName, metaData.getColumns(null,
					schema.trim().toUpperCase(),
					tableName.trim().toUpperCase(), "%"));
			rset.close();// 先关闭之前打开的
			rset = metaData.getPrimaryKeys(null, schema.trim().toUpperCase(),
					tableName.trim().toUpperCase());
		}
		if (table == null) {
			// 大写表名读取信息
			table = getTableConfiguration(tableName, metaData.getColumns(null,
					"%", tableName.trim().toUpperCase(), "%"));
			rset.close();// 先关闭之前打开的
			rset = metaData.getPrimaryKeys(null, "%", tableName.trim()
					.toUpperCase());
		}
		if (table == null) {
			// 小写写表名读取信息
			table = getTableConfiguration(tableName, metaData.getColumns(null,
					"%", tableName.trim().toLowerCase(), "%"));
			rset.close();// 先关闭之前打开的
			rset = metaData.getPrimaryKeys(null, "%", tableName.trim()
					.toLowerCase());
		}
		if (table != null) {
			// 若非空，则存入表格信息Map
			while (rset.next()) {
				table.setPrimaryKey(rset.getString(PK_NAME));
			}
			table.setSchema(schema);
			addTableConfiguration(table);
			logger.logMessage(LogLevel.INFO, "获取表格:{0}信息完成", tableName);
		} else {
			logger.logMessage(LogLevel.ERROR, "未能获取表格:{0}信息", tableName);
		}
		rset.close();// 关闭最后打开的

	}

	private void initBeanProperties(String beanType, String schema) {
		TableConfiguration tableConfig = getTableConfigurationByBean(beanType,
				schema);
		if (tableConfig == null) {
			logger.logMessage(LogLevel.ERROR, "表格不存在,无法初始化bean属性列表");
			return;
		}
		List<String> properties = new ArrayList<String>();
		for (ColumnConfiguration c : tableConfig.getColumns()) {
			String columnName = c.getColumnName();
			String propertyName = nameConverter
					.dbFieldNameToPropertyName(columnName);
			properties.add(propertyName);
		}
		if (!propertiesMap.containsKey(schema)) {
			propertiesMap.put(schema, new HashMap<String, List<String>>());
		}
		propertiesMap.get(schema).put(beanType, properties);
	}

	private static TableConfiguration getTableConfiguration(String tableName,
			ResultSet colRet) throws SQLException {
		try {
			TableConfiguration table = new TableConfiguration();
			boolean flag = false;
			while (colRet.next()) {
				flag = true;
				ColumnConfiguration column = new ColumnConfiguration();
				column.setColumnName(colRet.getString(COLUMN_NAME).toUpperCase());
				column.setColumnSize(colRet.getString(COLUMN_SIZE));
				column.setDecimalDigits(colRet.getString(DECIMAL_DIGITS));
				column.setAllowNull(colRet.getString(NULLABLE));
				column.setTypeName(colRet.getString(TYPE_NAME));
				column.setDataType(colRet.getInt(DATA_TYPE));
				table.getColumns().add(column);
			}
			if (!flag) {// 说明没有colRet中没有查到列信息
				return null;
			}
			table.setName(tableName);
			return table;
		} finally {
			colRet.close();
		}

	}

	public DBOperator<?> getDbOperator(String mainBean,
			Map<String, String> subBeanInfo, String schame) {

		return null;
	}

	public void loadTablesFromSchemas() {
		Connection con = null;
		try {
			logger.logMessage(LogLevel.INFO, "开始扫描schema中的所有表信息");
			for (String schema : schemaContainer.getSchemaList()) {
				logger.logMessage(LogLevel.INFO, "开始扫描schema：{0}", schema);
				con = DataSourceFactory.getConnection(null);
				try {
					initSchemaConfiguration(schema, con);
				} catch (SQLException e) {
					throw new DBRuntimeException(e);
				}
				logger.logMessage(LogLevel.INFO, "扫描schema结束：{0}", schema);
			}
			logger.logMessage(LogLevel.INFO, "扫描schema中的所有表信息结束");
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					throw new DBRuntimeException(e);
				}
			}
		}

	}

	private void registerBean(String beanType, String schema) {
		Map<String, List<String>> schemaBeanListMap = schemaContainer.getSchemaBeanListMap();
		if (!schemaBeanListMap.containsKey(schema)) {
			schemaBeanListMap.put(schema, new ArrayList<String>());
		}
		schemaBeanListMap.get(schema).add(beanType);
	}

	private void initSchemaConfiguration(String schema, Connection con)
			throws SQLException {
		DatabaseMetaData metaData = con.getMetaData();
		ResultSet tables = metaData.getTables("", schema.toUpperCase(), schemaContainer.getTablePattern(schema), tableTypes);
		while (tables.next()) {
			String tableName = tables.getString(TABLE_NAME);
			if (tableNamePattern.matcher(tableName).matches()) {
				String beanType = nameConverter
						.dbTableNameToTypeName(tableName);
				registerBean(beanType, schema);
			} else {
				logger.logMessage(LogLevel.ERROR, "表名：{0}不符合规范将被忽略", tableName);
			}
		}

	}

	public void setMainSchema(String schema) {
		this.mainSchema = schema;
	}

	public String getRealSchema(String schema) {
		if (schema == null || "".equals(schema)) {
			return mainSchema;
		}
		return schema;
	}

	public DBOperator<?> getDbOperator(String beanType) {
		return getDbOperator(mainSchema, beanType);
	}

	public TableConfiguration getTableConfiguration(String tableName) {
		return getTableConfiguration(tableName, mainSchema);
	}
	
	
	public Map<String, Map<String, TableConfiguration>> getTableConfigurations() {
		return tableMap;
	}

	public TableConfiguration getTableConfigurationByBean(String beanType) {
		return getTableConfigurationByBean(beanType, mainSchema);
	}

	public SchemaConfigContainer getSchemaContainer() {
		return schemaContainer;
	}

	public void addRelationConfigs(Relations relations) {
		for (Relation relation : relations.getRelations()) {
			relationIdMap.put(relation.getId(), relation);
			relationTypeMap.put(relation.getType(), relation);
		}
	}
	
	public void removeRelationConfigs(Relations relations) {
		for (Relation relation : relations.getRelations()) {
			relationIdMap.remove(relation.getId());
			relationTypeMap.remove(relation.getType());
		}
	}
	
	public Relation getRelationById(String id) {
		return relationIdMap.get(id);
	}
	
	public Relation getRelationByBeanType(String beanType) {
		return relationTypeMap.get(beanType);
	}

	public boolean existsTable(String beanType, String schema) {
		String tableName = nameConverter.typeNameToDbTableName(beanType);
		String ischema = getRealSchema(schema);
		if (!tableMap.containsKey(ischema)
				|| !tableMap.get(ischema).containsKey(tableName)) {
			throw new DBRuntimeException("tinydb.tableNotExist", beanType,
					ischema + "." + tableName);
		}
		return true;
	}

	public void addTableConfiguration(TableConfiguration configuration) {
		String schema=configuration.getSchema();
		if (!tableMap.containsKey(schema)) {
			tableMap.put(schema, new HashMap<String, TableConfiguration>());
		}
		tableMap.get(schema).put(configuration.getName(), configuration);
		
	}
}
