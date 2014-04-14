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
 * 文件名称: MySQLDialect.java
 * 软件版权: 恒生电子股份有限公司
 * 修改记录:
 * 修改日期      修改人员                     修改说明
 * ========    =======  ============================================
 *             
 * ========    =======  ============================================
 */
package org.tinygroup.tinydb.dialect.impl;

import org.springframework.jdbc.support.incrementer.MySQLMaxValueIncrementer;
import org.tinygroup.commons.tools.Assert;
import org.tinygroup.database.dialectfunction.DialectFunctionProcessor;
import org.tinygroup.database.util.DataBaseUtil;
import org.tinygroup.springutil.SpringUtil;
import org.tinygroup.tinydb.dialect.Dialect;
import org.tinygroup.tinydb.util.DialectUtil;

/**
 * The Class MySQLDialect.
 */
public class MySQLDialect implements Dialect {
	
	private MySQLMaxValueIncrementer incrementer;
	
	public MySQLMaxValueIncrementer getIncrementer() {
		return incrementer;
	}

	public void setIncrementer(MySQLMaxValueIncrementer incrementer) {
		this.incrementer = incrementer;
	}

	/**
	 * Instantiates a new my sql dialect.
	 */
	public MySQLDialect() {
	}

	/**
	 * getLimitString.
	 * 
	 * @param sql
	 *            String
	 * @param offset
	 *            int
	 * @param limit
	 *            int
	 * @return String
	 * @todo Implement this snowrain.database.data.Dialect method
	 */
	public String getLimitString(String sql, int offset, int limit) {
		StringBuffer pagingSelect = new StringBuffer();
		pagingSelect.append(sql);
		pagingSelect.append(" limit " + (offset - 1) + ", " + limit);
		return pagingSelect.toString();
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
		return "select now()";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hundsun.jres.interfaces.db.dialect.IDialect#getDialectName()
	 */
	public String getDialectName() {
		return DialectUtil.DB_TYPE_MYSQL;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.hundsun.jres.interfaces.db.dialect.IDialect#getTotalCountSql(java
	 * .lang.String)
	 */
	public String getTotalCountSql(String sql) {
		String countStr = sql.trim();
		if (sql.indexOf("union") != -1) {
			sql = "select count(1) as TotalCount from (" + sql
					+ ") temp_select ";
			return sql;
		}
		if (countStr.startsWith("select")) {
			if (countStr.indexOf("from") != -1)
				countStr = countStr.substring(0, 6)
						+ " count(1) as TotalCount "
						+ countStr.substring(countStr.indexOf("from"));
		}
		return countStr;
	}
	
	public String buildSqlFuction(String sql) {
		DialectFunctionProcessor processor=SpringUtil.getBean(DataBaseUtil.FUNCTION_BEAN);
		return processor.getFuntionSql(sql, DataBaseUtil.DB_TYPE_MYSQL);
	}
}
