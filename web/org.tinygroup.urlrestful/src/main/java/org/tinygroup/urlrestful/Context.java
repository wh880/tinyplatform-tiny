/**
 *  Copyright (c) 1997-2013, www.tinygroup.org (tinygroup@126.com).
 *
 *  Licensed under the GPL, Version 3.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.gnu.org/licenses/gpl.html
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
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
