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
package org.tinygroup.urlrestful.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.tinygroup.commons.tools.CollectionUtil;
import org.tinygroup.commons.tools.StringUtil;
import org.tinygroup.urlrestful.Context;
import org.tinygroup.urlrestful.UrlRestfulManager;
import org.tinygroup.urlrestful.ValueConverter;
import org.tinygroup.urlrestful.valueparser.DefaultValueConverter;
import org.tinygroup.weblayer.WebContext;
import org.tinygroup.weblayer.webcontext.parser.valueparser.ParameterParser;
import org.tinygroup.weblayer.webcontext.rewrite.RewriteSubstitutionContext;
import org.tinygroup.weblayer.webcontext.rewrite.RewriteSubstitutionHandler;

/**
 * restful风格字符串替换处理
 * 
 * @author renhui
 */
public class RestfulStyleSubstitutionHandler implements
		RewriteSubstitutionHandler {

	private static final String Accept = "Accept";
	private static final String HTTP_METHOD_KEY = "X-HTTP-METHOD-OVERRIDE";

	private UrlRestfulManager urlRestfulManager;

	private List<ValueConverter> converters = new ArrayList<ValueConverter>();

	public void setUrlRestfulManager(UrlRestfulManager urlRestfulManager) {
		this.urlRestfulManager = urlRestfulManager;
	}

	public void addConvert(ValueConverter converter) {
		converters.add(converter);
	}

	public void removeConvert(ValueConverter converter) {
		converters.remove(converter);
	}

	/**
	 * 先获取原来的请求路径与UrlRestful的配置进行匹配，把匹配的值放到上下文中，最后重新设置请求的路径。
	 */
	public void postSubstitution(RewriteSubstitutionContext context) {
		String orignalPath = context.getPath();
		String httpMethod = getHttpMethod(context);
		String requestAccept = context.getParserWebContext().get(Accept);
		Context restfulContext = urlRestfulManager.getContext(orignalPath,
				httpMethod, requestAccept);
		if (restfulContext != null) {
			ParameterParser parameterParser = context.getParameters();
			setParameter(parameterParser, restfulContext.getVariableMap());
			context.setPath(restfulContext.getMappingUrl());
		}

	}

	private String getHttpMethod(RewriteSubstitutionContext context) {
		WebContext webContext = context.getParserWebContext();
		String httpMethod = webContext.get(HTTP_METHOD_KEY);
		if (StringUtil.isBlank(httpMethod)) {
			httpMethod = webContext.getRequest().getMethod();
		}
		return httpMethod;
	}

	private void setParameter(ParameterParser parameterParser,
			Map<String, String> variableMap) {
		if (!CollectionUtil.isEmpty(variableMap)) {
			for (String key : variableMap.keySet()) {
				String value = variableMap.get(key);
				if (!StringUtil.isBlank(value)) {
					ValueConverter converter = findConvert(value);
					parameterParser.setObject(key, converter.convert(value));
				}
			}
		}
	}

	private ValueConverter findConvert(String value) {
		for (ValueConverter converter : converters) {
			if (converter.isMatch(value)) {
				return converter;
			}
		}
		return new DefaultValueConverter();
	}
}
