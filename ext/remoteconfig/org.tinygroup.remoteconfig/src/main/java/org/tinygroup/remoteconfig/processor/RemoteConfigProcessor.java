/**
 * 
 */
package org.tinygroup.remoteconfig.processor;

import java.util.Map;

import org.tinygroup.application.Application;
import org.tinygroup.application.ApplicationProcessor;
import org.tinygroup.config.util.ConfigurationUtil;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.remoteconfig.RemoteConfigReadClient;
import org.tinygroup.remoteconfig.config.ConfigPath;
import org.tinygroup.remoteconfig.manager.ConfigItemReader;
import org.tinygroup.remoteconfig.model.RemoteConfig;
import org.tinygroup.remoteconfig.model.RemoteEnvironment;
import org.tinygroup.xmlparser.node.XmlNode;

/**
 * @author yanwj
 *
 */
public class RemoteConfigProcessor implements ApplicationProcessor{
	private static final Logger LOGGER = LoggerFactory.getLogger(RemoteConfigProcessor.class);
	ConfigItemReader configItemReader;
	RemoteConfigReadClient remoteConfigReadClient;
	
	public void setConfigItemReader(ConfigItemReader configItemReader) {
		this.configItemReader = configItemReader;
	}

	public void setRemoteConfigReadClient(
			RemoteConfigReadClient remoteConfigReadClient) {
		this.remoteConfigReadClient = remoteConfigReadClient;
	}

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
		LOGGER.logMessage(LogLevel.INFO, "远程配置载入Tiny...");
		Map<String ,String> remoteConfigMap = configItemReader.getALL();
		if (remoteConfigMap != null && remoteConfigMap.size() > 0) {
			ConfigurationUtil.getConfigurationManager().getConfiguration().putAll(remoteConfigMap);
		}
	}

	public void init() {
		LOGGER.logMessage(LogLevel.INFO, "远程配置处理器启动");
		configItemReader.setConfigPath(getConfigPath());
		remoteConfigReadClient.start();
	}

	public void stop() {
		remoteConfigReadClient.stop();
	}

	public void setApplication(Application application) {

	}

	public ConfigPath getConfigPath(){
		RemoteConfig config = RemoteEnvironment.getConfig();
		ConfigPath configPath  = new ConfigPath();
		if (config != null) {
			configPath.setProductName(config.getApp());
			configPath.setVersionName(config.getVersion());
			configPath.setEnvironmentName(config.getEnv());
		}
		return configPath;
	}
	
}
