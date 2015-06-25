/**
 *  Copyright (c) 1997-2013, www.tinygroup.org (tinygroup@126.com).
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
/*
 * 系统名称: JRES 应用快速开发企业套件
 * 模块名称: JRES
 * 文件名称: InformixDialect.java
 * 软件版权: 恒生电子股份有限公司
 * 修改记录:
 * 修改日期      修改人员                     修改说明
 * ========    =======  ============================================
 *             
 * ========    =======  ============================================
 */
package org.tinygroup.tinydb.dialect.impl;

import org.tinygroup.database.util.DataBaseUtil;

/**
 * The Class InformixDialect.
 */
public class InformixDialect extends AbstractSequenceDialcet {
	
	/**
	 * Instantiates a new informix dialect.
	 */
	public InformixDialect() {
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
	 */
	public String getLimitString(String sql, int offset, int limit) {
		StringBuffer pagingSelect = new StringBuffer();
		pagingSelect.append("select skip ");
		pagingSelect.append(offset - 1);
		pagingSelect.append(" first ").append(limit);
		pagingSelect.append(" * from (").append(sql);
		pagingSelect.append(" )");
		return pagingSelect.toString();
	}

	/**
	 * supportsLimit.
	 * 
	 * @return boolean
	 */
	public boolean supportsLimit() {
		return true;
	}



	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hundsun.jres.interfaces.db.dialect.IDialect#getCurrentDate()
	 */
	public String getCurrentDate() {
		return "SELECT current FROM sysmaster:sysshmvals";
	}

	public String buildSqlFuction(String sql) {
		return functionProcessor.getFuntionSql(sql, DataBaseUtil.DB_TYPE_INFORMIX);
	}
	
	protected String getSequenceQuery() {
		return "select " + getSelectSequenceNextValString( getIncrementerName() ) + " from systables where tabid=1";
	}
	
	private String getSelectSequenceNextValString(String sequenceName) {
		return sequenceName + ".nextval";
	}

	
}
