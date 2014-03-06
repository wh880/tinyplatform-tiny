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
package org.tinygroup.dbrouterjdbc4.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import junit.framework.TestCase;

import org.tinygroup.dbrouter.RouterManager;
import org.tinygroup.dbrouter.factory.RouterManagerBeanFactory;

/**
 * 
 * 功能说明:测试  ResultSetMetaData的getTableName方法
 * <p>

 * 开发人员: renhui <br>
 * 开发时间: 2014-1-10 <br>
 * <br>
 */
public class ResultSetMetaDataTest extends TestCase {


	protected void setUp() throws Exception {
		super.setUp();
	}

	private Connection getConnectionWithDiffSchema()
			throws ClassNotFoundException, SQLException {
		RouterManager routerManager = RouterManagerBeanFactory.getManager();
		routerManager.addRouters("/differentSchemaShard.xml");
		Class.forName("org.tinygroup.dbrouterjdbc4.jdbc.TinyDriver");
		Connection conn = DriverManager.getConnection(
				"jdbc:dbrouter://diffSchemaShard", "luog", "123456");
		return conn;
	}

	private Connection getConnectiontWithSomeSchema()
			throws ClassNotFoundException, SQLException {
		RouterManager routerManager = RouterManagerBeanFactory.getManager();
		routerManager.addRouters("/sameSchemaDiffTableWithShard.xml");
		Class.forName("org.tinygroup.dbrouterjdbc4.jdbc.TinyDriver");
		Connection conn = DriverManager.getConnection(
				"jdbc:dbrouter://tableShard", "luog", "123456");
		return conn;
	}


	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testTableNameWithDiffSchema() throws ClassNotFoundException, SQLException{
		Connection connection=getConnectionWithDiffSchema();
		Statement stmt=connection.createStatement();
		for (int i = 0; i < 100; i++) {
			String sql = "select * from aaa where id="+(i+1)+" order by id";
			ResultSet rs=stmt.executeQuery(sql);
			assertEquals("aaa", rs.getMetaData().getTableName(1));
		}
		connection.close();
	}
	
    public void testTableNameWithSomeSchema() throws ClassNotFoundException, SQLException{
    	Connection connection=getConnectiontWithSomeSchema();
    	Statement stmt=connection.createStatement();
		for (int i = 0; i < 100; i++) {
			String sql = "select * from aaa where id="+(i+1)+" order by id";
			ResultSet rs=stmt.executeQuery(sql);
			assertEquals("aaa", rs.getMetaData().getTableName(1));
		}
		connection.close();
	}

}
