package org.tinygroup.springmerge;

import java.util.List;
import java.util.Map;

public class MergeObject {
	
	private String suffix;
	
	private Map<String, String> paramsMap;
	
	private List<String> paramList;

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
	
}
