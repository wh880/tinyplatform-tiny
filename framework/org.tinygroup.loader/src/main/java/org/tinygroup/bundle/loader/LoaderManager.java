package org.tinygroup.bundle.loader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoaderManager {
	/**
	 * 添加的classloader列表
	 */
	private static List<ClassLoader> loaders = new ArrayList<ClassLoader>();
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
		for (ClassLoader loader : loaders) {
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
		if (loaderMap.containsKey(loader)) {
			return;
		}
		List<String> classes = loaderMap.get(loader);
		if(classes==null)
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
	public static void addClassLoader(ClassLoader loader) {
		if (!loaders.contains(loader))
			loaders.add(loader);
	}
}
