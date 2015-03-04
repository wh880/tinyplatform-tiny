package org.tinygroup.urlrestful;

import java.util.Map;

import org.tinygroup.urlrestful.config.UrlMapping;
import org.tinygroup.urlrestful.config.UrlRestful;


/**
 * restful url映射处理的上下文
 * @author renhui
 *
 */
public class RestfulContext {
	/**
	 * 查找到的UrlMapping
	 */
	private UrlMapping urlMapping;
    /**
     * 路径匹配得到的变量
     */
	private Map<String, String> variableMap;
	
	private UrlRestful urlRestful;
	
	public RestfulContext(UrlRestful urlRestful,UrlMapping urlMapping,
			Map<String, String> variableMap) {
		super();
		this.urlMapping = urlMapping;
		this.variableMap = variableMap;
		this.urlRestful = urlRestful;
	}

	public UrlMapping getUrlMapping() {
		return urlMapping;
	}

	public void setUrlMapping(UrlMapping urlMapping) {
		this.urlMapping = urlMapping;
	}

	public Map<String, String> getVariableMap() {
		return variableMap;
	}

	public void setVariableMap(Map<String, String> variableMap) {
		this.variableMap = variableMap;
	}

	public UrlRestful getUrlRestful() {
		return urlRestful;
	}

	public void setUrlRestful(UrlRestful urlRestful) {
		this.urlRestful = urlRestful;
	}

	public String getMappingUrl() {
		return urlMapping.getMappingUrl();
	}
	
}
