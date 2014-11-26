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
package org.tinygroup.loader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoaderManager {
	/**
	 * 添加的<classloader,jarfiles>
	 */
	private static Map<ClassLoader, List<String>> loaders = new HashMap<ClassLoader, List<String>>();
	/**
	 * class与classloader的映射关系,便于直接查询
	 */
	private static Map<String, ClassLoader> classMap = new HashMap<String, ClassLoader>();
	/**
	 * loader与class的映射关系，便于直接查询
	 */
	private static Map<ClassLoader, List<String>> loaderMap = new HashMap<ClassLoader, List<String>>();

	/**
	 * 根据class名获取该class的classloader
	 * 
	 * @param className
	 * @return
	 * @throws ClassNotFoundException
	 */
	public static ClassLoader getLoader(String className)
			throws ClassNotFoundException {
		if (classMap.containsKey(className)) {
			return classMap.get(className);
		}
		try {
			Class.forName(className);
			classMap.put(className, LoaderManager.class.getClassLoader());
			return LoaderManager.class.getClassLoader();
		} catch (ClassNotFoundException e) {

		}
		for (ClassLoader loader : loaders.keySet()) {
			try {
				loader.loadClass(className);
				classMap.put(className, loader);
				if (loaderMap.containsKey(loader)) {
					loaderMap.get(loader).add(className);
				} else {
					List<String> classes = new ArrayList<String>();
					classes.add(className);
					loaderMap.put(loader, classes);
				}
				return loader;
			} catch (ClassNotFoundException e) {

			}
		}
		throw new ClassNotFoundException(className);
	}

	/**
	 * 移除一个classloader
	 * 
	 * @param loader
	 */
	public static void removeClassLoader(ClassLoader loader) {
		if (!loaderMap.containsKey(loader) && !loaders.containsKey(loader)) {
			return;
		}
		loaders.remove(loader);
		List<String> classes = loaderMap.remove(loader);

		if (classes == null)
			return;
		for (String className : classes) {
			classes.remove(className);
		}
	}

	/**
	 * 根据class名获取class
	 * 
	 * @param className
	 * @return
	 * @throws ClassNotFoundException
	 */
	public static Class<?> getClass(String className)
			throws ClassNotFoundException {
		return getLoader(className).loadClass(className);
	}

	/**
	 * 添加classloader
	 * 
	 * @param loader
	 */
	public static void addClassLoader(ClassLoader loader,List<String> jarFiles) {
		if (!loaders.containsKey(loader))
			loaders.put(loader,jarFiles);
	}
	public static List<String> getLoaderFiles(ClassLoader loader){
		return loaders.get(loader);
	}
}
