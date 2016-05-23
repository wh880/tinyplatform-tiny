package org.tinygroup.sqlindexsource.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbcp.BasicDataSource;
import org.tinygroup.beancontainer.BeanContainerFactory;
import org.tinygroup.commons.tools.StringUtil;
import org.tinygroup.context.Context;
import org.tinygroup.context.impl.ContextImpl;
import org.tinygroup.fulltext.document.Document;
import org.tinygroup.fulltext.exception.FullTextException;
import org.tinygroup.sqlindexsource.config.SqlConfig;
import org.tinygroup.sqlindexsource.config.SqlConfigs;
import org.tinygroup.templateindex.TemplateIndexOperator;
import org.tinygroup.templateindex.impl.DynamicDocumentCreator;

public class SqlConfigsIndexOperator extends AbstractResultSetOperator
		implements TemplateIndexOperator<SqlConfigs> {

	public List<Document> createDocuments(SqlConfigs config) throws Exception {
		List<Document> documents = new ArrayList<Document>();
		Connection conn = getConnection(config);
		try {
			if (config.getSqlConfigList() != null) {
				for (SqlConfig sqlConfig : config.getSqlConfigList()) {
					dealSqlConfig(conn, sqlConfig, documents);
				}
			}
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return documents;
	}

	// 处理单条SQL片段
	private void dealSqlConfig(Connection conn, SqlConfig sqlConfig,
			List<Document> documents) throws Exception {
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			ResultSet data = stmt.executeQuery(sqlConfig.getStatement());
			ResultSetMetaData rsmd = data.getMetaData();

			while (data.next()) {
				Context context = new ContextImpl();
				for (int i = 1; i <= rsmd.getColumnCount(); i++) {
					String name = rsmd.getColumnName(i);
					updateContext(data, i, name, context);
				}
				DynamicDocumentCreator creator = new DynamicDocumentCreator(
						sqlConfig.getFieldConfigList());
				Document doc = creator.execute(context);
				documents.add(doc);
			}
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
	}

	private Connection getConnection(SqlConfigs config) {
		if (!StringUtil.isEmpty(config.getDataSourceBean())) {
			// 通过bean加载
			try {
				BasicDataSource dataSource = BeanContainerFactory
						.getBeanContainer(getClass().getClassLoader()).getBean(
								config.getDataSourceBean());

				return dataSource.getConnection();
			} catch (Exception e) {
				throw new FullTextException(String.format(
						"加载bean配置[%s]发生异常",
						config.getDataSourceBean()), e);
			}
		} else {
			// 通过DriverManager加载
			try {
				Class.forName(config.getDriver());
			} catch (Exception e) {
				throw new FullTextException(String.format("加载驱动类[%s]发生异常",
						config.getDriver()), e);
			}
			try {
				return DriverManager.getConnection(config.getUrl(),
						config.getUser(), config.getPassword());
			} catch (Exception e) {
				throw new FullTextException(
						String.format(
								"DriverManager加载Connection失败:数据库地址[%s],用户名[%s],密码[%s]",
								config.getUrl(), config.getUser(),
								config.getPassword()), e);
			}
		}
	}

}
