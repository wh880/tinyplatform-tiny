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
package org.tinygroup.dict.config;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * dict-load的配置信息对象
 * @author renhui
 *
 */
@XStreamAlias("dict-loaders")
public class DictLoaderConfigs {
    @XStreamImplicit
	private List<DictLoaderConfig> configs;

	public List<DictLoaderConfig> getConfigs() {
		if(configs==null){
			configs=new ArrayList<DictLoaderConfig>();
		}
		return configs;
	}

	public void setConfigs(List<DictLoaderConfig> configs) {
		this.configs = configs;
	}
    
    
	
}
