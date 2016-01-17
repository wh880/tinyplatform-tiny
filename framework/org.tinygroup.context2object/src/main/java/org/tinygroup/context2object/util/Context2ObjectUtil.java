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
package org.tinygroup.context2object.util;

import org.tinygroup.beancontainer.BeanContainerFactory;
import org.tinygroup.commons.tools.StringUtil;
import org.tinygroup.context.Context;
import org.tinygroup.context2object.fileresolver.GeneratorFileProcessor;
import org.tinygroup.context2object.impl.ClassNameObjectGenerator;
import org.tinygroup.event.Parameter;

import java.util.Collection;

public final class Context2ObjectUtil {
	private Context2ObjectUtil() {

	}

	public static Object getObject(Parameter p, Context context,
			ClassLoader loader) {
		if (context.exist(p.getName()))
			return context.get(p.getName());
		return getObjectByGenerator(p, context, loader);
	}


	public static Object getObjectByGenerator(Parameter parameter,
			Context context, ClassLoader loader) {
		String collectionType = parameter.getCollectionType();// 集合类型
		String paramName = parameter.getName();
		String paramType = parameter.getType();
		ClassNameObjectGenerator generator = BeanContainerFactory
				.getBeanContainer(Context2ObjectUtil.class.getClassLoader())
				.getBean(GeneratorFileProcessor.CLASSNAME_OBJECT_GENERATOR_BEAN);
		if (!StringUtil.isBlank(collectionType)) {// 如果集合类型非空
			return generator.getObjectCollection(paramName, collectionType,
					paramType, loader,context);
		} else if (parameter.isArray()) {// 如果是数组
			return generator.getObjectArray(paramName, paramType, loader,context);
		}
		// 否则就是对象
		if(isSimpleType(paramType)&&!context.exist(paramName)){
			return null;
		}
		return generator.getObject(paramName, paramName, paramType,loader, context);
	}

	public static Collection<Object> getCollectionInstance(String collectionClass,ClassLoader loader){
		ClassNameObjectGenerator generator = BeanContainerFactory
				.getBeanContainer(Context2ObjectUtil.class.getClassLoader())
				.getBean(GeneratorFileProcessor.CLASSNAME_OBJECT_GENERATOR_BEAN);
		return generator.getObjectCollection(collectionClass,loader);
	}

	public static boolean isSimpleType(Class<?> clazz) {
		if (clazz.equals(int.class) || clazz.equals(char.class)
				|| clazz.equals(byte.class) || clazz.equals(boolean.class)
				|| clazz.equals(short.class) || clazz.equals(long.class)
				|| clazz.equals(double.class) || clazz.equals(float.class)) {
			return true;
		}
		return clazz.equals(Integer.class) || clazz.equals(Character.class)
				|| clazz.equals(Byte.class) || clazz.equals(Boolean.class)
				|| clazz.equals(Short.class) || clazz.equals(Long.class)
				|| clazz.equals(Double.class) || clazz.equals(Float.class)
				|| clazz.equals(String.class);
	}
	
	
	public static boolean isSimpleType(String clazz) {
		if (clazz.equals("int") || clazz.equals("char")
				|| clazz.equals("byte") || clazz.equals("boolean")
				|| clazz.equals("short") || clazz.equals("long")
				|| clazz.equals("double") || clazz.equals("float")) {
			return true;
		}
		return clazz.equals("java.lang.Integer") || clazz.equals("java.lang.Character")
				|| clazz.equals("java.lang.Byte") || clazz.equals("java.lang.Boolean")
				|| clazz.equals("java.lang.Short") || clazz.equals("java.lang.Long")
				|| clazz.equals("java.lang.Double") || clazz.equals("java.lang.Float")
				|| clazz.equals("java.lang.String");
	}
}
