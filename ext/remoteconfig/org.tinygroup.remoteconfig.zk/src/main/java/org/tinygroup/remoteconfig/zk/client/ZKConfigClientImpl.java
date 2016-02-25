package org.tinygroup.remoteconfig.zk.client;

import java.util.HashMap;
import java.util.Map;

import org.tinygroup.exception.BaseRuntimeException;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.remoteconfig.IRemoteConfigConstant;
import org.tinygroup.remoteconfig.RemoteConfigReadClient;
import org.tinygroup.remoteconfig.config.ConfigPath;
import org.tinygroup.remoteconfig.manager.EnvironmentManager;
import org.tinygroup.remoteconfig.zk.config.RemoteConfig;
import org.tinygroup.remoteconfig.zk.config.RemoteEnvironment;


public class ZKConfigClientImpl implements RemoteConfigReadClient{

	protected static final Logger LOGGER = LoggerFactory
			.getLogger(ZKConfigClientImpl.class);
	
	ConfigPath configPath;
	
	EnvironmentManager environmentManager;
	
	public void setEnvironmentManager(EnvironmentManager environmentManager) {
		this.environmentManager = environmentManager;
	}
	
	public boolean exists(String key) throws BaseRuntimeException{
		return ZKManager.exists(key, configPath);
	}

	public String get(String key) throws BaseRuntimeException {
		return ZKManager.get(key, configPath);
	}


	public Map<String, String> getALL() throws BaseRuntimeException {
		Map<String,String> itemMap = null;
		Map<String,String> parentItemMap = null;
		try {
			itemMap = ZKManager.getALL(configPath);
		} catch (Exception e) {
		}
		try {
			parentItemMap = ZKManager.getALL(getDefaultEnvPath(configPath));
			parentItemMap.putAll(itemMap);
			parentItemMap.remove(IRemoteConfigConstant.MODULE_FLAG);
		} catch (Exception e) {
			return new HashMap<String, String>();
		}
		return parentItemMap;
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
	
	public static ConfigPath getDefaultEnvPath(ConfigPath configPath){
		ConfigPath tempConfigPath = new ConfigPath();
		tempConfigPath.setVersionName(configPath.getVersionName());
		tempConfigPath.setProductName(configPath.getProductName());
		tempConfigPath.setEnvironmentName(IRemoteConfigConstant.DEFAULT_ENV);
		tempConfigPath.setModulePath(configPath.getModulePath());
		return tempConfigPath;
	}
	
}
