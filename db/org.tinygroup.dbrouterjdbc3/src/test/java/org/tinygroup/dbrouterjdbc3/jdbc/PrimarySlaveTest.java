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
import java.sql.SQLException;
import java.sql.Statement;

import junit.framework.TestCase;

import org.tinygroup.dbrouter.RouterManager;
import org.tinygroup.dbrouter.factory.RouterManagerBeanFactory;

public class PrimarySlaveTest extends TestCase {

	private static String driver = "org.tinygroup.dbrouterjdbc3.jdbc.TinyDriver";
	private static String routerpath = "/primarySlave.xml";
	private static RouterManager routerManager;

	private static String url = "jdbc:dbrouter://primarySlave";
	private static String user = "luog";
	private static String password = "123456";

	static {
		routerManager = RouterManagerBeanFactory.getManager();
		routerManager.addRouters(routerpath);
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void test1() throws Exception {
		Connection conn = null;
		Statement st = null;

		conn = DriverManager.getConnection(url, user, password);
		st = conn.createStatement();

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
			st.executeUpdate("delete from teacher");
			st.executeUpdate("insert into teacher(id,name) values(1,'zhang')");
			st.executeUpdate("insert into teacher(id,name) values(2,'qian')");
			st.executeUpdate("insert into teacher(id,name) values(3,'sun')");
			st.executeUpdate("insert into teacher(id,name) values(4,'wang')");
			st.executeUpdate("insert into teacher(id,name) values(5,'chen')");
			conn.commit();
		} catch (Exception e) {
			conn.rollback();
		}

		ResultSet rs = st.executeQuery("select count(*) from teacher");
		rs.first();
		assertEquals(5, rs.getInt(1));

		rs = st.executeQuery("select avg(id),sum(id),max(id),min(id) from teacher");
		rs.first();
		assertEquals(3, rs.getInt(1));
		assertEquals(15, rs.getInt(2));
		assertEquals(5, rs.getInt(3));
		assertEquals(1, rs.getInt(4));

		PreparedStatement ps = conn
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

		rs = st.executeQuery("select avg(id),sum(id),max(id),min(id) from teacher where id>1 group by id having id<3");
		rs.first();
		assertEquals(2, rs.getInt(1));
		assertEquals(2, rs.getInt(2));
		assertEquals(2, rs.getInt(3));
		assertEquals(2, rs.getInt(4));

		conn.setAutoCommit(false);
		try {
			st = conn.createStatement();
			st.executeUpdate("delete from teacher where id=1");
			rs = st.executeQuery("select count(*) from teacher");
			rs.first();
			assertEquals(4, rs.getInt(1));
		} catch (Exception e) {
			conn.rollback();
		}

		close(conn, st, rs);
		close(null, ps, null);
		System.out.println("test1执行结束！");
	}

	public void test6() throws Exception {
	}

	private void close(Connection conn, Statement st, ResultSet rs) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
