package org.tinygroup.remoteconfig.zk.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.tinygroup.exception.BaseRuntimeException;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.remoteconfig.IRemoteConfigConstant;
import org.tinygroup.remoteconfig.RemoteConfigReadClient;
import org.tinygroup.remoteconfig.config.ConfigPath;
import org.tinygroup.remoteconfig.config.Environment;
import org.tinygroup.remoteconfig.config.Module;
import org.tinygroup.remoteconfig.manager.EnvironmentManager;
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
		return ZKManager.exists(key, configPath);
	}

	public String get(String key) throws BaseRuntimeException {
		return ZKManager.get(key, configPath);
	}


	public Map<String, String> getAll() throws BaseRuntimeException {
		Map<String,String> itemMap = new HashMap<String, String>();
		Map<String,String> parentItemMap = new HashMap<String, String>();
		Environment defaultEnvironment = environmentManager.get(IRemoteConfigConstant.DEFAULT_ENV, configPath.getVersionName(), configPath.getProductName());
		Environment environment = environmentManager.get(configPath.getEnvironmentName(), configPath.getVersionName(), configPath.getProductName());
		if (environment != null && defaultEnvironment != null) {
			//取默认环境
			getConfig(defaultEnvironment, parentItemMap);
			
			//取指定模块
			getConfig(environment, itemMap);
			for (String key : itemMap.keySet()) {
				if (parentItemMap.get(key) != null) {
					parentItemMap.put(key, itemMap.get(key));
				}
			}
		}
		return parentItemMap;
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
	
	private void recursionModule(Module parentModule ,ConfigPath configPath ,Map<String,String> itemMap){
		if (configPath == null) {
			return;
		}
		configPath.setModulePath(PathHelper.getConfigPath(configPath.getModulePath() ,parentModule.getName()));
		Map<String, String> subItemMap = ZKManager.getAll(configPath);
		itemMap.putAll(subItemMap);
		for (String key : subItemMap.keySet()) {
			itemMap.put(key, subItemMap.get(key));
		}
		for (Module subModule : parentModule.getSubModules()) {
			recursionModule(subModule, configPath, itemMap);
		}
	}
	
}
