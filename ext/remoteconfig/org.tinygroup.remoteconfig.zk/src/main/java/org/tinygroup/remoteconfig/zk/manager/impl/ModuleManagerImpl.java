package org.tinygroup.remoteconfig.zk.manager.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.tinygroup.remoteconfig.config.ConfigPath;
import org.tinygroup.remoteconfig.config.Module;
import org.tinygroup.remoteconfig.manager.ModuleManager;
import org.tinygroup.remoteconfig.zk.client.ZKManager;
import org.tinygroup.remoteconfig.zk.utils.PathHelper;

public class ModuleManagerImpl extends BaseManager implements ModuleManager {

	public Module add(Module module, ConfigPath entity) {
		ZKManager.set(module.getName(), module.getModuleName(), entity);
		return module;
	}

	public void update(Module module, ConfigPath entity) {
		ZKManager.set(module.getName(), module.getModuleName(), entity);
	}

	public void delete(ConfigPath entity) {
		ZKManager.delete("", entity);

	}

	public Module get(ConfigPath entity) {
		Module module = new Module();
		if (StringUtils.indexOf(entity.getModulePath(), "/") > 0) {
			module.setName(StringUtils.substringAfterLast(entity.getModulePath(), "/"));
		}else {
			module.setName(entity.getModulePath());
		}
		module.setModuleName(ZKManager.get("", entity));
		return module;
	}

	public List<Module> querySubModules(ConfigPath entity) {
		List<Module> modules = new ArrayList<Module>();
		Map<String, String> moduleMap = ZKManager.getALL(entity);
		for (Iterator<String> iterator = moduleMap.keySet().iterator(); iterator.hasNext();) {
			String type = iterator.next();
			entity.setModulePath(type);
			Module parentModule = get(entity);
			modules.add(parentModule);
			try {
				getSubModule(parentModule, entity);
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
		Map<String ,String> sunModuleMap = ZKManager.getALL(entity);
		for (Iterator<String> iterator = sunModuleMap.keySet().iterator(); iterator.hasNext();) {
			String type = iterator.next();
			Module module = new Module();
			module.setName(type);
			module.setModuleName(sunModuleMap.get(type));
			modules.add(module);
			ConfigPath tempConfigPath = new ConfigPath();
			tempConfigPath.setEnvironmentName(entity.getEnvironmentName());
			tempConfigPath.setProductName(entity.getProductName());
			tempConfigPath.setVersionName(entity.getVersionName());
			tempConfigPath.setModulePath(PathHelper.getConfigPath(entity.getModulePath() ,type));
			getSubModule(module, tempConfigPath);
		}
	}
	
}
