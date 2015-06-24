package org.tinygroup.springmvc.extension.impl;

import org.springframework.web.util.UrlPathHelper;
import org.tinygroup.springmvc.extension.FileExtensionResolver;
import org.tinygroup.springmvc.util.WebUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;

/**
 * 
 * @author renhui
 *
 */
public class RequestURIFileExtensionResolver implements
		FileExtensionResolver<HttpServletRequest> {

	private UrlPathHelper urlPathHelper = new UrlPathHelper();

	public UrlPathHelper getUrlPathHelper() {
		return urlPathHelper;
	}

	public void setUrlPathHelper(UrlPathHelper urlPathHelper) {
		this.urlPathHelper = urlPathHelper;
	}

	public List<String> resolveFileExtensions(HttpServletRequest request) {
		UrlPathHelper urlPathHelper = getUrlPathHelper();
		String path = urlPathHelper.getLookupPathForRequest(request);
		String targetExt = WebUtil.getExtension(path);
		if (null == targetExt) {
			return null;
		}
		return Collections.singletonList(targetExt);
	}

	public boolean isSupport(HttpServletRequest t) {
		return t instanceof HttpServletRequest;
	}
}
