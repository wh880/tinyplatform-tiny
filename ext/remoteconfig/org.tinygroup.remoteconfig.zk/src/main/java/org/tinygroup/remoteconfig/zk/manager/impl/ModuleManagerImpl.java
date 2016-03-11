package org.tinygroup.remoteconfig.zk.manager.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.remoteconfig.config.ConfigPath;
import org.tinygroup.remoteconfig.config.Module;
import org.tinygroup.remoteconfig.manager.ModuleManager;
import org.tinygroup.remoteconfig.zk.client.ZKModuleManager;
import org.tinygroup.remoteconfig.zk.utils.PathHelper;

public class ModuleManagerImpl implements ModuleManager {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ModuleManagerImpl.class);
	
	public Module add(Module module, ConfigPath entity) {
		LOGGER.logMessage(LogLevel.DEBUG, String.format("远程配置，模块设值[%s=%s]" ,module.getName() ,module.getModuleName()));
		try {
			ZKModuleManager.set(module.getName(), module, entity);
			return module;
		} catch (Exception e) {
			LOGGER.logMessage(LogLevel.ERROR, String.format("远程配置，模块设值失败[模块=%s]" ,e, module.getName() ));
		}
		return null;
	}

	public void update(Module module, ConfigPath entity) {
		LOGGER.logMessage(LogLevel.DEBUG, String.format("远程配置，更新模块[%s=%s]" ,module.getName() ,module.getModuleName()));
		try {
			ZKModuleManager.set(module.getName(), module, entity);
		} catch (Exception e) {
			LOGGER.logMessage(LogLevel.ERROR, String.format("远程配置，更新模块失败[%s=%s]" , e,module.getName() ,module.getModuleName()));
		}
	}

	public void delete(ConfigPath entity) {
		String path = PathHelper.createPath(entity);
		LOGGER.logMessage(LogLevel.DEBUG, String.format("远程配置，删除模块[%s=%s]" ,path));
		try {
			ZKModuleManager.delete("", entity);
		} catch (Exception e) {
			LOGGER.logMessage(LogLevel.ERROR, String.format("远程配置，删除模块[%s=%s]" ,e ,path));
		}
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
		String path = PathHelper.createPath(entity);
		LOGGER.logMessage(LogLevel.DEBUG, String.format("远程配置，获取模块[%s]" ,path));
		try {
			ConfigPath tempPath = copyConfigPath(entity);
			Module module = ZKModuleManager.get("", tempPath);
			if (module != null) {
				module.setSubModules(querySubModules(tempPath));
			}
			return module;
		} catch (Exception e) {
			LOGGER.logMessage(LogLevel.ERROR, String.format("远程配置，获取模块失败[%s]",e ,path));
		}
		return null;
	}

	public List<Module> querySubModules(ConfigPath entity) {
		String path = PathHelper.createPath(entity);
		LOGGER.logMessage(LogLevel.DEBUG, String.format("远程配置，获取子模块[%s]" ,path));
		List<Module> modules = new ArrayList<Module>();
		try {
			Map<String, Module> moduleMap = ZKModuleManager.getAll(entity);
			if (moduleMap == null) {
				return modules;
			}
			for (String moduleKey : moduleMap.keySet()) {
				ConfigPath tempPath = copyConfigPath(entity);
				tempPath.setModulePath(PathHelper.getConfigPath(entity.getModulePath() ,moduleKey));
				Module parentModule = get(tempPath);
				if (parentModule == null) {
					continue;
				}
				modules.add(parentModule);
				getSubModule(parentModule, tempPath);
			}
		} catch (Exception e) {
			LOGGER.logMessage(LogLevel.ERROR, String.format("远程配置，获取子模块失败[%s]" ,e ,path));
		}
		return modules;
	}

	private void getSubModule(Module parentModule ,ConfigPath entity){
		if (parentModule == null) {
			return;
		}
		List<Module> modules = new ArrayList<Module>();
		parentModule.setSubModules(modules);
		Map<String, Module> sunModuleMap = null;
		try {
			sunModuleMap = ZKModuleManager.getAll(entity);
		} catch (Exception e) {
		}
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
