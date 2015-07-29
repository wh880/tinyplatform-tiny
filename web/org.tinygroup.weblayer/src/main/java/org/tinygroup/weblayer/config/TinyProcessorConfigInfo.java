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
package org.tinygroup.weblayer.config;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.ArrayList;
import java.util.List;

/**
 * TinyProcessor的配置信息
 * 
 * @author renhui
 * 
 */
@XStreamAlias("tiny-processor")
public class TinyProcessorConfigInfo extends BasicConfigInfo {
	@XStreamImplicit
	List<ServletMapping> servletMappings;

	public List<ServletMapping> getServletMappings() {
		if (servletMappings == null) {
			servletMappings = new ArrayList<ServletMapping>();
		}
		return servletMappings;
	}

	public void setServletMappings(List<ServletMapping> servletMappings) {
		this.servletMappings = servletMappings;
	}

	public void combine(TinyProcessorConfigInfo configInfo) {
		getParameterMap().putAll(configInfo.getParameterMap());
		getServletMappings().addAll(configInfo.getServletMappings());
	}
}
