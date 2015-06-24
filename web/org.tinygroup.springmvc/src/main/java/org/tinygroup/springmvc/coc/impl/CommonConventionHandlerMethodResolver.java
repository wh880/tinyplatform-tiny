package org.tinygroup.springmvc.coc.impl;

import org.apache.commons.lang.StringUtils;
import org.tinygroup.commons.tools.StringUtil;
import org.tinygroup.springmvc.coc.ConventionHelper;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 基础路径+方法名作为注册的url
 * @author renhui
 *
 */
public class CommonConventionHandlerMethodResolver extends
		AbstractConventionHandlerMethodResolver {

	public CommonConventionHandlerMethodResolver(Class<?> handlerType,
			ConventionHelper conventionHelper) {
		super(handlerType, conventionHelper);
	}

	@Override
	protected List<String> doResolve(String uriBase, Method method) {
		List<String> urlKeys= new ArrayList<String>(2);
		String rawUrlKey = new StringBuilder(uriBase).append("/")
				.append(method.getName()).toString();
		registerHandlerMethod(rawUrlKey, method);
		urlKeys.add(rawUrlKey);
		// 如果是驼峰形式的方法名，兼容以“_”形式来匹配
		String urlKey = StringUtil.toLowerCaseWithUnderscores(rawUrlKey);
		if (!StringUtils.equals(rawUrlKey, urlKey)) {
			registerHandlerMethod(urlKey, method);
			urlKeys.add(urlKey);
		}
		return urlKeys;
	}

}
