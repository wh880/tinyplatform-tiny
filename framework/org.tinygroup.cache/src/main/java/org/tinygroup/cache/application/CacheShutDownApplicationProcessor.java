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
package org.tinygroup.cache.application;

import org.tinygroup.application.AbstractApplicationProcessor;
import org.tinygroup.cache.CacheManager;
import org.tinygroup.xmlparser.node.XmlNode;

/**
 * 关闭缓存的应用处理器,清除內存中的缓存,并把内存中的缓存保存到辅助性缓存中
 * @author renhui
 *
 */
public class CacheShutDownApplicationProcessor extends
		AbstractApplicationProcessor {
	
	private CacheManager cacheManager;
	
	public CacheManager getCacheManager() {
		return cacheManager;
	}

	public void setCacheManager(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	public void start() {
		//do nothing
	}

	public void stop() {
		cacheManager.shutDown();//关闭缓存管理器
	}

	public String getApplicationNodePath() {
		return null;
	}

	public String getComponentConfigPath() {
		return null;
	}

	public void config(XmlNode applicationConfig, XmlNode componentConfig) {
		//do nothing
	}

	public XmlNode getComponentConfig() {
		return null;
	}

	public XmlNode getApplicationConfig() {
		return null;
	}

	public int getOrder() {
		return DEFAULT_PRECEDENCE;
	}

}
