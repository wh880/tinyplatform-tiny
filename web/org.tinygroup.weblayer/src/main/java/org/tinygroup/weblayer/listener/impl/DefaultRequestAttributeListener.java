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

import org.tinygroup.weblayer.listener.TinyRequestAttributeListener;

import javax.servlet.ServletRequestAttributeEvent;
import javax.servlet.ServletRequestAttributeListener;

public class DefaultRequestAttributeListener extends SimpleBasicTinyConfigAware implements
		TinyRequestAttributeListener {
	
	private ServletRequestAttributeListener requestAttributeListener;
	
	public DefaultRequestAttributeListener(
			ServletRequestAttributeListener requestAttributeListener) {
		super();
		this.requestAttributeListener = requestAttributeListener;
	}

	public void attributeAdded(ServletRequestAttributeEvent srae) {
		requestAttributeListener.attributeAdded(srae);
	}

	public void attributeRemoved(ServletRequestAttributeEvent srae) {
		requestAttributeListener.attributeRemoved(srae);
	}

	public void attributeReplaced(ServletRequestAttributeEvent srae) {
		requestAttributeListener.attributeReplaced(srae);
	}

	public int getOrder() {
		return DEFAULT_PRECEDENCE;
	}

}
