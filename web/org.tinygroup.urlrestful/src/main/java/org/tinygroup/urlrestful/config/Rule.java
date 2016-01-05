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
package org.tinygroup.urlrestful.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.CaseInsensitiveMap;
import org.tinygroup.commons.tools.CollectionUtil;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * 保存配置信息的对象
 * 
 * @author renhui
 * 
 */
@XStreamAlias("rule")
public class Rule {
	@XStreamAsAttribute
	private String pattern;

	@XStreamImplicit
	private List<Mapping> mappings;

	private transient Map<String, List<Mapping>> method2Mapping;

	public Rule(String pattern) {
		this.pattern = pattern;
	}

	private void addUrlMapping(Mapping mapping) {
		List<Mapping> mappings = method2Mapping.get(mapping
				.getMethod());
		if (mappings == null) {
			mappings = new ArrayList<Mapping>();
			method2Mapping.put(mapping.getMethod(), mappings);
		}
		mappings.add(mapping);
	}

	public String getPattern() {
		return pattern;
	}

	public List<Mapping> getUrlMappingsByMethod(String method) {
		if(method2Mapping==null){
			init();
		}
		return method2Mapping.get(method);
	}

	public List<Mapping> getMappings() {
		if (mappings == null) {
			mappings = new ArrayList<Mapping>();
		}
		return mappings;
	}

	public void setMappings(List<Mapping> mappings) {
		this.mappings = mappings;
	}

	@SuppressWarnings("unchecked")
	public void init(){
		method2Mapping=new CaseInsensitiveMap(); 
		if (!CollectionUtil.isEmpty(mappings)) {
			for (Mapping mapping : mappings) {
                   addUrlMapping(mapping);
			}
		}
	}
	
	
	@Override
	public int hashCode() {
		return pattern.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (this == obj) {
			return true;
		}
		if (obj instanceof Rule) {
			Rule other = (Rule) obj;
			return other.pattern.equals(this.pattern);
		}
		return false;
	}

}
