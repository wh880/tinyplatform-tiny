package org.tinygroup.tinydb.datasource;

import java.util.Map;

import javax.sql.DataSource;

public interface DataSourceFactory {

  void setProperties(Map<String, String> properties);

  DataSource getDataSource();

}