package org.tinygroup.springutil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	Map<ClassLoader, BeanContainer<?>> subs = new HashMap<ClassLoader, BeanContainer<?>>();
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
		fileSystemXmlApplicationContext.refresh();
		applicationContext = fileSystemXmlApplicationContext;
	}

	public SpringBeanContainer(SpringBeanContainer parent, ClassLoader loader) {
		if (inited == true)
			return;
		inited = true;
		FileSystemXmlApplicationContext fileSystemXmlApplicationContext = new FileSystemXmlApplicationContext(
				parent.getBeanContainerPrototype());
		fileSystemXmlApplicationContext.setAllowBeanDefinitionOverriding(true);
		fileSystemXmlApplicationContext.setClassLoader(loader);
		fileSystemXmlApplicationContext.refresh();
		applicationContext = fileSystemXmlApplicationContext;
	}

	public SpringBeanContainer(SpringBeanContainer parent,
			List<FileObject> files, ClassLoader loader) {
		if (inited == true)
			return;
		inited = true;
		List<String> configLocations = new ArrayList<String>();
		for (FileObject fileObject : files) {
			String urlString = fileObject.getURL().toString();
			if (!configLocations.contains(urlString)) {
				configLocations.add(urlString);
				logger.logMessage(LogLevel.INFO, "添加Spring配置文件:{urlString}",
						urlString);
			}
		}
		FileSystemXmlApplicationContext fileSystemXmlApplicationContext = new FileSystemXmlApplicationContext(
				listToArray(configLocations),
				parent.getBeanContainerPrototype());
		fileSystemXmlApplicationContext.setAllowBeanDefinitionOverriding(true);
		fileSystemXmlApplicationContext.setClassLoader(loader);
		fileSystemXmlApplicationContext.refresh();
		applicationContext = fileSystemXmlApplicationContext;
	}

	public BeanContainer<?> getSubBeanContainer(List<FileObject> files,
			ClassLoader loader) {
		SpringBeanContainer b = new SpringBeanContainer(this, files, loader);
		subs.put(loader, b);
		return b;
	}

	public BeanContainer<?> getSubBeanContainer(ClassLoader loader) {
		return subs.get(loader);
	}

	private static String[] listToArray(List<String> list) {
		String[] a = new String[0];
		return (String[]) list.toArray(a);
	}

	public Map<ClassLoader, BeanContainer<?>> getSubBeanContainers() {
		return subs;
	}

	public <T> Collection<T> getBeans(Class<T> type) {
		return applicationContext.getBeansOfType(type).values();
	}

	public <T> T getBean(String name) {
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

	public void regSpringConfigXml(List<FileObject> files) {
		for (FileObject fileObject : files) {
			String urlString = fileObject.getURL().toString();
			addUrl(urlString);
		}
	}

	public void regSpringConfigXml(String files) {
		String urlString = SpringBeanContainer.class.getResource(files).toString();
		addUrl(urlString);
	}

	private void addUrl(String urlString) {
		if (!configs.contains(urlString)) {
			configs.add(urlString);
			logger.logMessage(LogLevel.INFO, "添加Spring配置文件:{urlString}",
					urlString);
		}
	}

	public void refresh() {
		FileSystemXmlApplicationContext app = (FileSystemXmlApplicationContext) applicationContext;
		app.setConfigLocations(listToArray(configs));
		app.refresh();
	}

}
