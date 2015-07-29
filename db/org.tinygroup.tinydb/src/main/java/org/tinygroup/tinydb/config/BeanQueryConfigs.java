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
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.ArrayList;
import java.util.List;

/**
 * bean的查询配置
 * @author renhui
 *
 */
@XStreamAlias("bean-query-configs")
public class BeanQueryConfigs {
	@XStreamImplicit
	private List<BeanQueryConfig> queryConfigs;

	public List<BeanQueryConfig> getQueryConfigs() {
		if(queryConfigs==null){
			queryConfigs=new ArrayList<BeanQueryConfig>();
		}
		return queryConfigs;
	}

	public void setQueryConfigs(List<BeanQueryConfig> queryConfigs) {
		this.queryConfigs = queryConfigs;
	}
}
