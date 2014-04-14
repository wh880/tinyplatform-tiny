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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import junit.framework.TestCase;

import org.tinygroup.dbrouter.RouterManager;
import org.tinygroup.dbrouter.factory.RouterManagerBeanFactory;
import org.tinygroup.dbrouterjdbc3.jdbc.util.FileUtil;

public class PrimarySlaveTest extends TestCase {

	private static final String TINY_DRIVER = "org.tinygroup.dbrouterjdbc3.jdbc.TinyDriver";
	private static final String DERBY_DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
	private static final String ROUTER_PATH = "/primarySlave.xml";
	private static final String URL = "jdbc:dbrouter://primarySlave";
	private static final String USER = "luog";
	private static final String PASSWORD = "123456";
	private static final String[] DERBY_DBS = { "derbydb/db01", "derbydb/db02",
			"derbydb/db03" };

	private static RouterManager routerManager;

	static {
		try {
			Class.forName(TINY_DRIVER); // 加载tiny数据库驱动
			Class.forName(DERBY_DRIVER); // 加载derby数据库驱动
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("数据库驱动加载失败!");
		}

		// 初始化routerManager
		routerManager = RouterManagerBeanFactory.getManager();
		routerManager.addRouters(ROUTER_PATH);
	}

	protected void setUp() throws Exception {
		super.setUp();
		// init();
	}

	public void test() {
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		// destroy();
	}

	public void _test() throws Exception {
		Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
		Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);

		// 准备数据
		try {
			conn.setAutoCommit(false);
			st.executeUpdate("delete from teacher"); // 清空表teacher
			st.executeUpdate("insert into teacher(id,name) values(1,'zhang')");
			st.executeUpdate("insert into teacher(id,name) values(2,'qian')");
			st.executeUpdate("insert into teacher(id,name) values(3,'sun')");
			st.executeUpdate("insert into teacher(id,name) values(4,'wang')");
			st.executeUpdate("insert into teacher(id,name) values(5,'chen')");
			conn.commit();
		} catch (Exception e) {
			conn.rollback();
			throw new RuntimeException("表teacher数据初始化失败!");
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
		destroy();
	}

	/**
	 * 初始化derby数据库和表
	 */
	private void init() {
		StringBuffer sb = new StringBuffer();
		sb.append("CREATE TABLE ");
		sb.append("teacher").append("(");
		sb.append("ID int not null,");
		sb.append("NAME varchar(20))");
		String sql = sb.toString();

		for (String derbyDB : DERBY_DBS) {
			StringBuffer url = new StringBuffer("jdbc:derby:");
			url.append(derbyDB);
			url.append(";create=true");

			Connection conn = null;
			Statement st = null;
			try {
				conn = DriverManager.getConnection(url.toString());
				st = conn.createStatement();
				st.execute(sql);
			} catch (SQLException e) {
				throw new RuntimeException("初始化表失败!");
			} finally {
				close(conn, st, null);
			}
		}
	}

	/**
	 * 关闭数据库资源
	 * 
	 * @param conn
	 * @param st
	 * @param rs
	 */
	private static void close(Connection conn, Statement st, ResultSet rs) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
			}
		}
		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
			}
		}
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
			}
		}
	}

	/**
	 * 清理derby数据库
	 */
	private void destroy() throws Exception {
		try {
			DriverManager.getConnection("jdbc:derby:;shutdown=true"); // 关闭derby数据库
		} catch (SQLException e) {
			// 关闭失败,忽略之
		}

		// 删除数据库对应文件和目录
		FileUtil.deletefile("derby.log");
		FileUtil.deletefile("file.log");
		FileUtil.deletefile("derbydb");
	}

}
