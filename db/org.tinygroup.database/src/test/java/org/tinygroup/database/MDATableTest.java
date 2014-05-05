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
package org.tinygroup.database;

import junit.framework.TestCase;
import org.tinygroup.database.table.TableProcessor;
import org.tinygroup.database.util.DataBaseUtil;
import org.tinygroup.springutil.SpringUtil;

import java.util.List;

public class MDATableTest extends TestCase {
	static {
		TestInit.init();

	}
	TableProcessor tableProcessor;

	protected void setUp() throws Exception {
		super.setUp();
		tableProcessor = SpringUtil.getBean(DataBaseUtil.TABLEPROCESSOR_BEAN);
	}

	public void testGetTableStringString() {
		assertNotNull(tableProcessor.getTable("com.hundsun", "user"));
		assertNotNull(tableProcessor.getTable("com.hundsun", "company"));
	}

	public void testGetTableString() {
		assertNotNull(tableProcessor.getTable("user"));
		assertNotNull(tableProcessor.getTable("company"));
	}
	public void testGetCreateSqlStringStringString() {
		System.out.println("com.hundsun.user,sql:");
		List<String> tableSql= tableProcessor.getCreateSql( "user","com.hundsun", "oracle");
		System.out.println(tableSql);
		
		System.out.println("com.hundsun.company,sql:");
		List<String> tableSql2= tableProcessor.getCreateSql("company", "com.hundsun", "oracle");
		System.out.println(tableSql2);
	}
	
	public void testGetCreateMySqlStringStringString() {
		System.out.println("com.hundsun.user,sql:");
		List<String> tableSql= tableProcessor.getCreateSql( "user","com.hundsun", "mysql");
		System.out.println(tableSql);
		
		System.out.println("com.hundsun.company,sql:");
		List<String> tableSql2= tableProcessor.getCreateSql("company", "com.hundsun", "mysql");
		System.out.println(tableSql2);
	}
}
