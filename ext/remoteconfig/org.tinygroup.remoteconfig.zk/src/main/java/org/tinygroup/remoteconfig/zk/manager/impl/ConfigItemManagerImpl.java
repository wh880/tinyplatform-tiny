package org.tinygroup.remoteconfig.zk.manager.impl;

import java.util.Map;

import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.remoteconfig.RemoteConfigManageClient;
import org.tinygroup.remoteconfig.config.ConfigPath;
import org.tinygroup.remoteconfig.manager.ConfigItemManager;
import org.tinygroup.remoteconfig.zk.client.ZKManager;
import org.tinygroup.remoteconfig.zk.utils.PathHelper;


public class ConfigItemManagerImpl implements ConfigItemManager ,RemoteConfigManageClient{

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ConfigItemManagerImpl.class);
	
	public boolean exists(String key ,ConfigPath configPath){
		String path = PathHelper.createPath(configPath);
		LOGGER.logMessage(LogLevel.DEBUG, "远程配置，判断节点是否存在[key=%s ,path=%s]" ,key ,path);
		try {
			return ZKManager.exists(key, configPath);
		} catch (Exception e) {
			LOGGER.logMessage(LogLevel.ERROR, "远程配置，判断节点是否存在[key=%s ,path=%s]",e ,key ,path);
		}
		return false;
	}

	public String get(String key ,ConfigPath configPath){
		String path = PathHelper.createPath(configPath);
		LOGGER.logMessage(LogLevel.DEBUG, "远程配置，获取节点[key=%s ,path=%s]" ,key ,path);
		try {
			return ZKManager.get(key, configPath);
		} catch (Exception e) {
			LOGGER.logMessage(LogLevel.ERROR, "远程配置，获取节点失败[key=%s ,path=%s]",e ,key ,path);
		}
		return null;
	}

	public Map<String, String> getAll(ConfigPath configPath) {
		String path = PathHelper.createPath(configPath);
		LOGGER.logMessage(LogLevel.DEBUG, "远程配置，批量获取节点[path=%s]" ,path);
		try {
			return ZKManager.getAll(configPath);
		} catch (Exception e) {
			LOGGER.logMessage(LogLevel.ERROR, "远程配置，批量获取节点失败[path=%s]",e ,path);
		}
		return null;
	}

	public void start() {
	}

	public void stop() {
		LOGGER.logMessage(LogLevel.DEBUG, "--------------------------------------");
		LOGGER.logMessage(LogLevel.DEBUG, "远程配置，停止服务...");
		LOGGER.logMessage(LogLevel.DEBUG, "远程配置，停止服务...");
		LOGGER.logMessage(LogLevel.DEBUG, "远程配置，停止服务...");
		LOGGER.logMessage(LogLevel.DEBUG, "--------------------------------------");
		ZKManager.stop();
	}

	public void delete(String key, ConfigPath configPath) {
		String path = PathHelper.createPath(configPath);
		LOGGER.logMessage(LogLevel.DEBUG, "远程配置，删除节点[path=%s]" ,path);
		try {
			ZKManager.delete(key, configPath);
		} catch (Exception e) {
			LOGGER.logMessage(LogLevel.ERROR, "远程配置，删除节点失败[path=%s]",e ,path);
		}
	}

	public void set(String key, String value, ConfigPath configPath) {
		String path = PathHelper.createPath(configPath);
		LOGGER.logMessage(LogLevel.DEBUG, "远程配置，节点设值[%s=%s ,path=%s]" ,key ,value ,path);
		try {
			ZKManager.set(key, value, configPath);
		} catch (Exception e) {
			LOGGER.logMessage(LogLevel.ERROR, "远程配置，节点设值失败[%s=%s ,path=%s]" ,e,key ,value ,path);
		}
	}

}
