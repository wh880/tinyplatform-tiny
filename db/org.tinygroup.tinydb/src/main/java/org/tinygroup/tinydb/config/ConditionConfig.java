/**
 *  Copyright (c) 1997-2013, www.tinygroup.org (luo_guo@icloud.com).
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
package org.tinygroup.tinydb.config;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import org.tinygroup.commons.tools.StringUtil;

@XStreamAlias("condition-config")
public class ConditionConfig {
	@XStreamAlias("property-name")
	@XStreamAsAttribute
	private String propertyName;
	@XStreamAlias("condition-mode")
	@XStreamAsAttribute
	private String conditionMode;
	private static final String DEFAUTL_CONDITION_MODE="equals";
	
	public ConditionConfig() {
		super();
	}
	public ConditionConfig(String propertyName, String conditionMode) {
		super();
		this.propertyName = propertyName;
		this.conditionMode = conditionMode;
	}
	public String getPropertyName() {
		return propertyName;
	}
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	public String getConditionMode() {
		if(StringUtil.isBlank(conditionMode)){
			conditionMode=DEFAUTL_CONDITION_MODE;
		}
		return conditionMode;
	}
	public void setConditionMode(String conditionMode) {
		this.conditionMode = conditionMode;
	}
	
}
