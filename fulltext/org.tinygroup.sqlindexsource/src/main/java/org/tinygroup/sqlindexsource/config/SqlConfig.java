package org.tinygroup.sqlindexsource.config;

import java.util.List;

import org.tinygroup.templateindex.config.IndexFieldConfig;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("sql")
public class SqlConfig {
   
	private String statement;
	
	@XStreamImplicit
	private List<IndexFieldConfig> fieldConfigList;

	public String getStatement() {
		return statement;
	}

	public void setStatement(String statement) {
		this.statement = statement;
	}

	public List<IndexFieldConfig> getFieldConfigList() {
		return fieldConfigList;
	}

	public void setFieldConfigList(List<IndexFieldConfig> fieldConfigList) {
		this.fieldConfigList = fieldConfigList;
	}
	
	
}
