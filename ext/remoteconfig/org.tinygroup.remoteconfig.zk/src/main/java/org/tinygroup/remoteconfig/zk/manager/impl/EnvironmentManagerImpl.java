/**
 * 
 */
package org.tinygroup.remoteconfig.zk.manager.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.tinygroup.remoteconfig.config.ConfigPath;
import org.tinygroup.remoteconfig.config.Environment;
import org.tinygroup.remoteconfig.manager.EnvironmentManager;
import org.tinygroup.remoteconfig.manager.ModuleManager;

/**
 * @author yanwj06282
 *
 */
public class EnvironmentManagerImpl extends BaseManager implements EnvironmentManager {

	ModuleManager moduleManager;
	
	public void setModuleManager(ModuleManager moduleManager) {
		this.moduleManager = moduleManager;
	}
	
	public Environment add(Environment env, String versionId, String productId) {
		ConfigPath configPath = new ConfigPath();
		configPath.setProductName(productId);
		configPath.setVersionName(versionId);
		configItemManager.set(env.getName(), env.getEnvironment(), configPath);
		return env;
	}

	public void update(Environment env, String versionId, String productId) {
		add(env, versionId, productId);
	}

	public void delete(String envId, String versionId, String productId) {
		ConfigPath configPath = new ConfigPath();
		configPath.setProductName(productId);
		configPath.setVersionName(versionId);
		configItemManager.delete(envId, configPath);

	}

	public Environment get(String envId, String versionId, String productId) {
		ConfigPath configPath = new ConfigPath();
		configPath.setProductName(productId);
		configPath.setVersionName(versionId);
		String value = configItemManager.get(envId, configPath);
		if (StringUtils.isBlank(value)) {
			return null;
		}
		Environment environment = new Environment();
		environment.setName(envId);
		environment.setEnvironment(value);
		configPath.setEnvironmentName(envId);
		environment.setModules(moduleManager.querySubModules(configPath));
		return environment;
	}

	public List<Environment> query(String versionId, String productId) {
		ConfigPath configPath = new ConfigPath();
		configPath.setProductName(productId);
		configPath.setVersionName(versionId);
		Map<String ,String> sunModuleMap = configItemManager.getALL(configPath);
		List<Environment> envs = new ArrayList<Environment>();
		for (Iterator<String> iterator = sunModuleMap.keySet().iterator(); iterator.hasNext();) {
			String envId = iterator.next();
			Environment environment = get(envId, versionId ,productId);
			if (environment != null) {
				envs.add(environment);
			}
		}
		return envs;
	}

}
