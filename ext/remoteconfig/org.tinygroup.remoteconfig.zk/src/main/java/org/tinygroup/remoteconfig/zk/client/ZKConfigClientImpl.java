package org.tinygroup.remoteconfig.zk.client;

import java.util.Map;

import org.tinygroup.exception.BaseRuntimeException;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.remoteconfig.RemoteConfigReadClient;
import org.tinygroup.remoteconfig.config.ConfigPath;
import org.tinygroup.remoteconfig.manager.ConfigItemReader;
import org.tinygroup.remoteconfig.zk.config.RemoteConfig;
import org.tinygroup.remoteconfig.zk.config.RemoteEnvironment;


public class ZKConfigClientImpl implements ConfigItemReader ,RemoteConfigReadClient{

	protected static final Logger LOGGER = LoggerFactory
			.getLogger(ZKConfigClientImpl.class);
	
	ConfigPath configPath;
	
	public boolean exists(String key) throws BaseRuntimeException{
		return ZKManager.exists(key, configPath);
	}

	public String get(String key) throws BaseRuntimeException {
		return ZKManager.get(key, configPath);
	}


	public Map<String, String> getALL() throws BaseRuntimeException {
		return ZKManager.getALL(configPath);
	}


	public void start() {
		ZKManager.exists(null, null);
		setConfigPath(getConfigPath());
	}

	public void stop() {
		ZKManager.stop();
	}

	public void setConfigPath(ConfigPath configPath) {
		this.configPath = configPath;
		
	}
	
	private ConfigPath getConfigPath(){
		RemoteConfig config = RemoteEnvironment.getConfig();
		ConfigPath configPath  = new ConfigPath();
		if (config != null) {
			configPath.setProductName(config.getApp());
			configPath.setVersionName(config.getVersion());
			configPath.setEnvironmentName(config.getEnv());
			configPath.setModulePath(config.getModule());
		}
		return configPath;
	}
	
}
