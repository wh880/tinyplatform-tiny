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
package org.tinygroup.templateweb;

import org.tinygroup.context.impl.ContextImpl;
import org.tinygroup.template.TemplateContext;
import org.tinygroup.weblayer.WebContext;

/**
 * tinywebcontext包装的模板上下文
 * 
 * @author renhui
 * 
 */
public class TinyWebTemplateContext extends ContextImpl implements
		TemplateContext {

	private WebContext webContext;

	public TinyWebTemplateContext(WebContext webContext) {
		this.webContext = webContext;
	}

	@SuppressWarnings("unchecked")
	public <T> T get(String name) {
		T value=(T)super.get(name);
		if(value!=null){
			return value;
		}
		return (T)webContext.get(name);
	}

	public WebContext getWebContext() {
		return webContext;
	}

	@Override
	public boolean exist(String name) {
		boolean exist=super.exist(name);
		if(exist){
			return true;
		}
		return webContext.exist(name);
	}
	
	
}
