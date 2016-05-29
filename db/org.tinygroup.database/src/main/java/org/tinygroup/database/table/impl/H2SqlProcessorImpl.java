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

import org.tinygroup.database.config.table.Table;
import org.tinygroup.database.table.TableSqlProcessor;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class H2SqlProcessorImpl extends SqlProcessorImpl {
	
	private static TableSqlProcessor tableSqlProcessor=new H2SqlProcessorImpl();
	
	public static TableSqlProcessor getTableSqlProcessor(){
		tableSqlProcessor.setTableProcessor(TableProcessorImpl.getTableProcessor());
		return tableSqlProcessor;
	}

	protected String getDatabaseType() {
		return "h2";
	}

	protected String createNotNullSql(String tableName, String fieldName,
			String tableDataType) {
		return String.format("ALTER TABLE %s ALTER COLUMN %s %s NOT NULL",
				tableName, fieldName,tableDataType);
	}

	protected String createNullSql(String tableName, String fieldName,
			String tableDataType) {
		return String.format("ALTER TABLE %s ALTER COLUMN %s %s NULL", tableName,
				fieldName,tableDataType);
	}

	protected String createAlterTypeSql(String tableName, String fieldName,
			String tableDataType) {
		return String.format("ALTER TABLE %s ALTER COLUMN %s SET DATA TYPE %s",
				tableName, fieldName, tableDataType);
	}

	protected void appendComment(String comment, StringBuffer ddlBuffer,List<String> list) {
		//do nothing
	}

	/**
	 * 支持identity和auto_increment
	 * 这里选用auto_increment
	 * @return
     */
	protected String appendIncrease() {
		return " auto_increment ";
	}

	/**
	 * @param ddlBuffer
	 * @param table
	 * @param list
     */
	protected void appendFooter(StringBuffer ddlBuffer, Table table, List<String> list) {
		super.appendFooter(ddlBuffer, table,list);
		appendFooterComment(ddlBuffer, table,list);
	}

	public boolean checkTableExist(Table table, Connection connection)
			throws SQLException {
		ResultSet resultset = null;
		DatabaseMetaData metadata = connection.getMetaData();
		try {
			//h2数据库支持多schema,但是要先手动创建,而且connection无法获取url中schema参数
			//暂时简化,不支持除public外的其他schema
			String schema = null;
			resultset = metadata.getTables(connection.getCatalog(), schema,
					table.getNameWithOutSchema(), new String[] { "TABLE" });
			if (resultset.next()) {
				return true;
			} else {
				resultset.close();// 关闭上次打开的
				resultset = metadata.getTables(connection.getCatalog(), schema,
						table.getNameWithOutSchema().toUpperCase(),
						new String[] { "TABLE" });
				if (resultset.next()) {
					return true;
				} else if(schema!=null){
					resultset.close();
					resultset = metadata.getTables(connection.getCatalog(),
							schema.toUpperCase(), table.getNameWithOutSchema()
									.toUpperCase(), new String[] { "TABLE" });
					if (resultset.next()) {
						return true;
					}
				}
			}
		} finally {
			if (resultset != null) {
				resultset.close();
			}
		}
		return false;
	}

}
