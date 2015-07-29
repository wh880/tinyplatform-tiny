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

import org.tinygroup.weblayer.listener.TinySessionListener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class DefaultSessionListener extends SimpleBasicTinyConfigAware
		implements TinySessionListener {

	private HttpSessionListener httpSessionListener;

	public DefaultSessionListener(HttpSessionListener httpSessionListener) {
		super();
		this.httpSessionListener = httpSessionListener;
	}

	public void sessionCreated(HttpSessionEvent se) {
		httpSessionListener.sessionCreated(se);
	}

	public void sessionDestroyed(HttpSessionEvent se) {
		httpSessionListener.sessionDestroyed(se);
	}

	public int getOrder() {
		return DEFAULT_PRECEDENCE;
	}

}
