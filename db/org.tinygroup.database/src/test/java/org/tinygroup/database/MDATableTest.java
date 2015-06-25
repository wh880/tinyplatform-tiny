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
package org.tinygroup.database;

import junit.framework.TestCase;
import org.tinygroup.beancontainer.BeanContainerFactory;
import org.tinygroup.database.table.TableProcessor;
import org.tinygroup.database.util.DataBaseUtil;

import java.util.List;

public class MDATableTest extends TestCase {
	static {
		TestInit.init();

	}
	TableProcessor tableProcessor;

	protected void setUp() throws Exception {
		super.setUp();
		tableProcessor = BeanContainerFactory.getBeanContainer(this.getClass().getClassLoader()).getBean(DataBaseUtil.TABLEPROCESSOR_BEAN);
	}

	public void testGetTableStringString() {
		assertNotNull(tableProcessor.getTable("org.tinygroup", "user"));
		assertNotNull(tableProcessor.getTable("org.tinygroup", "company"));
	}

	public void testGetTableString() {
		assertNotNull(tableProcessor.getTable("user"));
		assertNotNull(tableProcessor.getTable("company"));
	}
	
	public void testOracleCreateSql() {
		System.out.println("org.tinygroup.user,oracle sql:");
		List<String> tableSql= tableProcessor.getCreateSql( "user","org.tinygroup", "oracle");
		System.out.println(tableSql);
		assertEquals(2, tableSql.size());
		
		System.out.println("org.tinygroup.company,oracle sql:");
		List<String> tableSql2= tableProcessor.getCreateSql("company", "org.tinygroup", "oracle");
		System.out.println(tableSql2);
		assertEquals(2, tableSql2.size());
	}
	
	public void testDb2CreateSql() {
		System.out.println("org.tinygroup.user,db2 sql:");
		List<String> tableSql= tableProcessor.getCreateSql( "user","org.tinygroup", "db2");
		System.out.println(tableSql);
		assertEquals(2, tableSql.size());
		
		System.out.println("org.tinygroup.company,db2 sql:");
		List<String> tableSql2= tableProcessor.getCreateSql("company", "org.tinygroup", "db2");
		System.out.println(tableSql2);
		assertEquals(2, tableSql2.size());
	}
	
	public void testH2CreateSql() {
		System.out.println("org.tinygroup.user,h2 sql:");
		List<String> tableSql= tableProcessor.getCreateSql( "user","org.tinygroup", "h2");
		System.out.println(tableSql);
		assertEquals(2, tableSql.size());
		
		System.out.println("org.tinygroup.company,h2 sql:");
		List<String> tableSql2= tableProcessor.getCreateSql("company", "org.tinygroup", "h2");
		System.out.println(tableSql2);
		assertEquals(2, tableSql2.size());
	}
	
	public void testMysqlCreateSql() {
		System.out.println("org.tinygroup.user,mysql sql:");
		List<String> tableSql= tableProcessor.getCreateSql( "user","org.tinygroup", "mysql");
		System.out.println(tableSql);
		assertEquals(2, tableSql.size());
		
		System.out.println("org.tinygroup.company,mysql sql:");
		List<String> tableSql2= tableProcessor.getCreateSql("company", "org.tinygroup", "mysql");
		System.out.println(tableSql2);
		assertEquals(2, tableSql2.size());
	}
	
	
}
