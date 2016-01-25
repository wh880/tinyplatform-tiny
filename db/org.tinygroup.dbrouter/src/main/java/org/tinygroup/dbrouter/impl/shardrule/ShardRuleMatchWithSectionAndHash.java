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
package org.tinygroup.dbrouter.impl.shardrule;

import groovy.lang.Binding;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyShell;
import groovy.lang.Script;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.tinygroup.dbrouter.config.Partition;
import org.tinygroup.dbrouter.util.ConsistentHash;

public class ShardRuleMatchWithSectionAndHash extends ShardRuleMatchWithSections {

	private String targetTableName;
	private ConsistentHash<String> consistentHash;
	private Class<?> scriptClazz;
	private String expression;
	private final static ConcurrentHashMap<String, Class<?>> scriptClazzMap = new ConcurrentHashMap<String, Class<?>>();

	public ShardRuleMatchWithSectionAndHash(List<Section> sections,
			String tableName, String targetTableName,
			ConsistentHash<String> consistentHash, String fieldName,
			String expression, Partition partition, Object[] preparedParams) {
		super(sections, tableName, fieldName, partition, preparedParams);
		this.targetTableName = (null == targetTableName || ""
				.equals(targetTableName.trim())) ? tableName : targetTableName;
		this.consistentHash = consistentHash;
		this.expression = expression;
		if (null != expression && !"".equals(expression.trim())) {
			scriptClazz = scriptClazzMap.get(expression);
			if (null == scriptClazz) {
				GroovyClassLoader loader = new GroovyClassLoader();
				scriptClazz = loader.parseClass(expression);
				scriptClazzMap.putIfAbsent(expression, scriptClazz);
			}
		}
	}

	protected boolean valueMatch(String paramValue) {
		long value = dealExpression(paramValue);
		if (isInScope(sections, value)
				&& consistentHash.getShardInfo(String.valueOf(value)).equals(
						targetTableName)) {
			return true;
		}

		return false;
	}
	
	private long dealExpression(String value) {
		if (null != this.scriptClazz) {
			Binding binding = new Binding();
			binding.setVariable("value", value);
			Script script = null;
			try {
				script = (Script) scriptClazz.newInstance();
			} catch (Exception e) {
				GroovyShell shell = new GroovyShell();
				script = shell.parse(expression);
			}
			script.setBinding(binding);
			return (Long) script.run();
		}
		return Long.parseLong(value);
	}

}
