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
package org.tinygroup.serviceprocessor;

import org.tinygroup.application.Application;
import org.tinygroup.application.ApplicationProcessor;
import org.tinygroup.cepcore.CEPCore;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.service.ServiceProviderInterface;
import org.tinygroup.xmlparser.node.XmlNode;

public class ServiceApplicationProcessor implements ApplicationProcessor{
	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceEventProcessorImpl.class);
	private ServiceEventProcessorImpl processor;
	private ServiceProviderInterface provider;
	private CEPCore cepcore;
	public String getApplicationNodePath() {
		return null;
	}

	public String getComponentConfigPath() {
		return null;
	}

	public void config(XmlNode applicationConfig, XmlNode componentConfig) {
	}

	public XmlNode getComponentConfig() {
		return null;
	}

	public XmlNode getApplicationConfig() {
		return null;
	}

	public int getOrder() {
		return 0;
	}

	public void start() {
		LOGGER.logMessage(LogLevel.DEBUG, "启动ServiceProcessor");
		cepcore.registerEventProcessor(processor);
		LOGGER.logMessage(LogLevel.DEBUG, "启动ServiceProcessor完成");
		
	}

	public void init() {
		initProcessors();
	}
	
	private void initProcessors() {
		LOGGER.logMessage(LogLevel.DEBUG, "初始化ServiceProcessor");
		processor = new ServiceEventProcessorImpl();
		processor.setServiceProvider(provider);
		LOGGER.logMessage(LogLevel.DEBUG, "初始化ServiceProcessor完成");
	}
	

	public void stop() {
		LOGGER.logMessage(LogLevel.DEBUG, "停止ServiceProcessor");
		cepcore.unregisterEventProcessor(processor);
		LOGGER.logMessage(LogLevel.DEBUG, "停止ServiceProcessor完成");
	}

	public void setApplication(Application application) {
		
	}

	public ServiceProviderInterface getProvider() {
		return provider;
	}

	public void setProvider(ServiceProviderInterface provider) {
		this.provider = provider;
	}

	public CEPCore getCepcore() {
		return cepcore;
	}

	public void setCepcore(CEPCore cepcore) {
		this.cepcore = cepcore;
	}

}
