package org.tinygroup.urlrestful.impl;

import java.util.ArrayList;
import java.util.List;

import org.tinygroup.commons.match.AntPathStringMatcher;
import org.tinygroup.commons.tools.CollectionUtil;
import org.tinygroup.commons.tools.StringUtil;
import org.tinygroup.urlrestful.Context;
import org.tinygroup.urlrestful.UrlRestfulManager;
import org.tinygroup.urlrestful.config.Mapping;
import org.tinygroup.urlrestful.config.Rule;
import org.tinygroup.urlrestful.config.Rules;

/**
 * 
 * @author renhui
 * 
 */
public class UrlRestfulManagerImpl implements UrlRestfulManager {

	private List<Rule> restfulContainer = new ArrayList<Rule>();

	public void addRules(Rules Rules) {
		restfulContainer.addAll(Rules.getRules());
	}

	public void removeRules(Rules Rules) {
		restfulContainer.removeAll(Rules.getRules());
	}

	public Context getContext(String requestPath,
                              String httpMethod, String accept) {
		String requestAccept = accept;
		if (StringUtil.isBlank(requestAccept)) {
			requestAccept = Mapping.TEXT_HTML;
		}
		for (Rule rule : restfulContainer) {
			AntPathStringMatcher matcher = new AntPathStringMatcher(
					rule.getPattern(), requestPath);
			List<Mapping> mappings = rule
					.getUrlMappingsByMethod(httpMethod);
			Mapping mapping = getUrlMapping(requestAccept, mappings);
			if (matcher.matches() && mapping != null) {
				return new Context(rule, mapping,
						matcher.getUriTemplateVariables());
			}
		}
		return null;
	}

	private Mapping getUrlMapping(String requestContentType,
			List<Mapping> mappings) {
		if (!CollectionUtil.isEmpty(mappings)) {
			for (Mapping mapping : mappings) {
				String contentType = mapping.getAccept();
				if (requestContentType != null
						&& requestContentType.indexOf(contentType) != -1) {
					return mapping;
				}
			}
		}
		return null;
	}

}
