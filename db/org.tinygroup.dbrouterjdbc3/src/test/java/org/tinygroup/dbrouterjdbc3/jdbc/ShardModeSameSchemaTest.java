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
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.tinygroup.dbrouter.RouterManager;
import org.tinygroup.dbrouter.factory.RouterManagerBeanFactory;

public class ShardModeSameSchemaTest extends TestCase {

	private static String driver = "org.tinygroup.dbrouterjdbc3.jdbc.TinyDriver";
	private static String routerpath = "/shardModeSameSchema.xml";
	private static RouterManager routerManager;

	private static String url = "jdbc:dbrouter://shardModeSameSchema";
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

	public void _test0() throws Exception {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		String sql = null;

		try {
			conn = DriverManager.getConnection(url, user, password);
			st = conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}

		List<String> _sList = new ArrayList<String>();
		List<Integer> _iList = new ArrayList<Integer>();

		st.addBatch("delete from teacher");
		st.addBatch("insert into teacher(id,name) values(1,'zhang')");
		st.addBatch("insert into teacher(id,name) values(2,'qian')");
		st.addBatch("insert into teacher(id,name) values(3,'sun')");
		st.addBatch("insert into teacher(id,name) values(4,'wang')");
		st.executeBatch();
		rs = st.executeQuery("select count(*) from teacher");
		if (rs.next()) {
			assertEquals(4, rs.getInt(1));
		}

		prepareRecord(st);
		sql = "select * from student where name in (select name from student where id<5 and id>2) order by id desc";
		rs = st.executeQuery(sql);
		_sList.clear();
		while (rs.next()) {
			_sList.add(rs.getString("name"));
		}
		assertEquals(2, _sList.size());
		assertEquals("s4", _sList.get(0));
		assertEquals("s3", _sList.get(1));

		sql = "select sum(age) from student where tId in (select id from teacher where id<3) group by tId ";
		rs = st.executeQuery(sql);
		_iList.clear();
		while (rs.next()) {
			_iList.add(rs.getInt(1));
		}
		assertEquals(2, _sList.size());
		assertEquals(23, _iList.get(0).intValue());
		assertEquals(27, _iList.get(1).intValue());

		sql = "SELECT age FROM (SELECT s.*, t.name AS tName FROM student s, teacher t WHERE t.id=s.tId AND t.id<3 AND s.id>1) aa GROUP BY age HAVING age >12 AND age<14;";
		rs = st.executeQuery(sql);
		_iList.clear();
		while (rs.next()) {
			_iList.add(rs.getInt(1));
		}
		assertEquals(13, _iList.get(0).intValue());

		sql = "SELECT age FROM (SELECT s.*, t.name AS tName FROM student s JOIN teacher t ON t.id=s.tId AND t.id<3 AND s.id>1) aa GROUP BY age HAVING age >12 AND age<14";
		rs = st.executeQuery(sql);
		_iList.clear();
		while (rs.next()) {
			_iList.add(rs.getInt(1));
		}
		assertEquals(13, _iList.get(0).intValue());

		sql = "SELECT count(*) FROM student s LEFT JOIN teacher t ON t.id=s.tId WHERE s.id=7";
		rs = st.executeQuery(sql);
		if (rs.next()) {
			assertEquals(0, rs.getInt(1));
		}

		sql = "SELECT count(*) FROM student s RIGHT JOIN teacher t ON t.id=s.tId WHERE s.id=7";
		rs = st.executeQuery(sql);
		if (rs.next()) {
			assertEquals(0, rs.getInt(1));
		}

		sql = "SELECT count(*) FROM student s RIGHT JOIN teacher t ON t.id=s.tId WHERE t.id=4";
		rs = st.executeQuery(sql);
		if (rs.next()) {
			assertEquals(1, rs.getInt(1));
		}

		sql = "SELECT count(*) FROM student s RIGHT JOIN teacher t ON t.id=s.tId WHERE s.id=7";
		rs = st.executeQuery(sql);
		if (rs.next()) {
			assertEquals(0, rs.getInt(1));
		}

		// 结果不准确
		sql = "select s.name from student s where s.id>5 union select t.name from teacher t where t.id>3";
		rs = st.executeQuery(sql);
		_sList.clear();
		while (rs.next()) {
			_sList.add(rs.getString("name"));
		}
		// assertEquals(2, _sList.size());
		// assertEquals("s6", _sList.get(0));
		// assertEquals("wang", _sList.get(1));

		sql = "SELECT * FROM (SELECT s.name FROM student s,teacher t WHERE s.tId=t.id) sName WHERE SUBSTR(sName.name,2,1)=1";
		rs = st.executeQuery(sql);
		_sList = new ArrayList<String>();
		if (rs.next()) {
			assertEquals("s1", rs.getString("name"));
		}

		close(conn, st, rs);
		System.out.println("test0执行结束!");
	}

	public void _test1() throws Exception {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			conn = DriverManager.getConnection(url, user, password);
			conn.setAutoCommit(false);
			st = conn.createStatement();
			st.executeUpdate("delete from teacher");
			st.executeUpdate("insert teacher(id,name) values(1,'lisi')");
			conn.commit();
		} catch (Exception e) {
			conn.rollback();
			return;
		}

		// getGeneratedKeys功能不可用
		// return autoGeneratedKeys
		// conn.setAutoCommit(true);
		// statement.executeUpdate("delete from teacher");
		// statement.executeUpdate("insert teacher( name) values('wangwu')",
		// Statement.RETURN_GENERATED_KEYS);
		// rs = statement.getGeneratedKeys();
		// if (rs.next()) {
		// int key = rs.getInt(1);
		// System.out.println(key);
		// }

		// return columnIndexs
		// statement.executeUpdate("delete from teacher");
		// int[] columnIndexs = { 1 };
		// statement.executeUpdate("insert teacher( name) values('wangwu')",
		// columnIndexs);
		// rs = statement.getGeneratedKeys();
		// if (rs.next()) {
		// int key = rs.getInt(1);
		// System.out.println(key);
		// }

		// return columnIndexs
		// statement.executeUpdate("delete from teacher");
		// String[] columnNames = { "dddd" };
		// statement.executeUpdate("insert teacher( name) values('wangwu')",
		// columnNames);
		// rs = statement.getGeneratedKeys();
		// if (rs.next()) {
		// int key = rs.getInt(1);
		// System.out.println(key);
		// }

		close(conn, st, rs);
		System.out.println("test1 执行结束");
	}

	public void _test2() throws Exception {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			conn = DriverManager.getConnection(url, user, password);
			st = conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}

		try {
			conn = DriverManager.getConnection(url, user, password);
			st = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY,
					ResultSet.CONCUR_UPDATABLE, 102);
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}

		st.addBatch("delete from teacher");
		st.addBatch("insert into teacher(id,name) values(1,'zhang')");
		st.addBatch("insert into teacher(id,name) values(2,'qian')");
		st.clearBatch();
		st.executeBatch();
		rs = st.executeQuery("select * from teacher");
		// assertEquals(false, rs.next());
		// assertEquals(false, rs.first()); //first有问题

		st.cancel();
		st.clearWarnings();
		st.getConnection();

		st.setFetchDirection(ResultSet.FETCH_REVERSE);
		st.setFetchSize(30);
		// assertEquals(ResultSet.FETCH_REVERSE,
		// statement.getFetchDirection());
		assertEquals(30, st.getFetchSize());

		// statement.getGeneratedKeys();

		st.setMaxFieldSize(101);
		st.setMaxRows(3);
		st.setQueryTimeout(13);
		// assertEquals(30, statement.getMaxFieldSize());
		assertEquals(3, st.getMaxRows());
		assertEquals(false, st.getMoreResults());
		assertEquals(false, st.getMoreResults(1));
		assertEquals(13, st.getQueryTimeout());

		st.getResultSet();

		assertEquals(ResultSet.CONCUR_UPDATABLE, st.getResultSetConcurrency());
		// assertEquals(1, statement.getResultSetHoldability());
		assertEquals(ResultSet.TYPE_FORWARD_ONLY, st.getResultSetType());
		// assertEquals(30, st.getUpdateCount());
		st.getWarnings();
		close(conn, st, rs);
		System.out.println("test2 执行结束");
	}

	public void _test3() throws Exception {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			conn = DriverManager.getConnection(url, user, password);
			st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}

		// 准备数据
		st.addBatch("delete from teacher");
		st.addBatch("insert into teacher(id,name) values(1,'zhang')");
		st.addBatch("insert into teacher(id,name) values(2,'qian')");
		st.addBatch("insert into teacher(id,name) values(3,'sun')");
		st.addBatch("insert into teacher(id,name) values(4,'wang')");
		st.executeBatch();
		rs = st.executeQuery("select * from teacher");
		// rs.setFetchDirection(ResultSet.FETCH_REVERSE);
		// rs.setFetchSize(101);

		rs.absolute(3);
		assertEquals("sun", rs.getString("name"));
		assertEquals(false, rs.isFirst());
		assertEquals(false, rs.isLast());
		assertEquals(false, rs.isBeforeFirst());
		assertEquals(false, rs.isAfterLast());

		rs.absolute(100);
		assertEquals(false, rs.absolute(100));
		assertEquals(false, rs.isFirst());
		assertEquals(false, rs.isLast());
		assertEquals(false, rs.isBeforeFirst());
		assertEquals(true, rs.isAfterLast());

		rs.absolute(-10);
		assertEquals(false, rs.absolute(-10));
		assertEquals(false, rs.isFirst());
		assertEquals(false, rs.isLast());
		assertEquals(true, rs.isBeforeFirst());
		assertEquals(false, rs.isAfterLast());

		rs.first();
		assertEquals("zhang", rs.getString("name"));
		assertEquals(true, rs.isFirst());
		assertEquals(false, rs.isLast());
		assertEquals(false, rs.isBeforeFirst());
		assertEquals(false, rs.isAfterLast());

		rs.last();
		assertEquals("wang", rs.getString("name"));
		assertEquals(false, rs.isFirst());
		assertEquals(true, rs.isLast());
		assertEquals(false, rs.isBeforeFirst());
		assertEquals(false, rs.isAfterLast());

		rs.beforeFirst();
		// assertEquals(null, rs.getString("name")); beforeFirst 滚动有问题
		assertEquals(false, rs.isFirst());
		assertEquals(false, rs.isLast());
		assertEquals(true, rs.isBeforeFirst());
		assertEquals(false, rs.isAfterLast());

		rs.afterLast();
		// assertEquals(null, rs.getString("name")); afterLast滚动有问题
		assertEquals(false, rs.isFirst());
		assertEquals(false, rs.isLast());
		assertEquals(false, rs.isBeforeFirst());
		assertEquals(true, rs.isAfterLast());

		rs.beforeFirst();
		assertEquals(true, rs.next());
		assertEquals(false, rs.previous());
		assertEquals(false, rs.isFirst());
		assertEquals(false, rs.isLast());
		assertEquals(true, rs.isBeforeFirst());
		assertEquals(false, rs.isAfterLast());

		rs.afterLast();
		assertEquals(true, rs.previous());
		String firstName1 = rs.getString("name");
		rs.last();
		String firstName2 = rs.getString("name");
		assertEquals(true, firstName1.equals(firstName2));

		rs.beforeFirst();
		assertEquals(true, rs.next());
		firstName1 = rs.getString("name");
		rs.first();
		firstName2 = rs.getString("name");
		assertEquals(true, firstName1.equals(firstName2));

		rs.clearWarnings();
		assertEquals(null, rs.getWarnings());

		// updateRow相关操作不能用
		// rs.first();
		// rs.updateString("name", "updatedName");
		// rs.cancelRowUpdates();
		// rs.updateRow();

		// rs.first();
		// rs.deleteRow();

		assertEquals(1, rs.findColumn("id"));
		assertEquals(2, rs.findColumn("name"));

		// assertEquals(ResultSet.CONCUR_UPDATABLE, rs.getConcurrency());
		// assertEquals(ResultSet.CONCUR_UPDATABLE, rs.getCursorName());

		assertEquals(ResultSet.FETCH_FORWARD, rs.getFetchDirection());
		assertEquals(0, rs.getFetchSize());

		ResultSetMetaData metaDate = rs.getMetaData();
		assertEquals(2, metaDate.getColumnCount());
		assertEquals("ID", metaDate.getColumnName(1));
		assertEquals("INT", metaDate.getColumnTypeName(1));
		assertEquals(8, metaDate.getColumnDisplaySize(1));
		assertEquals(true, metaDate.isAutoIncrement(1));

		// delete相关操作不能用
		// rs.first();
		// rs.updateInt(1, 10);
		// rs.updateString(1, "insertTestName");
		// rs.moveToInsertRow();
		// rs.insertRow();

		// rs.moveToCurrentRow();

		// rs.refreshRow();
		// rs.relative(rows)
		// rs.rowDeleted();
		// rs.rowInserted();
		// rs.rowUpdated();
		close(conn, st, rs);
		System.out.println("test3 执行结束");
	}

	public void _test4() throws Exception {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		String sql = null;
		List<String> _sList = new ArrayList<String>();

		try {
			conn = DriverManager.getConnection(url, user, password);
			st = conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}

		prepareRecord(st);
		sql = "select * from student where id in (select id from student where id<3)";
		rs = st.executeQuery(sql);
		_sList.clear();
		while (rs.next()) {
			_sList.add(rs.getString("name"));
		}
		assertEquals(2, _sList.size());
		assertEquals("s1", _sList.get(0));
		assertEquals("s2", _sList.get(1));

		sql = "select * from student where tId=(select id from teacher where name='qian')";
		rs = st.executeQuery(sql);
		_sList.clear();
		while (rs.next()) {
			_sList.add(rs.getString("name"));
		}
		assertEquals(2, _sList.size());
		assertEquals("s3", _sList.get(0));
		assertEquals("s4", _sList.get(1));
		sql = "select s.id, s.name, s.age, t.id, t.name from student s, teacher t where t.id=s.tId";
		rs = st.executeQuery(sql);
		_sList.clear();
		while (rs.next()) {
			_sList.add(rs.getString("name"));
		}
		assertEquals(6, _sList.size());
		assertEquals("s3", _sList.get(0));
		assertEquals("s6", _sList.get(1));
		assertEquals("s1", _sList.get(2));
		assertEquals("s4", _sList.get(3));
		assertEquals("s2", _sList.get(4));
		assertEquals("s5", _sList.get(5));

		sql = "select u.id, u.name, u.age, t.id, t.name from student u join teacher t on t.id=u.tId";
		rs = st.executeQuery(sql);
		_sList.clear();
		while (rs.next()) {
			_sList.add(rs.getString("name"));
		}
		assertEquals(6, _sList.size());
		assertEquals("s3", _sList.get(0));
		assertEquals("s6", _sList.get(1));
		assertEquals("s1", _sList.get(2));
		assertEquals("s4", _sList.get(3));
		assertEquals("s2", _sList.get(4));
		assertEquals("s5", _sList.get(5));

		sql = "select u.id, u.name, u.age, t.id, t.name from student u left join teacher t on t.id=u.tId";
		rs = st.executeQuery(sql);
		_sList.clear();
		while (rs.next()) {
			_sList.add(rs.getString("name"));
		}
		assertEquals(6, _sList.size());
		assertEquals("s3", _sList.get(0));
		assertEquals("s6", _sList.get(1));
		assertEquals("s1", _sList.get(2));
		assertEquals("s4", _sList.get(3));
		assertEquals("s2", _sList.get(4));
		assertEquals("s5", _sList.get(5));

		close(conn, st, rs);
		System.out.println("test4 执行结束");
	}

	public void _test5() throws Exception {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			conn = DriverManager.getConnection(url, user, password);
			st = conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}

		// 准备数据
		st.addBatch("delete from student");
		st.addBatch("insert into student(id,name,age,sex) values(1,'zhang',11,1)");
		st.addBatch("insert into student(id,name,age,sex) values(2,'qian',12,1)");
		st.addBatch("insert into student(id,name,age,sex) values(3,'sun',13,2)");
		st.addBatch("insert into student(id,name,age,sex) values(4,'wang',14,2)");
		st.executeBatch();

		rs = st.executeQuery("select count(*) from view_students_boys");
		if (rs.next()) {
			assertEquals(2, rs.getInt(1));
		}
		close(conn, st, rs);
		System.out.println("test5 执行结束");
	}

	public void test6() throws Exception {
	}

	private void prepareRecord(Statement st) throws SQLException,
			ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");
		Connection connection = DriverManager.getConnection(
				"jdbc:mysql://192.168.51.29:3306/testA", "root", "123456");
		Statement stmt = connection.createStatement();
		stmt.addBatch("delete from student");
		stmt.addBatch("delete from teacher");
		stmt.addBatch("insert into teacher(id,name) values(1,'zhang')");
		stmt.addBatch("insert into teacher(id,name) values(2,'qian')");
		stmt.addBatch("insert into teacher(id,name) values(3,'sun')");
		stmt.addBatch("insert into teacher(id,name) values(4,'wang')");
		stmt.addBatch("insert into student(id,tId,name,age) values(1,1,'s1',11)");
		stmt.addBatch("insert into student(id,tId,name,age) values(2,1,'s2',12)");
		stmt.addBatch("insert into student(id,tId,name,age) values(3,2,'s3',13)");
		stmt.addBatch("insert into student(id,tId,name,age) values(4,2,'s4',14)");
		stmt.addBatch("insert into student(id,tId,name,age) values(5,3,'s5',15)");
		stmt.addBatch("insert into student(id,tId,name,age) values(6,3,'s6',16)");
		stmt.executeBatch();

		st.addBatch("delete from student");
		st.addBatch("delete from teacher");
		st.addBatch("insert into teacher(id,name) values(1,'zhang')");
		st.addBatch("insert into teacher(id,name) values(2,'qian')");
		st.addBatch("insert into teacher(id,name) values(3,'sun')");
		st.addBatch("insert into teacher(id,name) values(4,'wang')");
		st.addBatch("insert into student(id,tId,name,age) values(1,1,'s1',11)");
		st.addBatch("insert into student(id,tId,name,age) values(2,1,'s2',12)");
		st.addBatch("insert into student(id,tId,name,age) values(3,2,'s3',13)");
		st.addBatch("insert into student(id,tId,name,age) values(4,2,'s4',14)");
		st.addBatch("insert into student(id,tId,name,age) values(5,3,'s5',15)");
		st.addBatch("insert into student(id,tId,name,age) values(6,3,'s6',16)");
		st.executeBatch();
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

	public static void main(String[] args) {
	}
}
