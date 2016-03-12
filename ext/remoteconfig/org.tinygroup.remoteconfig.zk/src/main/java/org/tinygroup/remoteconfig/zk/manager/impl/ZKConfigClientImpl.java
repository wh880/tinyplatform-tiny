package org.tinygroup.remoteconfig.zk.manager.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.tinygroup.exception.BaseRuntimeException;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.remoteconfig.IRemoteConfigConstant;
import org.tinygroup.remoteconfig.RemoteConfigReadClient;
import org.tinygroup.remoteconfig.config.ConfigPath;
import org.tinygroup.remoteconfig.config.Environment;
import org.tinygroup.remoteconfig.config.Module;
import org.tinygroup.remoteconfig.manager.EnvironmentManager;
import org.tinygroup.remoteconfig.zk.client.ZKManager;
import org.tinygroup.remoteconfig.zk.config.RemoteConfig;
import org.tinygroup.remoteconfig.zk.config.RemoteEnvironment;
import org.tinygroup.remoteconfig.zk.utils.PathHelper;


public class ZKConfigClientImpl implements RemoteConfigReadClient{

	protected static final Logger LOGGER = LoggerFactory
			.getLogger(ZKConfigClientImpl.class);
	
	ConfigPath configPath;
	
	EnvironmentManager environmentManager;
	
	public void setEnvironmentManager(EnvironmentManager environmentManager) {
		this.environmentManager = environmentManager;
	}
	
	public boolean exists(String key) throws BaseRuntimeException{
		String path = PathHelper.createPath(configPath);
		LOGGER.logMessage(LogLevel.DEBUG, "远程配置，判断节点是否存在[key=%s ,path=%s]" ,key ,path);
		try {
			return ZKManager.exists(key, configPath);
		} catch (Exception e) {
			LOGGER.logMessage(LogLevel.ERROR, "远程配置，判断节点是否存在[key=%s ,path=%s]",e ,key ,path);
		}
		return false;
	}

	public String get(String key) throws BaseRuntimeException {
		String path = PathHelper.createPath(configPath);
		LOGGER.logMessage(LogLevel.DEBUG, "远程配置，获取节点[key=%s ,path=%s]" ,key ,path);
		try {
			return ZKManager.get(key, configPath);
		} catch (Exception e) {
			LOGGER.logMessage(LogLevel.ERROR, "远程配置，获取节点失败[key=%s ,path=%s]",e ,key ,path);
		}
		return null;
	}


	public Map<String, String> getAll() throws BaseRuntimeException {
		Map<String,String> itemMap = new HashMap<String, String>();
		Map<String,String> defaultItemMap = new HashMap<String, String>();
		String path = PathHelper.createPath(configPath);
		LOGGER.logMessage(LogLevel.DEBUG, "远程配置，批量获取节点[path=%s]" ,path);
		Environment defaultEnvironment = environmentManager.get(IRemoteConfigConstant.DEFAULT_ENV_NAME, configPath.getVersionName(), configPath.getProductName());
		Environment environment = environmentManager.get(configPath.getEnvironmentName(), configPath.getVersionName(), configPath.getProductName());
		if (environment != null && defaultEnvironment != null) {
			//取默认环境
			getConfig(defaultEnvironment, defaultItemMap);
			
			//取指定模块
			getConfig(environment, itemMap);
			for (String key : itemMap.keySet()) {
				if (defaultItemMap.get(key) != null) {
					defaultItemMap.put(key, itemMap.get(key));
				}
			}
		}
		return defaultItemMap;
	}

	public void start() {
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
		}
		return configPath;
	}
	
	private void getConfig(Environment environment ,Map<String,String> itemMap){
		List<Module> modules = environment.getModules();
		for (Module subModule : modules) {
			ConfigPath tempConfigPath = new ConfigPath();
			tempConfigPath.setProductName(configPath.getProductName());
			tempConfigPath.setVersionName(configPath.getVersionName());
			tempConfigPath.setEnvironmentName(environment.getName());
			recursionModule(subModule, tempConfigPath, itemMap);
		}
	}
	
	/**
	 * 递归遍历模块
	 * 
	 * @param parentModule
	 * @param configPath
	 * @param itemMap
	 */
	private void recursionModule(Module parentModule ,ConfigPath configPath ,Map<String,String> itemMap){
		if (configPath == null) {
			return;
		}
		configPath.setModulePath(PathHelper.getConfigPath(configPath.getModulePath() ,parentModule.getName()));
		Map<String, String> subItemMap = null;
		try {
			subItemMap = ZKManager.getAll(configPath);
		} catch (Exception e) {
			return;
		}
		itemMap.putAll(subItemMap);
		for (String key : subItemMap.keySet()) {
			itemMap.put(key, subItemMap.get(key));
		}
		for (Module subModule : parentModule.getSubModules()) {
			recursionModule(subModule, configPath, itemMap);
		}
	}
	
}
