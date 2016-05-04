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
package org.tinygroup.weblayer.mvc;

import org.springframework.core.ParameterNameDiscoverer;
import org.tinygroup.beancontainer.BeanContainerFactory;
import org.tinygroup.commons.tools.ClassUtil;
import org.tinygroup.commons.tools.ReflectionUtils;
import org.tinygroup.commons.tools.ValueUtil;
import org.tinygroup.context2object.fileresolver.GeneratorFileProcessor;
import org.tinygroup.context2object.impl.ClassNameObjectGenerator;
import org.tinygroup.loader.LoaderManager;
import org.tinygroup.springutil.AnnotationParameterNameDiscoverer;
import org.tinygroup.weblayer.WebContext;
import org.tinygroup.weblayer.mvc.annotation.View;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;

/**
 * 
 * 功能说明: 根据http请求获取本次请求的处理相关对象
 * <p>
 * 
 * 开发人员: renhui <br>
 * 开发时间: 2013-4-22 <br>
 * <br>
 */
public class HandlerExecutionChain {

	private MappingClassModel model;

	private MappingMethodModel methodModel;

	private WebContext context;
	
	private ParameterNameDiscoverer discoverer=new AnnotationParameterNameDiscoverer();

	public HandlerExecutionChain(MappingClassModel model,
			MappingMethodModel methodModel) {
		super();
		this.model = model;
		this.methodModel = methodModel;
	}

	public MappingClassModel getModel() {
		return model;
	}

	public void setModel(MappingClassModel model) {
		this.model = model;
	}

	public MappingMethodModel getMethodModel() {
		return methodModel;
	}

	public void setMethodModel(MappingMethodModel methodModel) {
		this.methodModel = methodModel;
	}

	public WebContext getContext() {
		return context;
	}

	public void setContext(WebContext context) {
		this.context = context;
	}

	public void execute() throws ServletException, IOException, ClassNotFoundException {
		Method method = methodModel.getMapMethod();
		Class[] paramTypes = method.getParameterTypes();
		Object[] args = new Object[paramTypes.length];
		String[] parameterNames = discoverer.getParameterNames(method);
		for (int i = 0; i < paramTypes.length; i++) {
			args[i] = context.get(parameterNames[i]);
			Class type = paramTypes[i];
			if (args[i] == null) {
				if (type.equals(WebContext.class)) {
					args[i] = context;
				} else {
					ClassNameObjectGenerator generator = BeanContainerFactory
							.getBeanContainer(this.getClass().getClassLoader())
							.getBean(
									GeneratorFileProcessor.CLASSNAME_OBJECT_GENERATOR_BEAN);
					// TODO:此处应该还要考虑数据的情况
					if (Collection.class.isAssignableFrom(type)) {
						ParameterizedType pt = (ParameterizedType) (method
								.getGenericParameterTypes()[i]);
						Type[] actualTypeArguments = pt
								.getActualTypeArguments();
						ClassLoader loader = LoaderManager
								.getLoader(getClassName(actualTypeArguments[0]
										.toString()));
						args[i] = generator
								.getObjectCollection(null, type.getName(),
										getClassName(actualTypeArguments[0]
												.toString()), loader, context);
					} else {
						ClassLoader loader = LoaderManager.getLoader(type
								.getName());
						args[i] = generator.getObject(null, null,
								type.getName(), loader, context);
					}
				}
			} else {
				if(ClassUtil.isBasicClass(args[i].getClass())){
					args[i] = ValueUtil
							.getValue(args[i].toString(), type.getName());
				}
			}
			context.put(parameterNames[i], args[i]);

		}
		Object object = getInstance(model.getMapClass());
		if (object instanceof WebContextAware) {
			((WebContextAware) object).setContext(context);
		}
		Object result = ReflectionUtils.invokeMethod(method, object, args);
		// 如果返回值类型void或者用户没有设置ResultKey，则不放置
		if (result != null && methodModel.getResultKey() != null) {
			context.put(methodModel.getResultKey().value(), result);
		}
		View view = methodModel.getView();
		if (view != null) {
			String pathInfo = view.value();
			HttpServletRequest request = context.getRequest();
			request.getRequestDispatcher(pathInfo).forward(request,
					context.getResponse());
		}
	}

	private Object getInstance(Class mapClass) {
		Object object = null;
		try {
			object = BeanContainerFactory.getBeanContainer(
					this.getClass().getClassLoader()).getBean(mapClass);
		} catch (Exception e) {
			if (object == null) {
				try {
					object = model.getMapClass().newInstance();
				} catch (Exception e1) {
					throw new IllegalStateException("实例化出错", e1);
				}
			}
		}
		return object;
	}

	private String getClassName(String rawType) {
		if (rawType == null || "".equals(rawType)) {
			throw new RuntimeException("被解析的字符串为空");
		} else {
			if (rawType.startsWith("interface ")) {
				return rawType.substring("interface ".length());
			} else if (rawType.startsWith("class ")) {
				return rawType.substring("class ".length());
			} else {
				return rawType;
			}
		}
	}
	
}
