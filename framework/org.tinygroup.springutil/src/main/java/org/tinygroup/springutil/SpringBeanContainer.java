package org.tinygroup.springutil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.util.StringUtils;
import org.tinygroup.beancontainer.BeanContainer;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.vfs.FileObject;

public class SpringBeanContainer implements BeanContainer<ApplicationContext> {
	private static Logger logger = LoggerFactory
			.getLogger(SpringBeanContainer.class);
	ApplicationContext applicationContext = null;
	List<String> configs = new ArrayList<String>();
	List<ApplicationContext> subs = new ArrayList<ApplicationContext>();
	boolean inited = false;

	public ApplicationContext getBeanContainerPrototype() {
		return applicationContext;
	}

	public SpringBeanContainer() {
		if (inited == true)
			return;
		inited = true;
		FileSystemXmlApplicationContext fileSystemXmlApplicationContext = new FileSystemXmlApplicationContext();
		fileSystemXmlApplicationContext.setAllowBeanDefinitionOverriding(true);
		applicationContext = fileSystemXmlApplicationContext;
	}

	public ApplicationContext getSubBeanContainer(List<FileObject> files,
			ClassLoader loader) {
		List<String> configLocations = new ArrayList<String>();
		for (FileObject fileObject : files) {
			String urlString = fileObject.getURL().toString();
			if (!configLocations.contains(urlString)) {
				configLocations.add(urlString);
				logger.logMessage(LogLevel.INFO, "添加Spring配置文件:{urlString}",
						urlString);
			}
		}
		FileSystemXmlApplicationContext sub = new FileSystemXmlApplicationContext(
				listToArray(configLocations), applicationContext);
		sub.setClassLoader(loader);
		sub.refresh();
		subs.add(sub);
		return sub;

	}

	private static String[] listToArray(List<String> list) {
		String[] a = new String[0];
		return (String[]) list.toArray(a);
	}

	public List<ApplicationContext> getSubBeanContainers() {
		return subs;
	}

	public <T> Collection<T> getBeans(Class<T> type) {
		return applicationContext.getBeansOfType(type).values();
	}

	public <T> T getBean(String name) {
		// TODO Auto-generated method stub
		return (T) applicationContext.getBean(name);
	}

	public <T> T getBean(Class<T> clazz) {

		String[] beanNames = applicationContext.getBeanNamesForType(clazz);
		if (beanNames.length == 1) {
			return (T) applicationContext.getBean(beanNames[0], clazz);
		} else {
			throw new NoSuchBeanDefinitionException(clazz,
					"expected single bean but found "
							+ beanNames.length
							+ ": "
							+ StringUtils
									.arrayToCommaDelimitedString(beanNames));
		}
	}

	public <T> T getBean(String name, Class<T> clazz) {
		return (T) applicationContext.getBean(name, clazz);
	}

}
