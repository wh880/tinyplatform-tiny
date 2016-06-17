package org.tinygroup.sequence;

import org.dbunit.JdbcDatabaseTester;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.ext.h2.H2DataTypeFactory;
import org.dbunit.ext.mysql.MySqlDataTypeFactory;

public final class SequenceJdbcDatabaseTester extends JdbcDatabaseTester {
    
    private String driverClass;
    
    public SequenceJdbcDatabaseTester(final String driverClass, final String connectionUrl, final String username,
            final String password) throws ClassNotFoundException {
        super(driverClass, connectionUrl, username, password, null);
        this.driverClass = driverClass;
    }
    
    @Override
    public IDatabaseConnection getConnection() throws Exception {
        IDatabaseConnection result = super.getConnection();
        if (org.h2.Driver.class.getName().equals(driverClass)) {
            result.getConfig().setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new H2DataTypeFactory());
        } else if (com.mysql.jdbc.Driver.class.getName().equals(driverClass)) {
            result.getConfig().setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new MySqlDataTypeFactory());
        }
        return result;
    }
}