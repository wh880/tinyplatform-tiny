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
package org.tinygroup.remoteconfig.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.MalformedURLException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.tinygroup.commons.io.StreamUtil;
import org.tinygroup.fileresolver.FileResolver;
import org.tinygroup.fileresolver.FileResolverFactory;
import org.tinygroup.fileresolver.impl.RemoteConfigFileProcessor;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.remoteconfig.zk.manager.impl.ZKConfigClientImpl;

public class RemoteConfigStartupListener implements ServletContextListener {
	private static Logger logger = LoggerFactory
			.getLogger(RemoteConfigStartupListener.class);

	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		logger.logMessage(LogLevel.INFO, "WEB 远程配置停止中...");
		logger.logMessage(LogLevel.INFO, "WEB 远程配置停止完成。");
		
	}

	public void contextInitialized(ServletContextEvent servletContextEvent) {
		logger.logMessage(LogLevel.INFO, "WEB 远程配置启动中...");

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
				applicationConfig = StreamUtil.readText(inputStream, "UTF-8",
						true);
				
				loadRemoteConfig(applicationConfig);

			} catch (Exception e) {
				logger.errorMessage("载入远程配置时出现异常，错误原因：{}！", e, e.getMessage());
				throw new RuntimeException(e);
			}

		}

		logger.logMessage(LogLevel.INFO, "WEB 远程配置启动完成...");
	}

	private void loadRemoteConfig(String applicationConfig) {
		logger.logMessage(LogLevel.INFO, "加载远程配置开始...");
		FileResolver fileResolver = FileResolverFactory.getFileResolver();
		
		RemoteConfigFileProcessor remoteConfig = new RemoteConfigFileProcessor(applicationConfig);
		remoteConfig.setRemoteConfigReadClient(new ZKConfigClientImpl());
		fileResolver.addFileProcessor(remoteConfig);
		
		fileResolver.resolve();
		logger.logMessage(LogLevel.INFO, "加载远程配置结束...");
	}

}
