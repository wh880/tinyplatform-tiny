/**
 * 
 */
package org.tinygroup.remoteconfig.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.tinygroup.exception.BaseRuntimeException;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.remoteconfig.IRemoteConfigConstant;
import org.tinygroup.remoteconfig.config.ConfigPath;
import org.tinygroup.remoteconfig.config.Module;
import org.tinygroup.remoteconfig.manager.ConfigItemManager;
import org.tinygroup.remoteconfig.manager.ModuleManager;
import org.tinygroup.remoteconfig.service.DefaultEnvironment;
import org.tinygroup.remoteconfig.service.inter.RemoteConfigModuleService;
import org.tinygroup.remoteconfig.service.utils.EnvironmentHelper;
import org.tinygroup.remoteconfig.service.utils.PathHelper;

/**
 * 
 * 此服务只操作模块
 * 
 * @author yanwj
 *
 */
public class RemoteConfigModuleServiceImpl implements RemoteConfigModuleService{

	ModuleManager moduleManager;
	
	ConfigItemManager configItemManager;
	
	public void setModuleManager(ModuleManager moduleManager) {
		this.moduleManager = moduleManager;
	}
	
	public void setConfigItemManager(ConfigItemManager configItemManager) {
		this.configItemManager = configItemManager;
	}
	
	protected static final Logger LOGGER = LoggerFactory
			.getLogger(RemoteConfigModuleServiceImpl.class);

	public void add(Module module, ConfigPath entity) {
		moduleManager.add(module, entity);
		setDefaultModelFalg(module, entity);
	}

	public void set(Module oldModule ,Module newModule ,ConfigPath entity) {
		try {
			//如果路径发生变化
			if (!StringUtils.equals(oldModule.getName(), newModule.getName())) {
				LOGGER.errorMessage(String.format("模块无法修改key[%s=%s]", newModule.getName(), newModule.getModuleName()));
				return;
			}else {
				//普通修改，key未变化
				moduleManager.update(newModule, entity);
			}
		} catch (BaseRuntimeException e) {
			LOGGER.errorMessage(String.format("模块设值操作失败[%s=%s]", newModule.getName(), newModule.getModuleName()) ,e);
		}
	}

	public void delete(ConfigPath entity) {
		if (entity != null) {
			moduleManager.delete(entity);
			//如果初始环境模块删除，其他环境也要删除
			if (StringUtils.equals(DefaultEnvironment.DEFAULT.getName(), entity.getEnvironmentName())) {
				//操作其他环境
				List<ConfigPath> otherConfigPath = EnvironmentHelper.getOtherEnvPath(configItemManager, entity);
				for (ConfigPath configPath : otherConfigPath) {
					moduleManager.delete(configPath);
				}
			}
		}
	}

	public Module get(ConfigPath entity) {
		return moduleManager.get(entity);
	}

	public List<Module> query(ConfigPath entity) {
		return moduleManager.querySubModules(entity);
	}
	
	/**
	 * 设置默认模块表示，用以区分模块和非模块
	 * 
	 * @param serviceItem
	 */
	private void setDefaultModelFalg(Module module ,ConfigPath entity){
		configItemManager.set(PathHelper.getConfigPath(module.getName() ,IRemoteConfigConstant.MODULE_FLAG), IRemoteConfigConstant.MODULE_FLAG, entity);
	}
	
}
