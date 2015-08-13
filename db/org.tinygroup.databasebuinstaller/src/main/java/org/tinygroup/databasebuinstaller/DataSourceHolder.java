package org.tinygroup.databasebuinstaller;

import javax.sql.DataSource;

import org.tinygroup.beancontainer.BeanContainerFactory;
import org.tinygroup.database.util.DataSourceInfo;

public class DataSourceHolder {
	
	private static DataSource dataSource;
	
	public static void setDataSource(DataSource dataSource){
		DataSourceHolder.dataSource=dataSource;
	}

	public static DataSource getDataSource() {
		if(dataSource==null){
			dataSource = BeanContainerFactory.getBeanContainer(
					DataSourceHolder.class.getClassLoader()).getBean(
					DataSourceInfo.DATASOURCE_NAME);
		}
		return dataSource;
	}

}
