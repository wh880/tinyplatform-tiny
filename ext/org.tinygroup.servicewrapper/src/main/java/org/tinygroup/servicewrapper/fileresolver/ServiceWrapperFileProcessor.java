package org.tinygroup.servicewrapper.fileresolver;

import java.io.InputStream;

import org.tinygroup.fileresolver.impl.AbstractFileProcessor;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.servicewrapper.ServiceWrapperConfigManager;
import org.tinygroup.servicewrapper.config.MethodConfigs;
import org.tinygroup.vfs.FileObject;
import org.tinygroup.xstream.XStreamFactory;

import com.thoughtworks.xstream.XStream;

public class ServiceWrapperFileProcessor extends AbstractFileProcessor {

	private static final String WRAPPER_CACHE_EXT_FILENAME = ".servicewrapper.xml";

	private ServiceWrapperConfigManager manager;
	
	protected boolean checkMatch(FileObject fileObject) {
		return fileObject.getFileName().endsWith(WRAPPER_CACHE_EXT_FILENAME);
	}

	public void process() {
		XStream stream = XStreamFactory
				.getXStream(ServiceWrapperConfigManager.XSTEAM_PACKAGE_NAME);
		for (FileObject fileObject : deleteList) {
			LOGGER.logMessage(LogLevel.INFO, "正在移除aop服务包装配置文件[{0}]",
					fileObject.getAbsolutePath());
			MethodConfigs aopWrappers = (MethodConfigs) caches.get(fileObject
					.getAbsolutePath());
			if (aopWrappers != null) {
				manager.removeServiceWrappers(aopWrappers);
				caches.remove(fileObject.getAbsolutePath());
			}
			LOGGER.logMessage(LogLevel.INFO, "移除aop服务包装配置文件[{0}]结束",
					fileObject.getAbsolutePath());
		}
		for (FileObject fileObject : changeList) {
			LOGGER.logMessage(LogLevel.INFO, "正在加载aop服务包装配置文件[{0}]",
					fileObject.getAbsolutePath());
			MethodConfigs oldConfigs = (MethodConfigs) caches.get(fileObject
					.getAbsolutePath());
			if (oldConfigs != null) {
				manager.removeServiceWrappers(oldConfigs);
			}
			InputStream inputStream = fileObject.getInputStream();
			MethodConfigs configs = (MethodConfigs) stream.fromXML(inputStream);
			manager.addServiceWrappers(configs);
			caches.put(fileObject.getAbsolutePath(), configs);
			LOGGER.logMessage(LogLevel.INFO, "加载aop服务包装配置文件[{0}]结束",
					fileObject.getAbsolutePath());
		}
	}

	public ServiceWrapperConfigManager getManager() {
		return manager;
	}

	public void setManager(ServiceWrapperConfigManager manager) {
		this.manager = manager;
	}

}