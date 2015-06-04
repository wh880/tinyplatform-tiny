package org.tinygroup.springmvc.handlermapping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.mvc.annotation.DefaultAnnotationHandlerMapping;
import org.tinygroup.springmvc.coc.ConventionHelper;
import org.tinygroup.springmvc.util.WebUtil;
import org.tinygroup.weblayer.WebContext;
import org.tinygroup.weblayer.mvc.WebContextAware;
import org.tinygroup.weblayer.webcontext.util.WebContextUtil;

/**
 * 
 * @author kevin.luy
 * @since 2014年7月19日
 */
public class DefaultCOCUrlHandlerMapping extends
		DefaultAnnotationHandlerMapping {
	private static final Log logger = LogFactory
			.getLog(DefaultCOCUrlHandlerMapping.class);
	private ConventionHelper conventionHelper;

	public void setConventionHelper(ConventionHelper conventionHelper) {
		this.conventionHelper = conventionHelper;
	}

	@Override
	protected String[] determineUrlsForHandler(String beanName) {
		String[] urls = super.determineUrlsForHandler(beanName);
		try {
			// 沒有requestMapping
			Object obj = getApplicationContext().getBean(beanName);
			if (urls == null && conventionHelper.isHandler(obj)) {
				// desicide url
				urls = conventionHelper.determineUrl(obj);
			}
		} catch (Throwable e) {
			if (logger.isWarnEnabled()) {
				logger.warn("resolve the uris mapping for handler err!", e);
			}
		}
		return urls;
	}

	protected Object lookupHandler(String urlPath, HttpServletRequest request)
			throws Exception {
		// 处理约定系列时，如果有后缀，需要忽略

		// Direct match?
		Object handler = getHandlerMap().get(urlPath);
		if (handler != null) {
			// Bean name or resolved handler?
			if (handler instanceof String) {
				String handlerName = (String) handler;
				handler = getApplicationContext().getBean(handlerName);
			}
			validateHandler(handler, request);
			return buildPathExposingHandler(handler, urlPath, urlPath, null);
		}
		// +++ convention match 去除后缀，如果可以直接match上
		String noExtPath = WebUtil.getUriWithoutExtension(urlPath);
		boolean hasExtension = !StringUtils.equals(urlPath, noExtPath);

		// for Pattern match?
		List<String> matchingPatterns = new ArrayList<String>();

		if (hasExtension) {
			// 去掉后缀再尝试下
			handler = getHandlerMap().get(noExtPath);
			if (handler != null) {
				// Bean name or resolved handler?
				if (handler instanceof String) {
					String handlerName = (String) handler;
					handler = getApplicationContext().getBean(handlerName);
				}
				if (logger.isDebugEnabled()) {
					logger.debug("Convention Matching  for request [" + urlPath
							+ "] are " + noExtPath);
				}
				validateHandler(handler, request);
				return buildPathExposingHandler(handler, urlPath, urlPath, null);
			}

			// pattern match
			for (String registeredPattern : getHandlerMap().keySet()) {
				if (getPathMatcher().match(registeredPattern, urlPath)) {
					// 如果是约定开发的，那么容易把 users/1.htm match为
					// users/{id}，需要进一步处理，否则直接匹配返回。
					if (!conventionHelper.isConventional(registeredPattern)) {
						matchingPatterns.add(registeredPattern);
						continue;
					}
				}
				// 解决condition: convention && request path has extension
				if ((hasExtension && getPathMatcher().match(registeredPattern,
						noExtPath))) {
					// if request path has extension ,then add the extension to
					// pattern
					matchingPatterns.add(registeredPattern);
				}
			}
		} else {// 无后缀情况
			// pattern match
			for (String registeredPattern : getHandlerMap().keySet()) {
				if (getPathMatcher().match(registeredPattern, urlPath)) {
					matchingPatterns.add(registeredPattern);
				}
			}
		}
		String bestPatternMatch = null;
		Comparator<String> patternComparator = getPathMatcher()
				.getPatternComparator(urlPath);
		if (!matchingPatterns.isEmpty()) {
			Collections.sort(matchingPatterns, patternComparator);
			if (logger.isDebugEnabled()) {
				logger.debug("Matching patterns for request [" + urlPath
						+ "] are " + matchingPatterns);
			}
			bestPatternMatch = matchingPatterns.get(0);
		}
		if (bestPatternMatch != null) {
			handler = getHandlerMap().get(bestPatternMatch);
			// Bean name or resolved handler?
			if (handler instanceof String) {
				String handlerName = (String) handler;
				handler = getApplicationContext().getBean(handlerName);
			}
			validateHandler(handler, request);

			if (conventionHelper.isConventional(bestPatternMatch)
					&& hasExtension) {
				urlPath = noExtPath;
			}

			String pathWithinMapping = getPathMatcher()
					.extractPathWithinPattern(bestPatternMatch, urlPath);

			// There might be multiple 'best patterns', let's make sure we have
			// the correct URI template variables
			// for all of them
			Map<String, String> uriTemplateVariables = new LinkedHashMap<String, String>();
			for (String matchingPattern : matchingPatterns) {
				if (patternComparator
						.compare(bestPatternMatch, matchingPattern) == 0) {
					uriTemplateVariables.putAll(getPathMatcher()
							.extractUriTemplateVariables(matchingPattern,
									urlPath));
				}
			}
			if (logger.isDebugEnabled()) {
				logger.debug("URI Template variables for request [" + urlPath
						+ "] are " + uriTemplateVariables);
			}
			return buildPathExposingHandler(handler, bestPatternMatch,
					pathWithinMapping, uriTemplateVariables);
		}
		// No handler found...
		return null;
	}

	/**
	 * Validate the given annotated handler against the current request.
	 * 如果handler实现WebContextAware接口,那么就注入请求上下文
	 * 
	 * @see #validateMapping
	 */
	@Override
	protected void validateHandler(Object handler, HttpServletRequest request)
			throws Exception {
		super.validateHandler(handler, request);
		if (handler instanceof WebContextAware) {
			WebContext webContext = WebContextUtil.getWebContext(request);
			WebContextAware webContextAware = (WebContextAware) handler;
			webContextAware.setContext(webContext);
		}
	}
}
