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
package org.tinygroup.weblayer.listener.impl;

import org.tinygroup.weblayer.listener.TinyServletContextListener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 默认的TinyServletContextListener接口实现类，对原生ServletContextListener接口的包装
 * 
 * @author renhui
 * 
 */
public class DefaultServletContextListener extends SimpleBasicTinyConfigAware
		implements TinyServletContextListener {

	private ServletContextListener servletContextListener;

	public DefaultServletContextListener(
			ServletContextListener servletContextListener) {
		this.servletContextListener = servletContextListener;
	}

	public void contextInitialized(ServletContextEvent sce) {
		servletContextListener.contextInitialized(sce);
	}

	public void contextDestroyed(ServletContextEvent sce) {
		servletContextListener.contextDestroyed(sce);
	}

	public int getOrder() {
		return DEFAULT_PRECEDENCE;
	}

}
