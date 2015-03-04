package org.tinygroup.urlrestful.impl;

import java.util.ArrayList;
import java.util.List;

import org.tinygroup.commons.match.AntPathStringMatcher;
import org.tinygroup.commons.tools.CollectionUtil;
import org.tinygroup.commons.tools.StringUtil;
import org.tinygroup.urlrestful.RestfulContext;
import org.tinygroup.urlrestful.UrlRestfulManager;
import org.tinygroup.urlrestful.config.UrlMapping;
import org.tinygroup.urlrestful.config.UrlRestful;
import org.tinygroup.urlrestful.config.UrlRestfuls;

/**
 * 
 * @author renhui
 * 
 */
public class UrlRestfulManagerImpl implements UrlRestfulManager {

	private List<UrlRestful> restfulContainer = new ArrayList<UrlRestful>();

	public void addUrlRestfuls(UrlRestfuls urlRestfuls) {
		restfulContainer.addAll(urlRestfuls.getUrlRestfuls());
	}

	public void removeRestfuls(UrlRestfuls urlRestfuls) {
		restfulContainer.removeAll(urlRestfuls.getUrlRestfuls());
	}

	public RestfulContext getUrlMappingWithRequet(String requestPath,
			String httpMethod, String accept) {
		String requestAccept = accept;
		if (StringUtil.isBlank(requestAccept)) {
			requestAccept = UrlMapping.TEXT_HTML;
		}
		for (UrlRestful urlRestful : restfulContainer) {
			AntPathStringMatcher matcher = new AntPathStringMatcher(
					urlRestful.getPattern(), requestPath);
			List<UrlMapping> urlMappings = urlRestful
					.getUrlMappingsByMethod(httpMethod);
			UrlMapping urlMapping = getUrlMapping(requestAccept, urlMappings);
			if (matcher.matches() && urlMapping != null) {
				return new RestfulContext(urlRestful, urlMapping,
						matcher.getUriTemplateVariables());
			}
		}
		return null;
	}

	private UrlMapping getUrlMapping(String requestContentType,
			List<UrlMapping> urlMappings) {
		if (!CollectionUtil.isEmpty(urlMappings)) {
			for (UrlMapping urlMapping : urlMappings) {
				String contentType = urlMapping.getAccept();
				if (requestContentType != null
						&& requestContentType.indexOf(contentType) != -1) {
					return urlMapping;
				}
			}
		}
		return null;
	}

}
