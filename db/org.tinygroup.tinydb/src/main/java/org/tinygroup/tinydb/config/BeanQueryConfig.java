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
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.ArrayList;
import java.util.List;

/**
 * bean的查询配置
 * @author renhui
 *
 */
@XStreamAlias("bean-query-config")
public class BeanQueryConfig {
    @XStreamAsAttribute
    @XStreamAlias("bean-type")
	private String beanType;
    @XStreamImplicit
    private List<ConditionConfig> conditionConfigs;
    @XStreamImplicit
    private List<GroupByConfig> groupByConfigs;
    @XStreamImplicit
    private List<OrderByConfig> orderByConfigs;

	public String getBeanType() {
		return beanType;
	}

	public void setBeanType(String beanType) {
		this.beanType = beanType;
	}

	public List<ConditionConfig> getConditionConfigs() {
		if(conditionConfigs==null){
			conditionConfigs=new ArrayList<ConditionConfig>();
		}
		return conditionConfigs;
	}

	public void setConditionConfigs(List<ConditionConfig> conditionConfigs) {
		this.conditionConfigs = conditionConfigs;
	}

	public List<GroupByConfig> getGroupByConfigs() {
		if(groupByConfigs==null){
			groupByConfigs=new ArrayList<GroupByConfig>();
		}
		return groupByConfigs;
	}

	public void setGroupByConfigs(List<GroupByConfig> groupByConfigs) {
		this.groupByConfigs = groupByConfigs;
	}

	public List<OrderByConfig> getOrderByConfigs() {
		if(orderByConfigs==null){
			orderByConfigs=new ArrayList<OrderByConfig>();
		}
		return orderByConfigs;
	}

	public void setOrderByConfigs(List<OrderByConfig> orderByConfigs) {
		this.orderByConfigs = orderByConfigs;
	}

}
