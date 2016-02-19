/**
 * 
 */
package org.tinygroup.remoteconfig.processor;

import java.util.HashMap;
import java.util.Map;

import org.tinygroup.beancontainer.BeanContainerFactory;
import org.tinygroup.fileresolver.impl.AbstractFileProcessor;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.remoteconfig.RemoteConfigReadClient;
import org.tinygroup.remoteconfig.config.ConfigPath;
import org.tinygroup.remoteconfig.manager.ConfigItemReader;
import org.tinygroup.remoteconfig.model.RemoteConfig;
import org.tinygroup.remoteconfig.model.RemoteEnvironment;
import org.tinygroup.vfs.FileObject;

/**
 * @author yanwj
 *
 */
public class RemoteConfigFileProcessor extends AbstractFileProcessor{
	ConfigItemReader configItemReader;
	RemoteConfigReadClient remoteConfigReadClient;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RemoteConfigFileProcessor.class);

	public static final String REMOTE_CONFIG_PATH = "/application/application-properties/remoteconfig";
	
	String applicationConfig;
	
	Map<String ,String> remoteProperties = new HashMap<String ,String>();
	
	public RemoteConfigFileProcessor(String applicationXML) {
		this.applicationConfig = applicationConfig;
	}
	
	public String getApplicationNodePath() {
		return REMOTE_CONFIG_PATH;
	}

	public void process() {
		if (applicationConfig == null) {
			return;
		}
		LOGGER.logMessage(LogLevel.INFO, "远程配置处理器启动");
		configItemReader = BeanContainerFactory.getBeanContainer(getClass().getClassLoader()).getBean(ConfigItemReader.class);
		remoteConfigReadClient = BeanContainerFactory.getBeanContainer(getClass().getClassLoader()).getBean(RemoteConfigReadClient.class);
		if (configItemReader == null || remoteConfigReadClient == null) {
			throw new RuntimeException("远程配置，未找到服务实现");
		}
		configItemReader.setConfigPath(getConfigPath());
		remoteConfigReadClient.start();
		LOGGER.logMessage(LogLevel.INFO, "远程配置载入Tiny...");
		remoteProperties = configItemReader.getALL();
	}

	@Override
	protected boolean checkMatch(FileObject fileObject) {
		return false;
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
	
	public int getOrder() {
		return HIGHEST_PRECEDENCE;
	}

	public Map<String, String> getRemoteProperties() {
		return remoteProperties;
	}
	
}
