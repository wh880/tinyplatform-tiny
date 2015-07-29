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
package org.tinygroup.springmvc.support;

import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * WebBindingInitializer的复合对象
 * @author renhui
 *
 */
public class WebBindingInitializerComposite extends ApplicationObjectSupport implements WebBindingInitializer {

	private List<WebBindingInitializer> webBindingInitializerComposite=new ArrayList<WebBindingInitializer>();

	public void setWebBindingInitializerComposite(
			List<WebBindingInitializer> webBindingInitializerComposite) {
		this.webBindingInitializerComposite = webBindingInitializerComposite;
	}

	public void initBinder(WebDataBinder binder, WebRequest request) {
		  for (WebBindingInitializer wbi : webBindingInitializerComposite) {
              wbi.initBinder(binder, request);
          }
	}

}
