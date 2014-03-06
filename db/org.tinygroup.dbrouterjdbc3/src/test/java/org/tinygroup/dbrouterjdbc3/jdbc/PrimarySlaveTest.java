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
package org.tinygroup.dbrouterjdbc3.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import junit.framework.TestCase;

import org.tinygroup.dbrouter.RouterManager;
import org.tinygroup.dbrouter.factory.RouterManagerBeanFactory;

public class PrimarySlaveTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	public void test1() throws Exception {
		RouterManager routerManager = RouterManagerBeanFactory.getManager();
		routerManager.addRouters("/primarySlave.xml");
		Class.forName("org.tinygroup.dbrouterjdbc3.jdbc.TinyDriver");
		Connection conn = DriverManager.getConnection(
				"jdbc:dbrouter://primarySlave", "luog", "123456");
		Statement statement = conn.createStatement();
		PreparedStatement ps = null;

		// 准备数据
		try {
			conn.setAutoCommit(false);
			// statement.addBatch("delete from teacher");
			// statement
			// .addBatch("insert into teacher(id,name) values(1,'zhang')");
			// statement.addBatch("insert into teacher(id,name) values(2,'qian')");
			// statement.addBatch("insert into teacher(id,name) values(3,'sun')");
			// statement.addBatch("insert into teacher(id,name) values(4,'wang')");
			// statement.addBatch("insert into teacher(id,name) values(5,'chen')");
			// statement.executeBatch();
			statement.executeUpdate("delete from teacher");
			statement
					.executeUpdate("insert into teacher(id,name) values(1,'zhang')");
			statement
					.executeUpdate("insert into teacher(id,name) values(2,'qian')");
			statement
					.executeUpdate("insert into teacher(id,name) values(3,'sun')");
			statement
					.executeUpdate("insert into teacher(id,name) values(4,'wang')");
			statement
					.executeUpdate("insert into teacher(id,name) values(5,'chen')");
			conn.commit();
		} catch (Exception e) {
			conn.rollback();
		}

		ResultSet rs = statement.executeQuery("select count(*) from teacher");
		rs.first();
		assertEquals(5, rs.getInt(1));

		rs = statement
				.executeQuery("select avg(id),sum(id),max(id),min(id) from teacher");
		rs.first();
		assertEquals(3, rs.getInt(1));
		assertEquals(15, rs.getInt(2));
		assertEquals(5, rs.getInt(3));
		assertEquals(1, rs.getInt(4));

		ps = conn
				.prepareStatement("select avg(id),sum(id),max(id),min(id) from teacher where id in(?,?,?)");
		ps.setInt(1, 1);
		ps.setInt(2, 2);
		ps.setInt(3, 3);
		ps.executeQuery();
		rs.first();
		// assertEquals(2, rs.getInt(1));
		// assertEquals(6, rs.getInt(2));
		// assertEquals(3, rs.getInt(3));
		// assertEquals(1, rs.getInt(4));

		rs = statement
				.executeQuery("select avg(id),sum(id),max(id),min(id) from teacher where id>1 group by id having id<3");
		rs.first();
		assertEquals(2, rs.getInt(1));
		assertEquals(2, rs.getInt(2));
		assertEquals(2, rs.getInt(3));
		assertEquals(2, rs.getInt(4));

		statement.executeUpdate("delete from teacher where id=1");
		rs = statement.executeQuery("select count(*) from teacher");
		rs.first();
		assertEquals(4, rs.getInt(1));

		System.out.println("test1执行结束！");
	}

}
