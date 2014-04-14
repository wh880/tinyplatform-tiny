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
/*
 * 系统名称: JRES 应用快速开发企业套件
 * 模块名称: JRES
 * 文件名称: SQLServerDialect.java
 * 软件版权: 恒生电子股份有限公司
 * 修改记录:
 * 修改日期      修改人员                     修改说明
 * ========    =======  ============================================
 *             
 * ========    =======  ============================================
 */
package org.tinygroup.tinydb.dialect.impl;

import org.springframework.jdbc.support.incrementer.SqlServerMaxValueIncrementer;
import org.tinygroup.commons.tools.Assert;
import org.tinygroup.database.dialectfunction.DialectFunctionProcessor;
import org.tinygroup.database.util.DataBaseUtil;
import org.tinygroup.springutil.SpringUtil;
import org.tinygroup.tinydb.dialect.Dialect;
import org.tinygroup.tinydb.util.DialectUtil;

/**
 * The Class SQLServerDialect.
 */
public class SQLServerDialect implements Dialect {
	
	private SqlServerMaxValueIncrementer incrementer;
	

	public SqlServerMaxValueIncrementer getIncrementer() {
		return incrementer;
	}

	public void setIncrementer(SqlServerMaxValueIncrementer incrementer) {
		this.incrementer = incrementer;
	}

	/**
	 * Instantiates a new sQL server dialect.
	 */
	public SQLServerDialect() {
	}

	/**
	 * getLimitString.
	 * 
	 * @param sql
	 *            the query select
	 * @param offset
	 *            the offset
	 * @param limit
	 *            the limit
	 * @return String
	 * @todo Implement this snowrain.database.data.Dialect method
	 */
	public String getLimitString(String sql, int offset, int limit) {
		String tempSql = DialectUtil.getCloneSql(sql).toLowerCase();

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
		if (DialectUtil.hasDistinct(tempSql)) {
			pagingSelect.append(" row_.* from ( ") // add another (inner)
					// nested
					// select
					.append(sql.substring(startOfSelect)) // add the main
					// query
					.append(" ) as row_"); // close off the inner nested select
		} else {
			String fieldstr = DialectUtil.addOrderByToField(sql);
			int orderIdx = tempSql.lastIndexOf("order by");
			if (orderIdx != -1)
				fieldstr = fieldstr.substring(0, orderIdx);
			pagingSelect.append(" t_.* from(" + fieldstr + ")as t_");
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

	private String getRowNumber(String sql) {
		String tempSql = DialectUtil.getCloneSql(sql).toLowerCase();
		StringBuffer rownumber = new StringBuffer(50)
				.append("row_number() over(");
		if (tempSql.indexOf("union") == -1) { // 没有union情况下

			int orderByIndex = tempSql.lastIndexOf("order by");

			if (orderByIndex > 0 && !DialectUtil.hasDistinct(tempSql)) {
				rownumber.append("order by ").append(
						DialectUtil.replaceSchema(sql
								.substring(orderByIndex + 9), "t_"));
			}

		}
		rownumber.append(") as rownumber_,");
		return rownumber.toString();
	}

	/**
	 * supportsLimit.
	 * 
	 * @return boolean
	 * @todo Implement this snowrain.database.data.Dialect method
	 */
	public boolean supportsLimit() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.hundsun.jres.interfaces.db.dialect.IDialect#getAutoIncreaseKeySql()
	 */
	public int getNextKey() {
		Assert.assertNotNull(incrementer,"incrementer must not null");
		return incrementer.nextIntValue();		
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hundsun.jres.interfaces.db.dialect.IDialect#getCurrentDate()
	 */
	public String getCurrentDate() {
		return "Select CONVERT(varchar(100), GETDATE(), 21)";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hundsun.jres.interfaces.db.dialect.IDialect#getDialectName()
	 */
	public String getDialectName() {
		return DialectUtil.DB_TYPE_SQLSERVER;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.hundsun.jres.interfaces.db.dialect.IDialect#getTotalCountSql(java
	 * .lang.String)
	 */
	public String getTotalCountSql(String sql) {
		if (sql.indexOf("union") != -1) {
			sql = "select count(1) as TotalCount from (" + sql
					+ ") temp_select ";
			return sql;
		}

		String countStr = sql.trim();
		StringBuilder sb = new StringBuilder();
		if (countStr.startsWith("select")) {
			if (countStr.indexOf("from") != -1) {
				sb.append(countStr.substring(0, 6)).append(
						" count(1) as TotalCount ");
				if (countStr.indexOf("order by") != -1)
					sb.append(countStr.substring(countStr.indexOf("from"),
							countStr.indexOf("order by")));
				else
					sb.append(countStr.substring(countStr.indexOf("from")));
			}
		}
		return sb.toString();
	}
	public String buildSqlFuction(String sql) {
		DialectFunctionProcessor processor=SpringUtil.getBean(DataBaseUtil.FUNCTION_BEAN);
		return processor.getFuntionSql(sql, DataBaseUtil.DB_TYPE_SQLSERVER);
	}

}
