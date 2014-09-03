package org.tinygroup.tinydb.datasource.unpooled;

import java.util.Map;

import javax.sql.DataSource;

import org.tinygroup.tinydb.datasource.DataSourceFactory;
import org.tinygroup.tinydb.util.TinyDBUtil;

public class UnpooledDataSourceFactory implements DataSourceFactory {


	protected DataSource dataSource;

	public UnpooledDataSourceFactory() {
		this.dataSource = new UnpooledDataSource();
	}

	public void setProperties(Map<String, String> properties) {
		TinyDBUtil.setProperties(dataSource, properties);
	}

	public DataSource getDataSource() {
		return dataSource;
	}

}
