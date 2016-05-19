package org.tinygroup.sqlindexsource.config;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.tinygroup.templateindex.config.BaseIndexConfig;
import org.tinygroup.templateindex.config.IndexFieldConfig;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("sql-source")
public class SqlConfigs extends BaseIndexConfig {

	@XStreamAlias("data-source-bean")
	@XStreamAsAttribute
	private String dataSourceBean;

	@XStreamAsAttribute
	private String url;

	@XStreamAsAttribute
	private String driver;

	@XStreamAsAttribute
	private String user;

	@XStreamAsAttribute
	private String password;

	@XStreamImplicit
	private List<SqlConfig> sqlConfigList;

	public String getDataSourceBean() {
		return dataSourceBean;
	}

	public void setDataSourceBean(String dataSourceBean) {
		this.dataSourceBean = dataSourceBean;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<SqlConfig> getSqlConfigList() {
		return sqlConfigList;
	}

	public void setSqlConfigList(List<SqlConfig> sqlConfigList) {
		this.sqlConfigList = sqlConfigList;
	}

	public String getBeanName() {
		return "sqlConfigsIndexOperator";
	}

	public Set<String> getQueryFields() {
		Set<String> fields = new HashSet<String>();
		if (sqlConfigList != null) {
			for (SqlConfig config : sqlConfigList) {
				if (config.getFieldConfigList() != null) {
                    for(IndexFieldConfig fieldConfig:config.getFieldConfigList()){
                    	fields.add(fieldConfig.getIndexName());
                    }
				}
			}
		}
		return fields;
	}

}
