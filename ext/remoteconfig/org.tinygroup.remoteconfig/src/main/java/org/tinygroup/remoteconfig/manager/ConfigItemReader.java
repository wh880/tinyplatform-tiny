package org.tinygroup.remoteconfig.manager;

import java.util.Map;

import org.tinygroup.remoteconfig.config.ConfigPath;

public interface ConfigItemReader {
	void setConfigPath(ConfigPath configPath);

	String get(String key);

	boolean exists(String key);

	Map<String, String> getALL();
}
