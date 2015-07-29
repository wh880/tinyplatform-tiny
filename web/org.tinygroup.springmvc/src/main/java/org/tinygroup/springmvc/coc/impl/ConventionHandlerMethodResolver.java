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
package org.tinygroup.springmvc.coc.impl;

import org.tinygroup.commons.tools.CollectionUtil;
import org.tinygroup.springmvc.coc.ConventionHelper;
import org.tinygroup.springmvc.coc.CustomHandlerMethodResolver;
import org.tinygroup.springmvc.coc.Qualifier;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 组合使用所有约定的解析器
 * 
 */
public class ConventionHandlerMethodResolver implements
		CustomHandlerMethodResolver {
	private List<CustomHandlerMethodResolver> customHandlerMethodResolvers = new ArrayList<CustomHandlerMethodResolver>(
			2);
	private ConventionHelper conventionHelper;
	private Class<?> handlerType;

	public ConventionHandlerMethodResolver(Class<?> handlerType,
			ConventionHelper conventionHelper) {
		this.conventionHelper = conventionHelper;
		this.handlerType = handlerType;
		// 保持顺序【总体上：先requestMapping,然后Restful约定，再到一般约定】
		customHandlerMethodResolvers
				.add(new RestfulConventionHandlerMethodResolver(handlerType,
						conventionHelper));
		customHandlerMethodResolvers
				.add(new CommonConventionHandlerMethodResolver(handlerType,
						conventionHelper));
	}

	public Method getHandlerMethod(HttpServletRequest request) {
		// 因为解析时，没起作用的resolver会被剔除。所以需要判断下是否空
		if (CollectionUtil.isEmpty(customHandlerMethodResolvers)) {
			return null;
		}

		Method bestMatch = null;
		for (CustomHandlerMethodResolver resolver : customHandlerMethodResolvers) {
			Method mtd = resolver.getHandlerMethod(request);
			if (mtd != null) {
				// return mtd; 不先到先得，而后续为主
				bestMatch = mtd; // 策略，last is best.
			}
		}
		if (bestMatch != null) {
			request.setAttribute(ConventionHelper.CONVENTION_RESOURCE_NAME,
					conventionHelper.getHandlerName(handlerType));
		}
		return bestMatch;
	}

	/*
	 * 启动时，进行handlerMethod的解析
	 */
	public Set<String> resolve() {
		Set<String> urlList = new HashSet<String>();// for this handler
		OnceHandlerMethodQualifier qualifier = new OnceHandlerMethodQualifier();

		Iterator<CustomHandlerMethodResolver> it = customHandlerMethodResolvers
				.iterator();
		while (it.hasNext()) {
			CustomHandlerMethodResolver resolver = it.next();
			if (resolver instanceof AbstractConventionHandlerMethodResolver) {
				((AbstractConventionHandlerMethodResolver) resolver)
						.setQualifier(qualifier);
			}
			Set<String> urls = resolver.resolve();
			if (resolver instanceof AbstractConventionHandlerMethodResolver) {
				Collection<Method> mtds = ((AbstractConventionHandlerMethodResolver) resolver)
						.getAllHandlerMethods();
				if (!CollectionUtil.isEmpty(mtds)) {
					qualifier.resolved.addAll(mtds);
				}
				((AbstractConventionHandlerMethodResolver) resolver)
						.setQualifier(null);// 释放这次解析的qualifier对象引用
			}

			if (!CollectionUtil.isEmpty(urls)) {
				urlList.addAll(urls);
			} else {
				it.remove();// 没有解析出url,则对该handler而言，该resolver没有必要存在了。
			}
		}
		return urlList;
	}

	/**
	 * 
	 * 避免解析一个方法
	 * 
	 * @author kevin.luy
	 * @since 2014年7月23日
	 */
	class OnceHandlerMethodQualifier implements Qualifier {
		Set<Method> resolved = new HashSet<Method>();

		public boolean qualify(Method method) {
			return !resolved.contains(method);
		}

	}

	public Class<?> getHandlerType() {
		return handlerType;
	}

}
