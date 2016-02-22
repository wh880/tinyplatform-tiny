/**
 * 
 */
package org.tinygroup.remoteconfig.service.impl;

import java.util.Map;

import org.tinygroup.remoteconfig.config.ConfigPath;
import org.tinygroup.remoteconfig.manager.ConfigItemManager;
import org.tinygroup.remoteconfig.service.inter.RemoteConfigItemService;

/**
 * @author Administrator
 *
 */
public class RemoteConfigItemServiceImpl implements RemoteConfigItemService {

	ConfigItemManager configItemManager;
	
	public void setConfigItemManager(ConfigItemManager configItemManager) {
		this.configItemManager = configItemManager;
	}
	
	public void add(String key, String value, ConfigPath entity) {
		configItemManager.set(key, value, entity);
	}

	public void set(String key, String value, ConfigPath entity) {
		configItemManager.set(key, value, entity);
	}

	public void delete(String key, ConfigPath entity) {
		configItemManager.delete(key, entity);
	}

	public String get(String key, ConfigPath entity) {
		return configItemManager.get(key, entity);
	}

	public Map<String, String> getAll(ConfigPath entity) {
		return configItemManager.getALL(entity);
	}
	
}
