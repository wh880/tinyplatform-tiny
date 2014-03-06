/**
 *  Copyright (c) 1997-2013, tinygroup.org (luo_guo@live.cn).
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
 * --------------------------------------------------------------------------
 *  版权 (c) 1997-2013, tinygroup.org (luo_guo@live.cn).
 *
 *  本开源软件遵循 GPL 3.0 协议;
 *  如果您不遵循此协议，则不被允许使用此文件。
 *  你可以从下面的地址获取完整的协议文本
 *
 *       http://www.gnu.org/licenses/gpl.html
 */
package org.tinygroup.service.sysservice.fileresolver;

import java.util.ArrayList;
import java.util.List;

import org.tinygroup.fileresolver.FileProcessor;
import org.tinygroup.fileresolver.FileResolver;
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
import org.tinygroup.springutil.SpringUtil;
import org.tinygroup.vfs.FileObject;
import org.tinygroup.xstream.XStreamFactory;

import com.thoughtworks.xstream.XStream;

public class XmlSysServiceFileProcessor extends XmlConfigServiceLoader
		implements FileProcessor {
	private static Logger logger = LoggerFactory
			.getLogger(XmlSysServiceFileProcessor.class);
	private static final String SERVICE_EXT_FILENAME = ".sysservice.xml";
	private List<FileObject> serviceFiles = new ArrayList<FileObject>();

	private List<ServiceComponents> list = new ArrayList<ServiceComponents>();

	public List<FileObject> getServiceFiles() {
		return serviceFiles;
	}

	public boolean isMatch(FileObject fileObject) {
		return fileObject.getFileName().endsWith(SERVICE_EXT_FILENAME);
	}

	public void add(FileObject fileObject) {
		serviceFiles.add(fileObject);
	}

	public void noChange(FileObject fileObject) {

	}

	public void modify(FileObject fileObject) {
		serviceFiles.add(fileObject);
	}

	public void delete(FileObject fileObject) {
		serviceFiles.remove(fileObject);
	}

	public void process() {
		ServiceProviderInterface provider = SpringUtil.getBean("service");
		ServiceRegistry reg = SpringUtil.getBean(ServiceRegistry.BEAN_NAME);
		provider.setServiceRegistory(reg);

		XStream stream = XStreamFactory
				.getXStream(Service.SERVICE_XSTREAM_PACKAGENAME);
		for (FileObject fileObject : serviceFiles) {
			logger.logMessage(LogLevel.INFO, "正在读取SysService文件[{0}]",
					fileObject.getAbsolutePath());
			try {
				ServiceComponents components = (ServiceComponents) stream
						.fromXML(fileObject.getInputStream());
				list.add(components);
			} catch (Exception e) {
				logger.errorMessage("读取SysService文件[{0}]出错", e,
						fileObject.getAbsolutePath());
			}

			logger.logMessage(LogLevel.INFO, "读取SysService文件[{0}]结束",
					fileObject.getAbsolutePath());
		}
		try {
			logger.logMessage(LogLevel.INFO, "正在注册SysService");
			this.loadService(provider.getServiceRegistory());
			logger.logMessage(LogLevel.INFO, "注册SysService结束");
		} catch (ServiceLoadException e) {
			logger.errorMessage("注册SysService时出错", e);
		}

	}

	public void clean() {
		serviceFiles.clear();
		list.clear();
	}

	public void setConfigPath(String path) {

	}

	
	protected List<ServiceComponents> getServiceComponents() {
		return list;
	}

	protected Object getServiceInstance(ServiceComponent component)
			throws Exception {
		if (component.getBean() == null
				|| "".equals(component.getBean().trim())) {
			Class<?> clazz = Class.forName(component.getType());
			return SpringUtil.getBean(clazz);
		}
		return SpringUtil.getBean(component.getBean());
	}

	public boolean supportRefresh() {
		return true;
	}

	public void setFileResolver(FileResolver fileResolver) {

	}

	public int getOrder() {
		return DEFAULT_PRECEDENCE;
	}

}
