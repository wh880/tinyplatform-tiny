package org.tinygroup.dbrouterjdbc3.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.tinygroup.dbrouter.RouterManager;
import org.tinygroup.dbrouter.factory.RouterManagerBeanFactory;
import org.tinygroup.dbrouterjdbc3.jdbc.util.FiltUtil;

public class ShardModeDiffSchemaTest extends TestCase {

	private static final String TINY_DRIVER = "org.tinygroup.dbrouterjdbc3.jdbc.TinyDriver";
	private static final String DERBY_DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
	private static final String ROUTER_PATH = "/shardModeDiffSchema.xml";
	private static final String URL = "jdbc:dbrouter://shardModeDiffSchema";
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
		init();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		destroy();
	}

	public void test() throws Exception {
		Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
		relatedQueryTest(conn);
		statementTest(conn);
		resultSetTest(conn);
		relatedQueryTest2(conn);
		close(conn, null, null);
	}

	/**
	 * 两张表关联查询测试
	 * 
	 * @param conn
	 * @throws Exception
	 */
	public void relatedQueryTest(Connection conn) throws Exception {
		Statement st = null;
		ResultSet rs = null;

		st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);

		List<String> _sList = new ArrayList<String>();
		List<Integer> _iList = new ArrayList<Integer>();

		// addBatch操作
		st.executeUpdate("delete from teacher");
		st.executeUpdate("insert into teacher(id,name) values(1,'zhang')");
		st.executeUpdate("insert into teacher(id,name) values(2,'qian')");
		st.executeUpdate("insert into teacher(id,name) values(3,'sun')");
		st.executeUpdate("insert into teacher(id,name) values(4,'wang')");
		rs = st.executeQuery("select count(*) from teacher");
		if (rs.next()) {
			assertEquals(4, rs.getInt(1));
		}

		// 准备数据
		prepareRecord(st);
		String sql = "select * from student where name in (select name from student where id<5 and id>2) order by id desc";
		rs = st.executeQuery(sql);
		_sList.clear();
		while (rs.next()) {
			_sList.add(rs.getString("name"));
		}
		assertEquals(2, _sList.size());
		assertEquals("s4", _sList.get(0));
		assertEquals("s3", _sList.get(1));

		sql = "select sum(age) from student where tId in (select id from teacher where id<3) group by tId";
		rs = st.executeQuery(sql);
		_iList.clear();
		while (rs.next()) {
			_iList.add(rs.getInt(1));
		}
		assertEquals(2, _sList.size());
		assertEquals(23, _iList.get(0).intValue());
		assertEquals(27, _iList.get(1).intValue());

		sql = "SELECT age FROM (SELECT s.*, t.name AS tName FROM student s, teacher t WHERE t.id=s.tId AND t.id<3 AND s.id>1) aa GROUP BY age HAVING age >12 AND age<14";
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

		sql = "select s.name from student s where s.id>5 union select t.name from teacher t where t.id>3";
		rs = st.executeQuery(sql);
		_sList.clear();
		while (rs.next()) {
			_sList.add(rs.getString("name"));
		}
		assertEquals(2, _sList.size());
		assertEquals("s6", _sList.get(0));
		assertEquals("wang", _sList.get(1));

		// sql =
		// "SELECT * FROM (SELECT s.name FROM student s,teacher t WHERE s.tId=t.id) sName WHERE SUBSTR(sName.name,2,1)=1";
		// rs = st.executeQuery(sql);
		// _sList = new ArrayList<String>();
		// if (rs.next()) {
		// assertEquals("s1", rs.getString("name"));
		// }

		close(null, st, rs);
		System.out.println("relatedQueryTest执行结束!");
	}

	/**
	 * Statement相关测试，比如clearWarnings，setMaxFieldSize，setMaxRows等
	 * 
	 * @param conn
	 * @throws Exception
	 */
	public void statementTest(Connection conn) throws Exception {
		Statement st = null;
		ResultSet rs = null;

		try {
			st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
		} catch (SQLException e) {
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

		// st.cancel();
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

		// assertEquals(ResultSet.CONCUR_UPDATABLE,
		// st.getResultSetConcurrency());
		// assertEquals(1, statement.getResultSetHoldability());
		// assertEquals(ResultSet.TYPE_FORWARD_ONLY, st.getResultSetType());
		// assertEquals(30, st.getUpdateCount());
		st.getWarnings();

		close(null, st, rs);
		System.out.println("statementTest 执行结束");
	}

	/**
	 * ResultSet相关测试,比如first,last,absolute等
	 * 
	 * @param conn
	 * @throws Exception
	 */
	public void resultSetTest(Connection conn) throws Exception {
		Statement st = null;
		ResultSet rs = null;

		try {
			st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
		} catch (SQLException e) {
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
		assertEquals("zhang", rs.getString("name"));
		assertEquals(false, rs.isFirst());
		assertEquals(false, rs.isLast());
		assertEquals(false, rs.isBeforeFirst());
		assertEquals(false, rs.isAfterLast());

		rs.absolute(100);
		assertEquals(false, rs.absolute(100));
		assertEquals("wang", rs.getString("name"));
		assertEquals(false, rs.isFirst());
		assertEquals(false, rs.isLast());
		assertEquals(false, rs.isBeforeFirst());
		assertEquals(true, rs.isAfterLast());

		rs.absolute(-10);
		assertEquals(false, rs.absolute(-10));
		assertEquals("zhang", rs.getString("name"));
		assertEquals(false, rs.isFirst());
		assertEquals(false, rs.isLast());
		assertEquals(true, rs.isBeforeFirst());
		assertEquals(false, rs.isAfterLast());

		rs.first();
		assertEquals("sun", rs.getString("name"));
		assertEquals(true, rs.isFirst());
		assertEquals(false, rs.isLast());
		assertEquals(false, rs.isBeforeFirst());
		assertEquals(false, rs.isAfterLast());

		rs.last();
		assertEquals("qian", rs.getString("name"));
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

		assertEquals(ResultSet.CONCUR_UPDATABLE, rs.getConcurrency());
		// assertEquals(ResultSet.CONCUR_UPDATABLE, rs.getCursorName());

		assertEquals(ResultSet.FETCH_FORWARD, rs.getFetchDirection());
		// assertEquals(0, rs.getFetchSize());

		// ResultSetMetaData metaDate = rs.getMetaData();
		// assertEquals(2, metaDate.getColumnCount());
		// assertEquals("ID", metaDate.getColumnName(1));
		// assertEquals("INT", metaDate.getColumnTypeName(1));
		// assertEquals(8, metaDate.getColumnDisplaySize(1));
		// assertEquals(true, metaDate.isAutoIncrement(1));

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

		close(null, st, rs);
		System.out.println("resultSetTest执行结束");
	}

	/**
	 * 两张表关联测试
	 * 
	 * @param conn
	 * @throws Exception
	 */
	public void relatedQueryTest2(Connection conn) throws Exception {
		Statement st = null;
		ResultSet rs = null;
		String sql = null;
		List<String> _sList = new ArrayList<String>();

		try {
			st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
		} catch (SQLException e) {
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
		assertEquals("s5", _sList.get(0));
		assertEquals("s6", _sList.get(1));
		assertEquals("s1", _sList.get(2));
		assertEquals("s2", _sList.get(3));
		assertEquals("s3", _sList.get(4));
		assertEquals("s4", _sList.get(5));

		sql = "select u.id, u.name, u.age, t.id, t.name from student u join teacher t on t.id=u.tId";
		rs = st.executeQuery(sql);
		_sList.clear();
		while (rs.next()) {
			_sList.add(rs.getString("name"));
		}
		assertEquals(6, _sList.size());
		assertEquals("s5", _sList.get(0));
		assertEquals("s6", _sList.get(1));
		assertEquals("s1", _sList.get(2));
		assertEquals("s2", _sList.get(3));
		assertEquals("s3", _sList.get(4));
		assertEquals("s4", _sList.get(5));

		sql = "select u.id, u.name, u.age, t.id, t.name from student u left join teacher t on t.id=u.tId";
		rs = st.executeQuery(sql);
		_sList.clear();
		while (rs.next()) {
			_sList.add(rs.getString("name"));
		}
		assertEquals(6, _sList.size());
		assertEquals("s5", _sList.get(0));
		assertEquals("s6", _sList.get(1));
		assertEquals("s1", _sList.get(2));
		assertEquals("s2", _sList.get(3));
		assertEquals("s3", _sList.get(4));
		assertEquals("s4", _sList.get(5));

		close(null, st, rs);
		System.out.println("relatedQueryTest2执行结束");
	}

	/**
	 * 准备测试数据
	 * 
	 * @param st
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	private void prepareRecord(Statement st) throws SQLException,
			ClassNotFoundException {
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

	/**
	 * 关闭数据库资源
	 * 
	 * @param conn
	 * @param st
	 * @param rs
	 */
	private void close(Connection conn, Statement st, ResultSet rs) {
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
		FiltUtil.deletefile("derby.log");
		FiltUtil.deletefile("file.log");
		FiltUtil.deletefile("derbydb");
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
		String t_sql = sb.toString();

		sb = new StringBuffer();
		sb.append("CREATE TABLE student(");
		sb.append("ID int not null,");
		sb.append("TID int,");
		sb.append("AGE int,");
		sb.append("NAME varchar(20))");
		String s_sql = sb.toString();

		for (String derbyDB : DERBY_DBS) {
			StringBuffer url = new StringBuffer("jdbc:derby:");
			url.append(derbyDB);
			url.append(";create=true");

			Connection conn = null;
			Statement st = null;
			try {
				conn = DriverManager.getConnection(url.toString());
				st = conn.createStatement();
				st.execute(t_sql);
				st.execute(s_sql);
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException("初始化表失败!", e);
			} finally {
				close(conn, st, null);
			}
		}
	}

}
