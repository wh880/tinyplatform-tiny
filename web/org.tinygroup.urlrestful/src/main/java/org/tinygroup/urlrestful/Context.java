package org.tinygroup.urlrestful;

import java.util.Map;

import org.tinygroup.urlrestful.config.Mapping;
import org.tinygroup.urlrestful.config.Rule;


/**
 * restful url映射处理的上下文
 * @author renhui
 *
 */
public class Context {
	/**
	 * 查找到的UrlMapping
	 */
	private Mapping mapping;
    /**
     * 路径匹配得到的变量
     */
	private Map<String, String> variableMap;
	
	private Rule rule;
	
	public Context(Rule rule, Mapping mapping,
                   Map<String, String> variableMap) {
		super();
		this.mapping = mapping;
		this.variableMap = variableMap;
		this.rule = rule;
	}

	public Mapping getMapping() {
		return mapping;
	}

	public void setMapping(Mapping mapping) {
		this.mapping = mapping;
	}

	public Map<String, String> getVariableMap() {
		return variableMap;
	}

	public void setVariableMap(Map<String, String> variableMap) {
		this.variableMap = variableMap;
	}

	public Rule getRule() {
		return rule;
	}

	public void setRule(Rule rule) {
		this.rule = rule;
	}

	public String getMappingUrl() {
		return mapping.getUrl();
	}
	
}
