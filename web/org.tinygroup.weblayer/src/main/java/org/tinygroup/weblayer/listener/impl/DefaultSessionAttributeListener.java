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

import org.tinygroup.weblayer.listener.TinySessionAttributeListener;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

public class DefaultSessionAttributeListener extends SimpleBasicTinyConfigAware implements
		TinySessionAttributeListener {
	
	private HttpSessionAttributeListener sessionAttributeListener;
	
	public DefaultSessionAttributeListener(
			HttpSessionAttributeListener sessionAttributeListener) {
		super();
		this.sessionAttributeListener = sessionAttributeListener;
	}

	public void attributeAdded(HttpSessionBindingEvent se) {
		sessionAttributeListener.attributeAdded(se);
	}

	public void attributeRemoved(HttpSessionBindingEvent se) {
		sessionAttributeListener.attributeRemoved(se);
	}

	public void attributeReplaced(HttpSessionBindingEvent se) {
		sessionAttributeListener.attributeReplaced(se);
	}

	public int getOrder() {
		// TODO Auto-generated method stub
		return 0;
	}

}
