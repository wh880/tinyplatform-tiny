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

import org.tinygroup.commons.tools.StringUtil;
import org.tinygroup.database.config.table.Index;
import org.tinygroup.database.config.table.Table;
import org.tinygroup.database.config.table.TableField;
import org.tinygroup.database.table.TableSqlProcessor;
import org.tinygroup.database.util.DataBaseUtil;
import org.tinygroup.metadata.config.stdfield.StandardField;
import org.tinygroup.metadata.util.MetadataUtil;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class MysqlSqlProcessorImpl extends SqlProcessorImpl {
	
	private static TableSqlProcessor tableSqlProcessor=new MysqlSqlProcessorImpl();
	
	public static TableSqlProcessor getTableSqlProcessor(){
		tableSqlProcessor.setTableProcessor(TableProcessorImpl.getTableProcessor());
		return tableSqlProcessor;
	}

	protected String getDatabaseType() {
		return "mysql";
	}

	protected String appendIncrease() {
		return " auto_increment ";
	}

	public boolean checkTableExist(Table table, String catalog,
			DatabaseMetaData metadata) {
		ResultSet r = null;
		try {
			String schema = table.getSchema();
			r = metadata.getTables(catalog, schema,
					table.getNameWithOutSchema(), new String[] { "TABLE" });

			if (r.next()) {
				return true;
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			DataBaseUtil.closeResultSet(r);
		}

		return false;
	}


	protected String getQueryForeignSql(Table table,String schema) {
		 String sql = "SELECT c.COLUMN_NAME, tc.CONSTRAINT_NAME,fc.REFERENCED_TABLE_NAME,kcu.REFERENCED_COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS c"
				+ " LEFT JOIN INFORMATION_SCHEMA.KEY_COLUMN_USAGE kcu ON kcu.TABLE_SCHEMA = c.TABLE_SCHEMA"
				+ " AND kcu.TABLE_NAME = c.TABLE_NAME"
				+ " AND kcu.COLUMN_NAME = c.COLUMN_NAME"
				+ " LEFT JOIN INFORMATION_SCHEMA.TABLE_CONSTRAINTS tc ON tc.CONSTRAINT_SCHEMA = kcu.CONSTRAINT_SCHEMA"
				+ " AND tc.CONSTRAINT_NAME = kcu.CONSTRAINT_NAME"
				+ " LEFT JOIN INFORMATION_SCHEMA.REFERENTIAL_CONSTRAINTS fc ON kcu.CONSTRAINT_SCHEMA = fc.CONSTRAINT_SCHEMA"
				+ " AND kcu.CONSTRAINT_NAME = fc.CONSTRAINT_NAME"
				+ " where tc.CONSTRAINT_TYPE='FOREIGN KEY' and c.table_name='"
				+ table.getName()
				+ "'";
		if(schema!=null && schema.trim().length()>0) {
			sql += " and c.table_schema='"
					+ schema + "'";
		}
		 return sql;
	}

	protected String createNotNullSql(String tableName, String fieldName,String tableDataType) {
		return String.format(
				"ALTER TABLE %s CHANGE %s %s %s NOT NULL",
				tableName, fieldName, fieldName, tableDataType);
	}

	protected String createNullSql(String tableName, String fieldName,String tableDataType) {
		return String.format(
				"ALTER TABLE %s CHANGE %s %s %s NULL", tableName,
				fieldName, fieldName, tableDataType);
	}

	protected String createAlterTypeSql(String tableName, String fieldName,
			String tableDataType) {
		return String.format(
				"ALTER TABLE %s CHANGE %s %s %s ", tableName,
				fieldName, fieldName, tableDataType);
	}

	protected boolean checkTypeSame(String dbColumnType, String tableDataType) {
		//对tinyint特殊处理,由于tinyint在metadata中都是tiny(3,0),故返回true
		if(dbColumnType.startsWith("tinyint")
				&& tableDataType.replaceAll(" ", "")
				.toLowerCase().startsWith("tinyint")){
			return true;
		}
		return super.checkTypeSame(dbColumnType,tableDataType);
	}
	
	@Override
    protected String getIndexName(Index index, Table table) {
        return index.getName();
    }

	/**
	 * 在footer增加comment
	 * mysql 实现为空
	 * @param table
	 * @param list
	 */
	protected void appendFooterComment(Table table, List<String> list) {}
}