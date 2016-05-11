package org.tinygroup.indexinstaller.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.tinygroup.commons.tools.CollectionUtil;
import org.tinygroup.fulltext.exception.FullTextException;
import org.tinygroup.indexinstaller.IndexDataSource;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.xmlparser.node.XmlNode;

/**
 * 基于SQL的JDBC驱动数据源
 * 
 * @author yancheng11334
 * 
 */
public class SqlDataSource extends AbstractIndexDataSource implements
		IndexDataSource<XmlNode> {

	public String getType() {
		return "db";
	}

	public void install(XmlNode config) {
		LOGGER.logMessage(LogLevel.INFO, "开始安装数据库类型的索引数据源...");
		List<XmlNode> sqls = config.getSubNodes("sql");
		if (CollectionUtil.isEmpty(sqls)) {
			LOGGER.logMessage(LogLevel.WARN, "本数据库节点没有找到查询SQL!");
		} else {
			String driver = config.getAttribute("driver");
			String url = config.getAttribute("url");
			String user = config.getAttribute("user");
			String password = config.getAttribute("password");
			Connection conn = getConnection(driver, url, user, password);
			try {
				for (XmlNode sql : sqls) {
					LOGGER.logMessage(LogLevel.INFO, "开始处理语句{0}",
							sql.getContent());
					installSql(conn, sql.getContent(),
							sql.getAttribute("type"), sql.getAttribute("id"));
					LOGGER.logMessage(LogLevel.INFO, "处理语句{0}结束!",
							sql.getContent());
				}
			}  finally {
				if (conn != null) {
					try {
						conn.close();
					} catch (SQLException e) {
						throw new FullTextException("释放Connection资源发生异常:", e);
					}
				}
			}

		}
		LOGGER.logMessage(LogLevel.INFO, "安装数据库类型的索引数据源结束!");
	}

	private void installSql(Connection conn, String sql, String type, String id) {
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			getFullText().createIndex(type, rs, id);
		} catch (FullTextException e) {
			throw e;
		} catch (SQLException e) {
			throw new FullTextException(String.format("数据库操作发生异常:执行[%s]", sql),
					e);
		} catch (Exception e) {
			throw new FullTextException(String.format(
					"安装数据库索引异常:索引类型[%s],主键字段[%s]", type, id), e);
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					throw new FullTextException("释放Statement资源发生异常:", e);
				}
			}
		}
	}

	private Connection getConnection(String driver, String url, String user,
			String password) {
		try {
			Class.forName(driver);
			return DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			throw new FullTextException(String.format("加载驱动类[%s]发生异常", driver),
					e);
		} catch (SQLException e) {
			throw new FullTextException(String.format(
					"DriverManager加载Connection失败:数据库地址[%s],用户名[%s],密码[%s]",
					url, user, password), e);
		} catch (Exception e) {
			throw new FullTextException(String.format(
					"获得数据库Connection发生异常:驱动名[%s],数据库地址[%s],用户名[%s],密码[%s]",
					driver, url, user, password), e);
		}
	}
}
