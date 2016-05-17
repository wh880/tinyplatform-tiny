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
package org.tinygroup.mbean.config;

import java.lang.management.ManagementFactory;
import java.util.List;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import org.tinygroup.beancontainer.BeanContainerFactory;
import org.tinygroup.fileresolver.impl.AbstractFileProcessor;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;

public abstract class MBeanFileConfig extends AbstractFileProcessor{
	private static final Logger LOGGER = LoggerFactory
			.getLogger(MBeanFileConfig.class);

	protected static final String MBEAN_XSTREAM_PACKAGENAME = "mbean";
	
	public void loadMBeanServer(ClassLoader classLoader) throws Exception {
		List<TinyModeInfo> list = getTinyModeInfos();
		if (list.isEmpty()) {
			return;
		}
		MBeanServer mb = ManagementFactory.getPlatformMBeanServer();
		for (TinyModeInfo info : list) {
			ObjectName name = new ObjectName("TinyMBean:name="
					+ info.getTinyModeName());
			Class<?> clazz;
			if("bean".equals(info.getFrom())){
				clazz = BeanContainerFactory.getBeanContainer(classLoader).getBean(info.getValue());
			}else{
				clazz = Class.forName(info.getValue());
			}
			mb.registerMBean(clazz.newInstance(), name);
			LOGGER.logMessage(LogLevel.INFO, "MBean服务[{0}]注册成功",
					info.getTinyModeName());
		}
	}

	protected abstract List<TinyModeInfo> getTinyModeInfos();

}
