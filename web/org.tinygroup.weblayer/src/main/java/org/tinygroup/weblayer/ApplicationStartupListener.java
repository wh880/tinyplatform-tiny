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
package org.tinygroup.weblayer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.AbstractRefreshableConfigApplicationContext;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.tinygroup.application.Application;
import org.tinygroup.application.ApplicationProcessor;
import org.tinygroup.application.impl.ApplicationDefault;
import org.tinygroup.beancontainer.BeanContainerFactory;
import org.tinygroup.commons.io.StreamUtil;
import org.tinygroup.config.util.ConfigurationUtil;
import org.tinygroup.fileresolver.FileProcessor;
import org.tinygroup.fileresolver.FileResolver;
import org.tinygroup.fileresolver.FileResolverFactory;
import org.tinygroup.fileresolver.FileResolverUtil;
import org.tinygroup.fileresolver.FullContextFileRepository;
import org.tinygroup.fileresolver.impl.ConfigurationFileProcessor;
import org.tinygroup.fileresolver.impl.LocalPropertiesFileProcessor;
import org.tinygroup.fileresolver.impl.MergePropertiesFileProcessor;
import org.tinygroup.fileresolver.impl.RemoteConfigFileProcessor;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.parser.filter.PathFilter;
import org.tinygroup.springmerge.beanfactory.SpringMergeApplicationContext;
import org.tinygroup.springutil.ExtendsSpringBeanContainer;
import org.tinygroup.springutil.fileresolver.SpringBeansFileProcessor;
import org.tinygroup.weblayer.configmanager.TinyListenerConfigManager;
import org.tinygroup.weblayer.configmanager.TinyListenerConfigManagerHolder;
import org.tinygroup.weblayer.listener.ServletContextHolder;
import org.tinygroup.weblayer.listener.TinyServletContext;
import org.tinygroup.xmlparser.node.XmlNode;
import org.tinygroup.xmlparser.parser.XmlStringParser;

public class ApplicationStartupListener implements ServletContextListener {
	private static Logger logger = LoggerFactory
			.getLogger(ApplicationStartupListener.class);
	private Application application = null;

	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		logger.logMessage(LogLevel.INFO, "WEB 应用停止中...");
		application.stop();
		// SpringBeanContainer.destory();// 关闭spring容器
		logger.logMessage(LogLevel.INFO, "WEB 应用停止完成。");
		destroyContextListener(servletContextEvent);
		ExtendsSpringBeanContainer container=(ExtendsSpringBeanContainer) BeanContainerFactory.getBeanContainer(this.getClass().getClassLoader());
		ApplicationContext application=container.getBeanContainerPrototype();
		if(application instanceof ConfigurableApplicationContext){
			((ConfigurableApplicationContext) application).close();
		}
	}

	@SuppressWarnings("unchecked")
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		TinyServletContext servletContext = new TinyServletContext(
				servletContextEvent.getServletContext());
		ServletContextHolder.setServletContext(servletContext);
		Enumeration<String> enumeration = servletContextEvent
				.getServletContext().getAttributeNames();
		logger.logMessage(LogLevel.INFO, "WEB环境属性开始");
		while (enumeration.hasMoreElements()) {
			String key = enumeration.nextElement();
			logger.logMessage(LogLevel.INFO, "{0}=[{1}]", key,
					servletContextEvent.getServletContext().getAttribute(key));
		}
		logger.logMessage(LogLevel.INFO, "WEB 应用启动中...");

		logger.logMessage(LogLevel.INFO, "WEB 应用信息：[{0}]", servletContextEvent
				.getServletContext().getServerInfo());
		String webRootPath = servletContextEvent.getServletContext()
				.getRealPath("/");
		if (webRootPath == null) {
			try {
				webRootPath = servletContextEvent.getServletContext()
						.getResource("/").getFile();
			} catch (MalformedURLException e) {
				logger.errorMessage("获取WEBROOT失败！", e);
			}
		}
		logger.logMessage(LogLevel.INFO, "TINY_WEBROOT：[{0}]", webRootPath);
		ConfigurationUtil.getConfigurationManager().setConfiguration(
				"TINY_WEBROOT", webRootPath);
		logger.logMessage(LogLevel.INFO, "应用参数<TINY_WEBROOT>=<{}>", webRootPath);

		logger.logMessage(LogLevel.INFO, "ServerContextName：[{0}]",
				servletContextEvent.getServletContext().getServletContextName());
		logger.logMessage(LogLevel.INFO, "WEB环境属性结束");

		InputStream inputStream = this.getClass().getResourceAsStream(
				"/application.xml");
		if (inputStream == null) {
			try {
				File file = new File(webRootPath + "/classes/application.xml");
				inputStream = new FileInputStream(file);
			} catch (FileNotFoundException e) {
				logger.errorMessage("获取配置文件失败，错误原因：！", e, e.getMessage());
			}
		}

		if (inputStream != null) {
			String applicationConfig = "";
			try {
				application = new ApplicationDefault();
				applicationConfig = StreamUtil.readText(inputStream, "UTF-8",
						true);
				loadSpringBeans(applicationConfig);

				XmlNode applicationXml = ConfigurationUtil
						.getConfigurationManager()
						.getApplicationConfiguration();
				if (applicationXml != null) {
					List<XmlNode> processorConfigs = applicationXml
							.getSubNodesRecursively("application-processor");
					if (processorConfigs != null) {
						for (XmlNode processorConfig : processorConfigs) {
							String processorBean = processorConfig
									.getAttribute("bean");
							ApplicationProcessor processor = BeanContainerFactory
									.getBeanContainer(
											this.getClass().getClassLoader())
									.getBean(processorBean);
							application.addApplicationProcessor(processor);
						}
					}
				}
			} catch (Exception e) {
				logger.errorMessage("载入应用配置信息时出现异常，错误原因：{}！", e, e.getMessage());
				throw new RuntimeException(e);
			}

			logger.logMessage(LogLevel.INFO, "启动应用开始...");
			application.init();
			application.start();
			FullContextFileRepository fileRepository = BeanContainerFactory
					.getBeanContainer(this.getClass().getClassLoader())
					.getBean(
							FullContextFileRepository.FILE_REPOSITORY_BEAN_NAME);
			servletContext.setFullContextFileRepository(fileRepository);// 设置上下文关联的全文搜索对象
		}
		initContextListener(servletContextEvent);

		logger.logMessage(LogLevel.INFO, "WEB 应用启动完成。");
	}

	/**
	 * 执行其他ContextListener
	 */
	private void initContextListener(ServletContextEvent servletContextEvent) {
		TinyListenerConfigManager configManager = TinyListenerConfigManagerHolder
				.getInstance();
		List<ServletContextListener> contextListeners = configManager
				.getContextListeners();
		for (ServletContextListener servletContextListener : contextListeners) {
			logger.logMessage(LogLevel.DEBUG,
					"ServletContextListener:[{0}] will be Initialized",
					servletContextListener);
			servletContextListener.contextInitialized(servletContextEvent);
			logger.logMessage(LogLevel.DEBUG,
					"ServletContextListener:[{0}] Initialized",
					servletContextListener);
		}
	}

	/**
	 * 执行其他ContextListener
	 */
	private void destroyContextListener(ServletContextEvent servletContextEvent) {
		TinyListenerConfigManager configManager = TinyListenerConfigManagerHolder
				.getInstance();
		List<ServletContextListener> contextListeners = configManager
				.getContextListeners();
		for (ServletContextListener servletContextListener : contextListeners) {
			logger.logMessage(LogLevel.DEBUG,
					"ServletContextListener:[{0}] will be Destroyed",
					servletContextListener);
			servletContextListener.contextDestroyed(servletContextEvent);
			logger.logMessage(LogLevel.DEBUG,
					"ServletContextListener:[{0}] Destroyed",
					servletContextListener);
		}
	}

	private void loadSpringBeans(String applicationConfig) {
		logger.logMessage(LogLevel.INFO, "加载Spring Bean文件开始...");
		BeanContainerFactory.initBeanContainer(ExtendsSpringBeanContainer.class
				.getName());
		ExtendsSpringBeanContainer beanContainer = (ExtendsSpringBeanContainer) BeanContainerFactory
				.getBeanContainer(getClass().getClassLoader());
		beanContainer.setApplicationContext(createWebApplicationContext());
		FileResolver fileResolver = FileResolverFactory.getFileResolver();
		FileResolverUtil.addClassPathPattern(fileResolver);
		loadFileResolverConfig(fileResolver, applicationConfig);
		fileResolver
				.addResolvePath(FileResolverUtil.getClassPath(fileResolver));
		fileResolver.addResolvePath(FileResolverUtil.getWebClasses());
		try {
			fileResolver.addResolvePath(FileResolverUtil
					.getWebLibJars(fileResolver));
		} catch (Exception e) {
			logger.errorMessage("为文件扫描器添加webLibJars时出现异常", e);
		}
		fileResolver.addFileProcessor(new SpringBeansFileProcessor());
		
		fileResolver.addFileProcessor(new LocalPropertiesFileProcessor(applicationConfig));
		
		FileProcessor remoteConfig = new RemoteConfigFileProcessor(applicationConfig);
		fileResolver.addFileProcessor(remoteConfig);
		
		fileResolver.addFileProcessor(new MergePropertiesFileProcessor());
		
		fileResolver.addFileProcessor(new ConfigurationFileProcessor());
		fileResolver.resolve();
		logger.logMessage(LogLevel.INFO, "加载Spring Bean文件结束。");
	}

	protected AbstractRefreshableConfigApplicationContext createWebApplicationContext() {
		BeanDefinitionRegistry mergedBeanDefinitionRegistry=loadMergeBeanDefinition();
		SpringMergeApplicationContext wac = new SpringMergeApplicationContext(mergedBeanDefinitionRegistry);
		// Assign the best possible id value.
		wac.setServletContext(ServletContextHolder.getServletContext());
		return wac;
	}

	/**
	 * 
	 * @return
	 */
	private BeanDefinitionRegistry loadMergeBeanDefinition() {
		ResourceLoader resourceLoader = new DefaultResourceLoader();
		Resource resource = resourceLoader.getResource("MERGE-INFO/merge.xml");
		BeanDefinitionRegistry registry=null;
		if(resource.exists()){
			registry=new XmlBeanFactory(resource);
		}else{
			registry=new DefaultListableBeanFactory();
		}
		return registry;
	}

	private void loadFileResolverConfig(FileResolver fileResolver,
			String applicationConfig) {
		XmlStringParser parser = new XmlStringParser();
		XmlNode root = parser.parse(applicationConfig).getRoot();
		PathFilter<XmlNode> filter = new PathFilter<XmlNode>(root);
		XmlNode appConfig = filter
				.findNode("/application/file-resolver-configuration");
		fileResolver.config(appConfig, null);
	}

}
