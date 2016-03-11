/**
 * 
 */
package org.tinygroup.remoteconfig.zk.manager.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.remoteconfig.config.ConfigPath;
import org.tinygroup.remoteconfig.config.Environment;
import org.tinygroup.remoteconfig.manager.EnvironmentManager;
import org.tinygroup.remoteconfig.manager.ModuleManager;
import org.tinygroup.remoteconfig.zk.client.ZKDefaultEnvManager;
import org.tinygroup.remoteconfig.zk.client.ZKEnvManager;

/**
 * @author yanwj06282
 *
 */
public class EnvironmentManagerImpl implements EnvironmentManager {

	protected static final Logger LOGGER = LoggerFactory
			.getLogger(EnvironmentManagerImpl.class);
	
	ModuleManager moduleManager;
	
	public void setModuleManager(ModuleManager moduleManager) {
		this.moduleManager = moduleManager;
	}
	
	public Environment add(Environment env, String versionId, String productId) {
		LOGGER.logMessage(LogLevel.DEBUG, "远程配置，增加环境[%s ,版本=%s ,项目=%s]" ,env.getName() ,env.getEnvironment() ,versionId ,productId);
		ConfigPath configPath = new ConfigPath();
		configPath.setProductName(productId);
		configPath.setVersionName(versionId);
		try {
			ZKEnvManager.set(env.getName(), env, configPath);
		} catch (Exception e) {
			LOGGER.logMessage(LogLevel.ERROR, "远程配置，增加环境失败[%s ,版本=%s ,项目=%s]" , e,env.getName() ,env.getEnvironment() ,versionId ,productId);
		}
		return env;
	}

	public void update(Environment env, String versionId, String productId) {
		LOGGER.logMessage(LogLevel.DEBUG, "远程配置，更新环境[%s ,版本=%s ,项目=%s]" ,env.getName() ,env.getEnvironment() ,versionId ,productId);
		ConfigPath configPath = new ConfigPath();
		configPath.setProductName(productId);
		configPath.setVersionName(versionId);
		try {
			ZKEnvManager.set(env.getName(), env, configPath);
		} catch (Exception e) {
			LOGGER.logMessage(LogLevel.ERROR, "远程配置，更新环境失败[%s ,版本=%s ,项目=%s]" , e,env.getName() ,env.getEnvironment() ,versionId ,productId);
		}
	}

	public void delete(String envId, String versionId, String productId) {
		LOGGER.logMessage(LogLevel.DEBUG, "远程配置，删除环境[%s ,版本=%s ,项目=%s]" ,envId ,versionId ,productId);
		ConfigPath configPath = new ConfigPath();
		configPath.setProductName(productId);
		configPath.setVersionName(versionId);
		try {
			ZKEnvManager.delete(envId, configPath);
		} catch (Exception e) {
			LOGGER.logMessage(LogLevel.ERROR, "远程配置，删除环境失败[%s ,版本=%s ,项目=%s]" , e,envId ,versionId ,productId);
		}

	}

	public Environment get(String envId, String versionId, String productId) {
		LOGGER.logMessage(LogLevel.DEBUG, "远程配置，获取环境[%s ,版本=%s ,项目=%s]" ,envId ,versionId ,productId);
		ConfigPath configPath = new ConfigPath();
		configPath.setProductName(productId);
		configPath.setVersionName(versionId);
		try {
			Environment environment = ZKEnvManager.get(envId, configPath);
			if (environment == null) {
				return null;
			}
			configPath.setEnvironmentName(envId);
			environment.setModules(moduleManager.querySubModules(configPath));
			return environment;
		} catch (Exception e) {
			LOGGER.logMessage(LogLevel.ERROR, "远程配置，获取环境失败[%s ,版本=%s ,项目=%s]" ,e ,envId ,versionId ,productId);
		}
		return null;
	}

	public List<Environment> query(String versionId, String productId) {
		LOGGER.logMessage(LogLevel.DEBUG, "远程配置，批量获取环境[版本=%s ,项目=%s]" ,versionId ,productId);
		if (StringUtils.isBlank(versionId) || StringUtils.isBlank(productId) ) {
			LOGGER.logMessage(LogLevel.DEBUG, "远程配置，批量获取默认环境");
			try {
				return new ArrayList<Environment>(ZKDefaultEnvManager.getAll().values());
			} catch (Exception e) {
				LOGGER.logMessage(LogLevel.ERROR, "远程配置，批量获取默认环境失败" ,e);
			}
		}
		ConfigPath configPath = new ConfigPath();
		configPath.setProductName(productId);
		configPath.setVersionName(versionId);
		List<Environment> envs = new ArrayList<Environment>();
		try {
			Map<String ,Environment> sunModuleMap = ZKEnvManager.getAll(configPath);
			for (Iterator<String> iterator = sunModuleMap.keySet().iterator(); iterator.hasNext();) {
				String envId = iterator.next();
				Environment environment = get(envId, versionId ,productId);
				if (environment != null) {
					envs.add(environment);
				}
			}
		} catch (Exception e) {
			LOGGER.logMessage(LogLevel.ERROR, "远程配置，批量获取环境失败[版本=%s ,项目=%s]", e ,versionId ,productId);
		}
		return envs;
	}

	public Environment add(Environment env) {
		LOGGER.logMessage(LogLevel.DEBUG, "远程配置，增加默认环境[%s]" ,env.getName());
		try {
			ZKDefaultEnvManager.set(env.getName(), env);
		} catch (Exception e) {
			LOGGER.logMessage(LogLevel.ERROR, "远程配置，增加默认环境失败[%s]" , e,env.getName());
			return null;
		}
		return env;
	}

	public void update(Environment env) {
		LOGGER.logMessage(LogLevel.DEBUG, "远程配置，更新默认环境[%s]" ,env.getName());
		try {
			ZKDefaultEnvManager.set(env.getName(), env);
		} catch (Exception e) {
			LOGGER.logMessage(LogLevel.ERROR, "远程配置，更新默认环境失败[%s]" , e ,env.getName());
		}
	}

	public void delete(String envId) {
		LOGGER.logMessage(LogLevel.DEBUG, "远程配置，删除默认环境[%s]" ,envId);
		try {
			ZKDefaultEnvManager.delete(envId);
		} catch (Exception e) {
			LOGGER.logMessage(LogLevel.ERROR, "远程配置，删除默认环境[%s]" ,e,envId);
		}
	}

	public Environment get(String envId) {
		LOGGER.logMessage(LogLevel.DEBUG, "远程配置，获取默认环境[%s]" ,envId);
		Environment environment = null;
		try {
			environment = ZKDefaultEnvManager.get(envId);
		} catch (Exception e) {
			LOGGER.logMessage(LogLevel.ERROR, "远程配置，获取默认环境失败[%s]" ,e ,envId);
		}
		return environment;
	}

	public void add(String envId, String versionId, String productId) {
		// TODO Auto-generated method stub
		
	}

}
