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
