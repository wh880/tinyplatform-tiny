package org.tinygroup.springmerge;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class MergeObject2 {
	
	private String suffix;
	
	private Map<String, String> paramsMap;
	
	private List<String> paramList;

	private Set<String> paramSet;

	private Properties properties;

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public Map<String, String> getParamsMap() {
		return paramsMap;
	}

	public void setParamsMap(Map<String, String> paramsMap) {
		this.paramsMap = paramsMap;
	}

	public List<String> getParamList() {
		return paramList;
	}

	public void setParamList(List<String> paramList) {
		this.paramList = paramList;
	}


	public void setParamSet(Set<String> paramSet) {
		this.paramSet = paramSet;
	}

	public Set<String> getParamSet() {
		return paramSet;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	public Properties getProperties() {
		return properties;
	}
}
