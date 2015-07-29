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
package org.tinygroup.cepcoreimpl;

import org.tinygroup.cepcore.CEPCore;
import org.tinygroup.commons.tools.StringUtil;
import org.tinygroup.context.Context;
import org.tinygroup.context.util.ContextFactory;
import org.tinygroup.context2object.config.BasicTypeConverter;
import org.tinygroup.context2object.util.Context2ObjectUtil;
import org.tinygroup.event.Event;
import org.tinygroup.event.Parameter;
import org.tinygroup.event.ServiceInfo;

import java.util.List;

public class ServiceParamUtil {
	private static final String IS_CHANGED = "TINY_IS_EVENT_HAS_CHANGED";

	private ServiceParamUtil() {

	}

	public static void changeEventContext(Event event, CEPCore core,
			ClassLoader loader) {
		Context oldContext = event.getServiceRequest().getContext();
		if (oldContext.exist(IS_CHANGED)) {
			return;
		}
		Context newContext = ContextFactory.getContext();
		newContext.put(IS_CHANGED, true);

		String serviceId = event.getServiceRequest().getServiceId();
		ServiceInfo service = core.getServiceInfo(serviceId);
		List<Parameter> inputs = service.getParameters();
		if (inputs != null) {
			for (Parameter input : inputs) {
				Object value = getParameterValue(loader, oldContext, input);
				newContext.put(input.getName(), value);
			}
		}
		event.getServiceRequest().setContext(newContext);
	}

	private static Object getParameterValue(ClassLoader loader,
			Context oldContext, Parameter input) {
		String inputName = input.getName();
		if (!oldContext.exist(inputName)) {
			// 如果context中不存在该参数，则进行组装
			return Context2ObjectUtil.getObject(input, oldContext, loader);
		} else {
			// 如果存在该参数，则取出参数进行处理
			Object paramValue = oldContext.get(inputName);
			if (paramValue instanceof String[]) {
				// 如果取出来的值是String[],则判断参数类型
				// 1、是否是简单类型数组
				// 2、是否是简单类型的封装类的数组或者集合
				Object value = checkParamType((String[]) paramValue, input,
						loader);
				if (value == null) {
					// 如果返回的为null，则表明参数类型均非以上情况，将无法进行转换
					throw new RuntimeException("无法将参数" + inputName
							+ "的值从String[]转换为" + input.getType() + "数组");
				} else {
					// 顺利返回，即表示已经转换完成
					return value;
				}
			} else if (paramValue instanceof String) {
				// 如果取出来的值是String类型
				// 则对其进行处理
				// 如果目标参数是各种简单类型或者其封装类型，则进行转换
				// 否则不做处理
				return BasicTypeConverter.getValue((String) paramValue,
						input.getType());
			}
			return paramValue; // 既不是String[]也不是String，则直接返回
		}
	}

	private static Object checkParamType(String[] stringArray, Parameter input,
			ClassLoader loader) {
		if (input.isArray()) {
			return checkArrayType(stringArray, input);
		} else if (!StringUtil.isBlank(input.getCollectionType())) {
			return checkCollectionType(stringArray, input, loader);
		}
		return stringArray;
	}

	private static Object checkArrayType(String[] stringArray, Parameter input) {
		return BasicTypeConverter.convertBasicTypeArray(stringArray,
				input.getType());
	}

	private static Object checkCollectionType(String[] stringArray,
			Parameter input, ClassLoader loader) {
		return BasicTypeConverter.convertBasicTypeCollection(stringArray,
				input.getCollectionType(), input.getType(), loader);
	}

	public static void resetEventContext(Event event, CEPCore core,
			Context oldContext) {
		oldContext.remove(IS_CHANGED);
		String serviceId = event.getServiceRequest().getServiceId();
		Context context = event.getServiceRequest().getContext();
		ServiceInfo service = core.getServiceInfo(serviceId);
		List<Parameter> outputs = service.getResults();
		if (outputs != null) {
			for (Parameter output : outputs) {
				String name = output.getName();
				if (context.exist(name)) {
					oldContext.put(name, context.get(name));
				}
			}
		}
		event.getServiceRequest().setContext(oldContext);
	}
}
