package org.tinygroup.remoteconfig.zk.manager.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.tinygroup.remoteconfig.config.ConfigPath;
import org.tinygroup.remoteconfig.config.Module;
import org.tinygroup.remoteconfig.manager.ModuleManager;
import org.tinygroup.remoteconfig.zk.client.ZKModuleManager;
import org.tinygroup.remoteconfig.zk.utils.PathHelper;

public class ModuleManagerImpl implements ModuleManager {

	public Module add(Module module, ConfigPath entity) {
		ZKModuleManager.set(module.getName(), module, entity);
		return module;
	}

	public void update(Module module, ConfigPath entity) {
		ZKModuleManager.set(module.getName(), module, entity);
	}

	public void delete(ConfigPath entity) {
		ZKModuleManager.delete("", entity);
	}
 
	private ConfigPath copyConfigPath(ConfigPath src){
		ConfigPath cy = new ConfigPath();
		cy.setProductName(src.getProductName());
		cy.setVersionName(src.getVersionName());
		cy.setEnvironmentName(src.getEnvironmentName());
		cy.setModulePath(src.getModulePath());
		return cy;
	}
	
	public Module get(ConfigPath entity) {
		ConfigPath tempPath = copyConfigPath(entity);
		Module module = ZKModuleManager.get("", tempPath);
		if (module != null) {
			module.setSubModules(querySubModules(tempPath));
		}
		return module;
	}

	public List<Module> querySubModules(ConfigPath entity) {
		List<Module> modules = new ArrayList<Module>();
		Map<String, Module> moduleMap = ZKModuleManager.getAll(entity);
		if (moduleMap == null) {
			return modules;
		}
		for (String moduleKey : moduleMap.keySet()) {
			ConfigPath tempPath = copyConfigPath(entity);
			tempPath.setModulePath(PathHelper.getConfigPath(entity.getModulePath() ,moduleKey));
			Module parentModule = get(tempPath);
			modules.add(parentModule);
			try {
				getSubModule(parentModule, tempPath);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return modules;
	}

	private void getSubModule(Module parentModule ,ConfigPath entity){
		if (parentModule == null) {
			return;
		}
		List<Module> modules = new ArrayList<Module>();
		parentModule.setSubModules(modules);
		Map<String ,Module> sunModuleMap = ZKModuleManager.getAll(entity);
		if (sunModuleMap == null) {
			return ;
		}
		for (String subModuleKey : sunModuleMap.keySet()) {
			Module module = sunModuleMap.get(subModuleKey);
			modules.add(module);
			ConfigPath tempConfigPath = copyConfigPath(entity);
			tempConfigPath.setModulePath(PathHelper.getConfigPath(entity.getModulePath() ,subModuleKey));
			getSubModule(module, tempConfigPath);
		}
	}
	
}
