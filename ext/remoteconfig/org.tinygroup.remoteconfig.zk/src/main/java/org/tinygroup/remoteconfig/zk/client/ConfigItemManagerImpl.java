package org.tinygroup.remoteconfig.zk.client;

import java.util.Map;

import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.remoteconfig.RemoteConfigManageClient;
import org.tinygroup.remoteconfig.config.ConfigPath;
import org.tinygroup.remoteconfig.manager.ConfigItemManager;


public class ConfigItemManagerImpl implements ConfigItemManager ,RemoteConfigManageClient{

	protected static final Logger LOGGER = LoggerFactory
			.getLogger(ZKConfigClientImpl.class);
	
	public boolean exists(String key ,ConfigPath configPath){
		return ZKManager.exists(key, configPath);
	}

	public String get(String key ,ConfigPath configPath){
		return ZKManager.get(key, configPath);
	}

	public Map<String, String> getALL(ConfigPath configPath) {
		return ZKManager.getALL(configPath);
	}

	public void start() {
		ZKManager.exists(null, null);
	}

	public void stop() {
		ZKManager.stop();
	}

	public void delete(String key, ConfigPath configPath) {
		ZKManager.delete(key, configPath);
	}

	public void set(String key, String value, ConfigPath configPath) {
		ZKManager.set(key, value, configPath);
		
	}

}
