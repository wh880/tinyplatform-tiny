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
import org.tinygroup.remoteconfig.zk.client.ZKDefaultEnvManager;
import org.tinygroup.remoteconfig.zk.client.ZKEnvManager;

/**
 * @author yanwj06282
 *
 */
public class EnvironmentManagerImpl implements EnvironmentManager {

	ModuleManager moduleManager;
	
	public void setModuleManager(ModuleManager moduleManager) {
		this.moduleManager = moduleManager;
	}
	
	public Environment add(Environment env, String versionId, String productId) {
		ConfigPath configPath = new ConfigPath();
		configPath.setProductName(productId);
		configPath.setVersionName(versionId);
		ZKEnvManager.set(env.getName(), env, configPath);
		return env;
	}

	public void update(Environment env, String versionId, String productId) {
		add(env, versionId, productId);
	}

	public void delete(String envId, String versionId, String productId) {
		ConfigPath configPath = new ConfigPath();
		configPath.setProductName(productId);
		configPath.setVersionName(versionId);
		ZKEnvManager.delete(envId, configPath);

	}

	public Environment get(String envId, String versionId, String productId) {
		ConfigPath configPath = new ConfigPath();
		configPath.setProductName(productId);
		configPath.setVersionName(versionId);
		Environment environment = ZKEnvManager.get(envId, configPath);
		if (environment == null) {
			return null;
		}
		configPath.setEnvironmentName(envId);
		environment.setModules(moduleManager.querySubModules(configPath));
		return environment;
	}

	public List<Environment> query(String versionId, String productId) {
		if (StringUtils.isBlank(versionId) || StringUtils.isBlank(productId) ) {
			return new ArrayList<Environment>(ZKDefaultEnvManager.getAll().values());
		}
		ConfigPath configPath = new ConfigPath();
		configPath.setProductName(productId);
		configPath.setVersionName(versionId);
		Map<String ,Environment> sunModuleMap = ZKEnvManager.getAll(configPath);
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

	public Environment add(Environment env) {
		ZKDefaultEnvManager.set(env.getName(), env);
		return env;
	}

	public void update(Environment env) {
		ZKDefaultEnvManager.set(env.getName(), env);
	}

	public void delete(String envId) {
		ZKDefaultEnvManager.delete(envId);
	}

	public Environment get(String envId) {
		return ZKDefaultEnvManager.get(envId);
	}

	public void add(String envId, String versionId, String productId) {
		// TODO Auto-generated method stub
		
	}

}
