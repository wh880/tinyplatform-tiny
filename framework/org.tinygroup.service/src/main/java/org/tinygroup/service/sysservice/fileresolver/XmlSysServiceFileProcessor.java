/**
 *  Copyright (c) 1997-2013, www.tinygroup.org (tinygroup@126.com).
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
package org.tinygroup.service.sysservice.fileresolver;

import com.thoughtworks.xstream.XStream;
import org.tinygroup.beancontainer.BeanContainer;
import org.tinygroup.beancontainer.BeanContainerFactory;
import org.tinygroup.fileresolver.FileProcessor;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.service.Service;
import org.tinygroup.service.ServiceProviderInterface;
import org.tinygroup.service.config.ServiceComponent;
import org.tinygroup.service.config.ServiceComponents;
import org.tinygroup.service.config.XmlConfigServiceLoader;
import org.tinygroup.service.exception.ServiceLoadException;
import org.tinygroup.service.registry.ServiceRegistry;
import org.tinygroup.vfs.FileObject;
import org.tinygroup.xstream.XStreamFactory;

import java.util.ArrayList;
import java.util.List;

public class XmlSysServiceFileProcessor extends XmlConfigServiceLoader
		implements FileProcessor {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(XmlSysServiceFileProcessor.class);
	private static final String SERVICE_EXT_FILENAME = ".sysservice.xml";
	private ServiceProviderInterface provider;
	private ServiceRegistry reg;
	private List<ServiceComponents> list = new ArrayList<ServiceComponents>();

	public ServiceProviderInterface getProvider() {
		return provider;
	}

	public void setProvider(ServiceProviderInterface provider) {
		this.provider = provider;
	}

	public ServiceRegistry getReg() {
		return reg;
	}

	public void setReg(ServiceRegistry reg) {
		this.reg = reg;
	}

	protected boolean checkMatch(FileObject fileObject) {
		return fileObject.getFileName().endsWith(SERVICE_EXT_FILENAME);
	}

	public void process() {
		// ServiceProviderInterface provider =
		// SpringBeanContainer.getBean("service");
		// ServiceRegistry reg =
		// SpringBeanContainer.getBean(ServiceRegistry.BEAN_NAME);
		provider.setServiceRegistory(reg);

		XStream stream = XStreamFactory
				.getXStream(Service.SERVICE_XSTREAM_PACKAGENAME);
		for (FileObject fileObject : deleteList) {
			LOGGER.logMessage(LogLevel.INFO, "正在移除SysService文件[{0}]",
					fileObject.getAbsolutePath());
			ServiceComponents components = (ServiceComponents) caches
					.get(fileObject.getAbsolutePath());
			if (components != null) {
				list.remove(components);
				caches.remove(fileObject.getAbsolutePath());
			}
			LOGGER.logMessage(LogLevel.INFO, "移除SysService文件[{0}]结束",
					fileObject.getAbsolutePath());
		}
		for (FileObject fileObject : changeList) {
			LOGGER.logMessage(LogLevel.INFO, "正在读取SysService文件[{0}]",
					fileObject.getAbsolutePath());
			try {
				ServiceComponents oldComponents = (ServiceComponents) caches
						.get(fileObject.getAbsolutePath());
				if (oldComponents != null) {
					list.remove(oldComponents);
				}
				ServiceComponents components = (ServiceComponents) stream
						.fromXML(fileObject.getInputStream());
				list.add(components);
				caches.put(fileObject.getAbsolutePath(), components);
			} catch (Exception e) {
				LOGGER.errorMessage("读取SysService文件[{0}]出现异常", e,
						fileObject.getAbsolutePath());
			}

			LOGGER.logMessage(LogLevel.INFO, "读取SysService文件[{0}]结束",
					fileObject.getAbsolutePath());
		}
		try {
			LOGGER.logMessage(LogLevel.INFO, "正在注册SysService");
			this.loadService(provider.getServiceRegistory(), getFileResolver()
					.getClassLoader());
			LOGGER.logMessage(LogLevel.INFO, "注册SysService结束");
		} catch (ServiceLoadException e) {
			LOGGER.errorMessage("注册SysService时出现异常", e);
		}
		list.clear();// 扫描结束后清空服务列表
	}

	public void setConfigPath(String path) {

	}

	protected List<ServiceComponents> getServiceComponents() {
		return list;
	}

	protected Object getServiceInstance(ServiceComponent component)
			throws Exception {
		BeanContainer<?> container = BeanContainerFactory.getBeanContainer(this
				.getClass().getClassLoader());
		try {
			if (component.getBean() == null
					|| "".equals(component.getBean().trim())) {
				Class<?> clazz = Class.forName(component.getType());
				return container.getBean(clazz);
			}
			return container.getBean(component.getBean());
		} catch (Exception e) {
			LOGGER.logMessage(LogLevel.WARN, "查找Bean {}时发生异常", e,
					component.getBean());
			Class<?> clazz = Class.forName(component.getType());
			if (!clazz.isInterface()) {
				return clazz.newInstance();
			} else {
				throw e;
			}
		}
	}

}
