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
package org.tinygroup.weblayer.listener.impl;

import org.tinygroup.weblayer.listener.TinySessionActivationListener;

import javax.servlet.http.HttpSessionActivationListener;
import javax.servlet.http.HttpSessionEvent;

public class DefaultSessionActivationListener extends
		SimpleBasicTinyConfigAware implements TinySessionActivationListener {

	private HttpSessionActivationListener sessionActivationListener;

	public DefaultSessionActivationListener(
			HttpSessionActivationListener sessionActivationListener) {
		super();
		this.sessionActivationListener = sessionActivationListener;
	}

	public void sessionWillPassivate(HttpSessionEvent se) {
		sessionActivationListener.sessionWillPassivate(se);
	}

	public void sessionDidActivate(HttpSessionEvent se) {
		sessionActivationListener.sessionDidActivate(se);
	}

	public int getOrder() {
		return DEFAULT_PRECEDENCE;
	}

}
