package org.tinygroup.jdbctemplatedslsession.provider;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.tinygroup.jdbctemplatedslsession.PrimaryKeyProvider;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;

/**
 * 
 * @author renhui
 * 
 */
public class DefaultPrimaryKeyProvider implements PrimaryKeyProvider {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(DefaultPrimaryKeyProvider.class);

	public String[] generatedKeyNamesWithMetaData(DataSource dataSource,
			String catalogName, String schemaName, String tableName) {
		Connection con = null;
		try {
			con = DataSourceUtils.getConnection(dataSource);
			if (con == null) {
				// should only happen in test environments
				throw new DataAccessResourceFailureException(
						"Connection returned by DataSource [" + dataSource
								+ "] was null");
			}
			DatabaseMetaData databaseMetaData = con.getMetaData();
			if (databaseMetaData == null) {
				// should only happen in test environments
				throw new DataAccessResourceFailureException(
						"DatabaseMetaData returned by Connection [" + con
								+ "] was null");
			}
			boolean storesUpperCaseIdentifiers = false;
			try {
				storesUpperCaseIdentifiers = databaseMetaData
						.storesUpperCaseIdentifiers();
			} catch (SQLException se) {
				LOGGER.logMessage(
						LogLevel.WARN,
						"Error retrieving 'DatabaseMetaData.storesUpperCaseIdentifiers' - {0}",
						se.getMessage());
			}
			boolean storesLowerCaseIdentifiers = false;
			try {
				storesLowerCaseIdentifiers = databaseMetaData
						.storesLowerCaseIdentifiers();
			} catch (SQLException se) {
				LOGGER.logMessage(
						LogLevel.WARN,
						"Error retrieving 'DatabaseMetaData.storesLowerCaseIdentifiers' -{0} ",
						se.getMessage());
			}
			ResultSet tables = null;
			List<String> columnNames = new ArrayList<String>();
			try {
				tables = databaseMetaData.getPrimaryKeys(
						nameToUse(catalogName, storesUpperCaseIdentifiers,
								storesLowerCaseIdentifiers),
						nameToUse(schemaName, storesUpperCaseIdentifiers,
								storesLowerCaseIdentifiers),
						nameToUse(tableName, storesUpperCaseIdentifiers,
								storesLowerCaseIdentifiers));
				while (tables != null && tables.next()) {
					columnNames.add(tables.getString("COLUMN_NAME"));
				}
			} catch (SQLException se) {
				LOGGER.logMessage(LogLevel.WARN,
						"Error while accessing table meta data results:{0}",
						se.getMessage());
			} finally {
				if (tables != null) {
					try {
						tables.close();
					} catch (SQLException e) {
						LOGGER.logMessage(
								LogLevel.WARN,
								"Error while closing table meta data reults {0}",
								e.getMessage());
					}
				}
			}
			return columnNames.toArray(new String[0]);

		} catch (CannotGetJdbcConnectionException ex) {
			throw new DataAccessResourceFailureException(
					"Could not get Connection for extracting meta data", ex);
		} catch (SQLException ex) {
			throw new DataAccessResourceFailureException(
					"Error while extracting DatabaseMetaData", ex);
		} catch (AbstractMethodError err) {
			throw new DataAccessResourceFailureException(
					"JDBC DatabaseMetaData method not implemented by JDBC driver - upgrade your driver",
					err);
		} finally {
			DataSourceUtils.releaseConnection(con, dataSource);
		}

	}

	private String nameToUse(String name, boolean storesUpperCaseIdentifiers,
			boolean storesLowerCaseIdentifiers) {
		if (name == null)
			return null;
		else if (storesUpperCaseIdentifiers)
			return name.toUpperCase();
		else if (storesLowerCaseIdentifiers)
			return name.toLowerCase();
		else
			return name;
	}

}
