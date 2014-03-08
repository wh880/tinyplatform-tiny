package org.tinygroup.dbrouterjdbc3.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.enhydra.jdbc.standard.StandardXADataSource;

public class CommonJDBCTest {

	private static String driverName = "com.mysql.jdbc.Driver";
	private static String url = "jdbc:mysql://192.168.51.29:3306/testA";
	private static String user = "root";
	private static String password = "123456";
	private static StandardXADataSource dataSource = new StandardXADataSource();

	static {
		try {
			dataSource.setUrl(url);
			dataSource.setDriverName(driverName);
			dataSource.setUser(user);
			dataSource.setPassword(password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void close(Connection conn, Statement st, ResultSet rs) {
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

	public static void main(String[] args) throws ClassNotFoundException,
			SQLException {
		StandardXADataSource dataSource = new StandardXADataSource();
		dataSource.closeFreeConnection();
	}

}
