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
package org.tinygroup.springmvc.support;

import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.tinygroup.springmvc.coc.ConventionHelper;
import org.tinygroup.springmvc.coc.impl.RestfulConventionHandlerMethodResolver;

/**
 * 约定处理handlerMethod时 指定view路径不用全路径
 * 
 */
public class ConventionModelAndViewResolver extends
		AbstractConventionModelAndViewResolver {

	protected String getViewPath(NativeWebRequest webRequest,
			String requestUri, String viewName) {
		String restPath = (String) webRequest
				.getAttribute(
						RestfulConventionHandlerMethodResolver.RESTFUL_CONVENTION_VIEW_PATH,
						WebRequest.SCOPE_REQUEST);
		if (restPath != null) {
			String resourceName = (String) webRequest.getAttribute(
					ConventionHelper.CONVENTION_RESOURCE_NAME,
					WebRequest.SCOPE_REQUEST);
			int pos = requestUri.lastIndexOf(resourceName);
			return requestUri.toLowerCase().substring(0,
					pos + resourceName.length())
					+ "/" + viewName;
		} else {
			return super.getViewPath(webRequest, requestUri, viewName);
		}
	}
}
