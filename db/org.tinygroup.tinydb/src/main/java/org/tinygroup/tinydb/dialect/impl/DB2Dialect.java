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
package org.tinygroup.tinydb.dialect.impl;

import org.springframework.jdbc.support.incrementer.DB2SequenceMaxValueIncrementer;
import org.tinygroup.commons.tools.Assert;
import org.tinygroup.database.dialectfunction.DialectFunctionProcessor;
import org.tinygroup.database.util.DataBaseUtil;
import org.tinygroup.springutil.SpringUtil;
import org.tinygroup.tinydb.dialect.Dialect;

public class DB2Dialect implements Dialect{

	private DB2SequenceMaxValueIncrementer incrementer;
	
	public DB2SequenceMaxValueIncrementer getIncrementer() {
		return incrementer;
	}

	public void setIncrementer(DB2SequenceMaxValueIncrementer incrementer) {
		this.incrementer = incrementer;
	}

	public boolean supportsLimit() {
		return true;
	}

	public String getLimitString(String sql, int offset, int limit) {
		String tempSql = getCloneSql(sql).toLowerCase();

		// 如果含有union语句进行特殊处理 sql语句转换成子查询
		if (sql.indexOf("union") != -1) {
			sql = "select temp_select.* from (" + sql + ") temp_select "; // sql
			// 语句进行转换
		}
		int startOfSelect = tempSql.indexOf("select");
		boolean hasOffset = false;
		if (offset >= 1) {
			hasOffset = true;
		}
		StringBuffer pagingSelect = new StringBuffer(sql.length() + 100)
				.append(sql.substring(0, startOfSelect)) // add the comment
				.append("select * from ( select ") // nest the main query in an
				// outer select
				.append(getRowNumber(sql)); // add the rownnumber bit into the
		// outer query select list
		if (hasDistinct(tempSql)) {
			pagingSelect.append(" row_.* from ( ") // add another (inner)
					// nested
					// select
					.append(sql.substring(startOfSelect)) // add the main
					// query
					.append(" ) as row_"); // close off the inner nested select
		} else {
			pagingSelect
					.append(("select t_.* from(" + addOrderByToField(sql) + ")as t_")
							.substring(startOfSelect + 6)); // add the
			// main
			// query
		}
		pagingSelect.append(" ) as temp_ where rownumber_ ");
		// add the restriction to the outer select
		if (hasOffset) {
			// pagingSelect.append("between ?+1 and ?");
			pagingSelect.append("between " + (offset) + " and "
					+ (offset + limit - 1));
		} else {
			pagingSelect.append("<= " + (offset + limit - 1));
		}

		return pagingSelect.toString();
	}

	public int getNextKey() {
		Assert.assertNotNull(incrementer,"incrementer must not null");
		return incrementer.nextIntValue();
	}


	/**
	 * 如果分页查询的sql有排序条件，需要将排序字段也作为查询的输出字段，以便于在sql的OVER函数中调用
	 * 
	 * @param sql
	 * @return
	 */
	private String addOrderByToField(String sql) {
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

	/**
	 * Gets the row number.
	 * 
	 * @param sql
	 *            the sql
	 * @return the row number
	 */
	private String getRowNumber(String sql) {
		String tempSql = getCloneSql(sql).toLowerCase();
		StringBuffer rownumber = new StringBuffer(50)
				.append("rownumber() over(");
		if (tempSql.indexOf("union") == -1) { // 没有union情况下

			int orderByIndex = tempSql.lastIndexOf("order by");

			if (orderByIndex > 0 && !hasDistinct(tempSql)) {
				rownumber.append("order by ").append(
						replaceSchema(sql.substring(orderByIndex + 9), "t_"));
			}

		}
		rownumber.append(") as rownumber_,");
		return rownumber.toString();
	}

	/**
	 * 替换sql中字段的schema为指定的sche
	 * 
	 * @param sql
	 * @param sche
	 * @return
	 */
	private String replaceSchema(String sql, String sche) {
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

	/**
	 * 生成克隆sql.
	 * 
	 * @param sql
	 *            the sql
	 * @return the clone sql
	 * @return
	 */
	private static String getCloneSql(String sql) {
		byte[] tempBytes = new byte[sql.getBytes().length];
		System
				.arraycopy(sql.getBytes(), 0, tempBytes, 0,
						sql.getBytes().length);
		String tempSql = new String(tempBytes);
		return tempSql;
	}

	/**
	 * Checks for distinct.
	 * 
	 * @param sql
	 *            the sql
	 * @return true, if successful
	 */
	private static boolean hasDistinct(String sql) {
		// String tempSql = getCloneSql(sql);
		return sql.indexOf("select distinct") >= 0;
	}

	public String getCurrentDate() {
		return "select current timestamp from sysibm.sysdummy1";
	}

	public String buildSqlFuction(String sql) {
		DialectFunctionProcessor processor=SpringUtil.getBean(DataBaseUtil.FUNCTION_BEAN);
		return processor.getFuntionSql(sql, DataBaseUtil.DB_TYPE_DB2);
	}

}
