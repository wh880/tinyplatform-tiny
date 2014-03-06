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
package org.tinygroup.tinydb.util;


public class DialectUtil{

	/* 数据库类型 */
	public static final String DB_TYPE_ORACLE = "oracle";
	public static final String DB_TYPE_DB2 = "db2";
	public static final String DB_TYPE_MYSQL = "mysql";
	public static final String DB_TYPE_SQLSERVER = "sqlserver";
	public static final String DB_TYPE_INFORMIX = "informix";
	public static final String DB_TYPE_SYBASE = "sybase";
	/**
	 * 按规则组合序列名称。序列命名要符合"seq_" + tableName的规范
	 * 
	 * @param param
	 * @return
	 */
	public static String getSeqName(String param) {
		return "seq_" + param;
	}
	
	/**
	 * 生成克隆sql.
	 * 
	 * @param sql
	 *            the sql
	 * @return the clone sql
	 * @return
	 */
	public static String getCloneSql(String sql) {
		byte[] tempBytes = new byte[sql.getBytes().length];
		System
				.arraycopy(sql.getBytes(), 0, tempBytes, 0,
						sql.getBytes().length);
		String tempSql = new String(tempBytes);
		return tempSql;
	}
	
	public static boolean hasDistinct(String sql) {
		return sql.indexOf("select distinct") >= 0;
	}
	
	/**
	 * 如果分页查询的sql有排序条件，需要将排序字段也作为查询的输出字段，以便于在sql的OVER函数中调用
	 * 
	 * @param sql
	 * @return
	 */
	public static String addOrderByToField(String sql) {
		String tempSql = getCloneSql(sql).toLowerCase();
		if (tempSql.indexOf("union") != -1)
			return sql;

		int orberBy = tempSql.lastIndexOf("order by");
		if (orberBy > 0) {
			int select = tempSql.indexOf("select");
			int from = tempSql.indexOf("from");

			String fields = sql.substring(select + 6, from - 1);

			if (fields.trim().equals(""))
				return sql;
			String fieldArray[] = fields.split(",");
			for (String field : fieldArray) {
				if (field.trim().equals("*"))
					return sql;
			}

			StringBuilder fileStr = new StringBuilder(fields);

			String param = sql.substring(orberBy + 9);
			int desc = param.toLowerCase().indexOf("desc");
			int asc = param.toLowerCase().indexOf("asc");
			if (desc > 0)
				param = param.substring(0, desc - 1);
			if (asc > 0)
				param = param.substring(0, asc - 1);

			if (param != null && param.trim().length() > 0) {
				String[] paraArray = param.split(",");
				for (String para : paraArray) {

					int schemaIdx = para.indexOf(".");
					if (schemaIdx > 0) {
						String schema = para.substring(0, schemaIdx);
						if (fields.indexOf(schema + ".*") != -1)
							continue;
					}

					if (fields.indexOf(para.trim()) == -1)
						fileStr.append(", ").append(para);
				}
			}

			if (fileStr.length() > fields.length()) {
				StringBuilder sqlBuilder = new StringBuilder();
				sqlBuilder.append(sql.substring(0, select)).append("select ")
						.append(fileStr.toString()).append(" ").append(
								sql.substring(from));
				return sqlBuilder.toString();
			}
		}

		return sql;
	}
	
	public static String replaceSchema(String sql, String sche) {
		StringBuilder _param = new StringBuilder();
		if (sql != null && sql.trim().length() > 0) {

			String[] paramArray = sql.split(",");
			for (String param : paramArray) {
				int i = param.indexOf(".");
				if (sche != null && sche.trim().length() > 0) {
					_param.append(sche).append(".");
				}
				if (i > 0) {
					_param.append(param.substring(i + 1));
				} else {
					_param.append(param.trim());
				}

				_param.append(",");
				i = -1;
			}

			if (_param.length() > 1)
				return _param.substring(0, _param.length() - 1);
		}
		return _param.toString();
	}
}
