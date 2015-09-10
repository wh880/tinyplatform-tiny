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
package org.tinygroup.commons.beanutil;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.commons.beanutils.PropertyUtils;
import org.tinygroup.commons.namediscover.LocalVariableTableParameterNameDiscoverer;
import org.tinygroup.commons.namediscover.ParameterNameDiscoverer;
import org.tinygroup.commons.tools.ClassUtil;

public class BeanUtil {

	private static ParameterNameDiscoverer discoverer = new LocalVariableTableParameterNameDiscoverer();

	public static String[] getMethodParameterName(Class<?> clazz, Method method) {
		return discoverer.getParameterNames(method);
	}

	public static String[] getMethodParameterName(Method method) {
		return discoverer.getParameterNames(method);
	}

	public static String[] getMethodParameterName(Constructor ctor) {
		return discoverer.getParameterNames(ctor);
	}

	public static Object deepCopy(Object orig) throws Exception {
		Object dest = orig.getClass().newInstance();
		PropertyDescriptor[] origDescriptors = PropertyUtils
				.getPropertyDescriptors(orig);
		for (PropertyDescriptor propertyDescriptor : origDescriptors) {
			String name = propertyDescriptor.getName();
			if (PropertyUtils.isReadable(orig, name)
					&& PropertyUtils.isWriteable(dest, name)) {
				Object value = PropertyUtils.getSimpleProperty(orig, name);
				Object valueDest = null;
				if (value != null && canDeepCopyObject(value)) {
					if (value instanceof Collection) {
						Collection coll = (Collection) value;
						Collection newColl = createApproximateCollection(value);
						Iterator it = coll.iterator();
						while (it.hasNext()) {
							newColl.add(deepCopy(it.next()));
						}
						valueDest = newColl;
					} else if (value.getClass().isArray()) {
						Object[] values = (Object[]) value;
						Object[] newValues = new Object[values.length];
						for (int i = 0; i < newValues.length; i++) {
							newValues[i] = deepCopy(values[i]);
						}
						valueDest = newValues;
					} else if (value instanceof Map) {
						Map map = (Map) value;
						Map newMap = createApproximateMap(map);
						for (Object key : map.keySet()) {
							newMap.put(key, deepCopy(map.get(key)));
						}
						valueDest = newMap;
					} else {
						valueDest = deepCopy(value);
					}
				} else {
					valueDest = value;
				}
				PropertyUtils.setSimpleProperty(dest, name, valueDest);
			}

		}
		return dest;

	}

	private static boolean canDeepCopyObject(Object value) {
		if (ClassUtil.getPrimitiveType(value.getClass()) != null) {
			return false;
		}
		if (value instanceof String) {
			return false;
		}
		return true;
	}

	public static Collection createApproximateCollection(Object collection) {
		if (collection instanceof LinkedList) {
			return new LinkedList();
		} else if (collection instanceof List) {
			return new ArrayList();
		} else if (collection instanceof SortedSet) {
			return new TreeSet(((SortedSet) collection).comparator());
		} else {
			return new LinkedHashSet();
		}
	}

	public static Map createApproximateMap(Object map) {
		if (map instanceof SortedMap) {
			return new TreeMap(((SortedMap) map).comparator());
		} else {
			return new LinkedHashMap();
		}
	}
}
