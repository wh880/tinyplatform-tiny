package org.tinygroup.remoteconfig.manager;

import java.util.Map;

import org.tinygroup.remoteconfig.config.ConfigPath;

/**
 * 此方法中，所有的configPath指向的module都必须是叶子module
 * 
 * @author chenjiao
 * 
 */
public interface ConfigItemManager {

	void set(String key, String value, ConfigPath configPath);

	void delete(String key, ConfigPath configPath);

	String get(String key, ConfigPath configPath);

	boolean exists(String key, ConfigPath configPath);

	Map<String, String> getAll(ConfigPath configPath);

}
