package org.tinygroup.sequence;

import static org.dbunit.Assertion.assertEquals;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.dbunit.DatabaseUnitException;
import org.dbunit.IDatabaseTester;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.h2.H2Connection;
import org.dbunit.ext.mysql.MySqlConnection;
import org.dbunit.operation.DatabaseOperation;
import org.h2.tools.RunScript;
import org.junit.Before;

/**
 * 使用dbunit进行数据库测试的基类
 * @author renhui
 *
 */
public abstract class AbstractDBUnitTest {

	protected static final DatabaseType CURRENT_DB_TYPE = DatabaseType.H2;

	private static final Map<String, DataSource> DATA_SOURCES = new HashMap<String, DataSource>();

	private final DataBaseEnvironment dbEnv = new DataBaseEnvironment(
			CURRENT_DB_TYPE);

	@Before
	public void createSchema() throws SQLException {
		for (String each : getSchemaFiles()) {
			Connection conn = createDataSource(each).getConnection();
			RunScript.execute(conn, new InputStreamReader(
					AbstractDBUnitTest.class.getClassLoader()
							.getResourceAsStream(each)));
			conn.close();
		}
	}

	@Before
	public final void importDataSet() throws Exception {
		for (String each : getDataSetFiles()) {
			InputStream is = AbstractDBUnitTest.class.getClassLoader()
					.getResourceAsStream(each);
			IDataSet dataSet = new FlatXmlDataSetBuilder()
					.build(new InputStreamReader(is));
			IDatabaseTester databaseTester = new SequenceJdbcDatabaseTester(
					dbEnv.getDriverClassName(),
					dbEnv.getURL(getFileName(each)), dbEnv.getUsername(),
					dbEnv.getPassword());
			databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
			databaseTester.setDataSet(dataSet);
			databaseTester.onSetup();
		}
	}

	protected abstract List<String> getSchemaFiles();

	protected abstract List<String> getDataSetFiles();

	protected final Map<String, DataSource> createDataSourceMap(
			final String dataSourceName) {
		Map<String, DataSource> result = new HashMap<String, DataSource>(
				getDataSetFiles().size());
		for (String each : getDataSetFiles()) {
			result.put(String.format(dataSourceName, getFileName(each)),
					createDataSource(each));
		}
		return result;
	}
	
	protected final List<DataSource> createDataSourceList() {
		List<DataSource> dataSources=new ArrayList<DataSource>(getDataSetFiles().size());
		for (String each : getDataSetFiles()) {
			dataSources.add(createDataSource(each));
		}
		return dataSources;
	}

	private DataSource createDataSource(final String dataSetFile) {
		if (DATA_SOURCES.containsKey(dataSetFile)) {
			return DATA_SOURCES.get(dataSetFile);
		}
		BasicDataSource result = new BasicDataSource();
		result.setDriverClassName(dbEnv.getDriverClassName());
		result.setUrl(dbEnv.getURL(getFileName(dataSetFile)));
		result.setUsername(dbEnv.getUsername());
		result.setPassword(dbEnv.getPassword());
		result.setMaxActive(1000);
		DATA_SOURCES.put(dataSetFile, result);
		return result;
	}

	private String getFileName(final String dataSetFile) {
		String fileName = new File(dataSetFile).getName();
		if (-1 == fileName.lastIndexOf(".")) {
			return fileName;
		}
		return fileName.substring(0, fileName.lastIndexOf("."));
	}

	protected void assertDataSet(final String expectedDataSetFile,
			final Connection connection, final String actualTableName,
			final String sql, final Object... params) throws SQLException,
			DatabaseUnitException {
		Connection conn = connection;
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(sql);
			int i = 1;
			for (Object each : params) {
				ps.setObject(i++, each);
			}
			ITable actualTable = getConnection(connection).createTable(
					actualTableName, ps);
			IDataSet expectedDataSet = new FlatXmlDataSetBuilder()
					.build(new InputStreamReader(AbstractDBUnitTest.class
							.getClassLoader().getResourceAsStream(
									expectedDataSetFile)));
			assertEquals(expectedDataSet.getTable(actualTableName), actualTable);
		} finally {
			if (ps != null) {
				ps.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
	}

	protected void assertDataSet(final String expectedDataSetFile,
			final Connection connection, final String actualTableName,
			final String sql) throws SQLException, DatabaseUnitException {
		Connection conn = connection;
		try {
			ITable actualTable = getConnection(connection).createQueryTable(
					actualTableName, sql);
			IDataSet expectedDataSet = new FlatXmlDataSetBuilder()
					.build(new InputStreamReader(AbstractDBUnitTest.class
							.getClassLoader().getResourceAsStream(
									expectedDataSetFile)));
			assertEquals(expectedDataSet.getTable(actualTableName), actualTable);
		}finally {
			if (conn != null) {
				conn.close();
			}
		}
	}

	private IDatabaseConnection getConnection(final Connection connection)
			throws DatabaseUnitException {
		switch (dbEnv.getDatabaseType()) {
		case H2:
			return new H2Connection(connection, "PUBLIC");
		case MySQL:
			return new MySqlConnection(connection, "PUBLIC");
		default:
			throw new UnsupportedOperationException(dbEnv.getDatabaseType()
					.name());
		}
	}
}