/**
 * 
 */
package org.tinygroup.remoteconfig.zk.manager.impl;

import java.util.Map;

import org.tinygroup.remoteconfig.config.ConfigPath;
import org.tinygroup.remoteconfig.manager.ConfigItemManager;

/**
 * @author Administrator
 *
 */
public class ConfigItemManagerImpl extends BaseManager implements ConfigItemManager {

	public void set(String key, String value, ConfigPath configPath) {
		configItemManager.set(key, value, configPath);
	}

	public void delete(String key, ConfigPath configPath) {
		configItemManager.delete(key, configPath);

	}

	public String get(String key, ConfigPath configPath) {
		return configItemManager.get(key, configPath);
	}

	public boolean exists(String key, ConfigPath configPath) {
		return configItemManager.exists(key, configPath);
	}

	public Map<String, String> getALL(ConfigPath configPath) {
		return configItemManager.getALL(configPath);
	}

}
