package org.tinygroup.remoteconfig.manager;

import java.util.Map;

public interface ConfigItemReader {

	String get(String key);

	boolean exists(String key);

	Map<String, String> getAll();
}
