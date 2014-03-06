package org.tinygroup.dbrouterjdbc3.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import junit.framework.TestCase;

public class CommonJDBCTest extends TestCase {
	public void testCommonJDBC() throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");
		Connection connection = DriverManager.getConnection(
				"jdbc:mysql://192.168.51.29:3306/testA", "root", "123456");
		Statement statement = connection.createStatement(
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE,
				102);
		ResultSet rs = null;

		statement.executeUpdate("delete from teacher");
		statement
				.executeUpdate("insert into teacher(id,name) values(1,'zhang')");
		statement
				.executeUpdate("insert into teacher(id,name) values(2,'qian')");
		statement.executeUpdate("insert into teacher(id,name) values(3,'sun')");
		statement
				.executeUpdate("insert into teacher(id,name) values(4,'wang')");
		statement
				.executeUpdate("insert into teacher(id,name) values(5,'chen')");

		rs = statement
				.executeQuery("select avg(id),sum(id),max(id),min(id) from teacher");
		rs.first();
		assertEquals(3, rs.getInt(1));
		assertEquals(15, rs.getInt(2));
		assertEquals(5, rs.getInt(3));
		assertEquals(1, rs.getInt(4));
	}
}
