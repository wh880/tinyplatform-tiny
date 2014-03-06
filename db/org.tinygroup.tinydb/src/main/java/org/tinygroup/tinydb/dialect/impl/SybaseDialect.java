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

import org.springframework.jdbc.support.incrementer.SybaseMaxValueIncrementer;
import org.tinygroup.commons.tools.Assert;
import org.tinygroup.database.dialectfunction.DialectFunctionProcessor;
import org.tinygroup.database.util.DataBaseUtil;
import org.tinygroup.springutil.SpringUtil;
import org.tinygroup.tinydb.dialect.Dialect;
import org.tinygroup.tinydb.util.DialectUtil;

public class SybaseDialect implements Dialect {
	
	private SybaseMaxValueIncrementer incrementer;
	

	public SybaseMaxValueIncrementer getIncrementer() {
		return incrementer;
	}

	public void setIncrementer(SybaseMaxValueIncrementer incrementer) {
		this.incrementer = incrementer;
	}

	public SybaseDialect() {

	}

	public int getNextKey() {
		Assert.assertNotNull(incrementer,"incrementer must not null");
		return incrementer.nextIntValue();
	}

	
	public String getCurrentDate() {
		return "Select CONVERT(varchar(100), GETDATE(), 21)";
	}

	public String getDialectName() {
		return DialectUtil.DB_TYPE_SYBASE;
	}

	public String getLimitString(String sql, int offset, int limit) {
		return sql;
	}

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

	public boolean supportsLimit() {
		return false;
	}
	
	public String buildSqlFuction(String sql) {
		DialectFunctionProcessor processor=SpringUtil.getBean(DataBaseUtil.FUNCTION_BEAN);
		return processor.getFuntionSql(sql, DataBaseUtil.DB_TYPE_SYBASE);
	}
}
