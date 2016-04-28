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
package org.tinygroup.serviceweblayer.fileresolver;

import com.thoughtworks.xstream.XStream;
import org.tinygroup.fileresolver.impl.AbstractFileProcessor;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.serviceweblayer.ServiceMappingManager;
import org.tinygroup.serviceweblayer.config.ServiceViewMappings;
import org.tinygroup.vfs.FileObject;
import org.tinygroup.xstream.XStreamFactory;

import java.io.InputStream;

public class ServiceMappingFileProcessor extends AbstractFileProcessor {

	private static final String SERVICEMAPPING_EXT_FILENAMES = ".servicemapping.xml";
	private ServiceMappingManager manager;
	protected boolean checkMatch(FileObject fileObject) {
		return fileObject.getFileName().endsWith(SERVICEMAPPING_EXT_FILENAMES);
	}

	public ServiceMappingManager getManager() {
		return manager;
	}

	public void setManager(ServiceMappingManager manager) {
		this.manager = manager;
	}

	public void process() {
		
		XStream stream = XStreamFactory
				.getXStream(ServiceMappingManager.XSTREAM_PACKAGE_NAME);
		for (FileObject fileObject : deleteList) {
			LOGGER.logMessage(LogLevel.INFO, "正在ServiceMappings文件[{0}]",
					fileObject.getAbsolutePath());
			ServiceViewMappings mappings = (ServiceViewMappings) caches
					.get(fileObject.getAbsolutePath());
			if (mappings != null) {
				manager.removeServiceMappings(mappings);
				caches.remove(fileObject.getAbsolutePath());
			}
			LOGGER.logMessage(LogLevel.INFO, "读取ServiceMappings文件[{0}]结束",
					fileObject.getAbsolutePath());
		}
		for (FileObject fileObject : changeList) {
			LOGGER.logMessage(LogLevel.INFO, "正在ServiceMappings文件[{0}]",
					fileObject.getAbsolutePath());
			ServiceViewMappings oldMappings = (ServiceViewMappings) caches
					.get(fileObject.getAbsolutePath());
			if (oldMappings != null) {
				manager.removeServiceMappings(oldMappings);
			}
			InputStream inputStream = fileObject.getInputStream();
			ServiceViewMappings mappings = (ServiceViewMappings) stream
					.fromXML(inputStream);
			try {
				inputStream.close();
			} catch (Exception e) {
				LOGGER.errorMessage("关闭文件流时出错,文件路径:{}",e, fileObject.getAbsolutePath());
			}
			manager.addServiceMappings(mappings);
			caches.put(fileObject.getAbsolutePath(), mappings);
			LOGGER.logMessage(LogLevel.INFO, "读取ServiceMappings文件[{0}]结束",
					fileObject.getAbsolutePath());
		}
	}

}
