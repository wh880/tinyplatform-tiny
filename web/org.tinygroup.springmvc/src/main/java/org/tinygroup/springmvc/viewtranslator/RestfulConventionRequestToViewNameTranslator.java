package org.tinygroup.springmvc.viewtranslator;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.view.DefaultRequestToViewNameTranslator;
import org.tinygroup.springmvc.coc.ConventionHelper;
import org.tinygroup.springmvc.coc.impl.RestfulConventionHandlerMethodResolver;

/**
 * restful约定开发 默认页面布局处理
 * 
 */
public class RestfulConventionRequestToViewNameTranslator extends
		DefaultRequestToViewNameTranslator {
	private static final ThreadLocal<String> TL_RESOURCE_NAME = new ThreadLocal<String>();
	private static final ThreadLocal<String> TL_PAGE_NAME = new ThreadLocal<String>();

	@Override
	public String getViewName(HttpServletRequest request) {
		// 如果是restful
		String restPath = (String) request
				.getAttribute(RestfulConventionHandlerMethodResolver.RESTFUL_CONVENTION_VIEW_PATH);
		if (restPath != null) {
			TL_RESOURCE_NAME.set((String) request
					.getAttribute(ConventionHelper.CONVENTION_RESOURCE_NAME));
			TL_PAGE_NAME.set(restPath);
		}

		String path = null;
		try {
			path = super.getViewName(request);
		} finally {
			if (restPath != null) {
				TL_RESOURCE_NAME.remove();
				TL_PAGE_NAME.remove();
			}
		}
		return path;
	}

	@Override
	protected String transformPath(String lookupPath) {
		String resourceName = TL_RESOURCE_NAME.get();
		if (resourceName != null) {
			String pageName = TL_PAGE_NAME.get();
			int pos = lookupPath.lastIndexOf(resourceName);
			String prefix = lookupPath.substring(0, pos);
			lookupPath = prefix + resourceName.toLowerCase() + "/" + pageName;
		}
		return super.transformPath(lookupPath);
	}
}
