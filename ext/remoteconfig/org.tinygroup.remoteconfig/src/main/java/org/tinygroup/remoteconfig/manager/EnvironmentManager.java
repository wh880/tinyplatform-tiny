package org.tinygroup.remoteconfig.manager;

import java.util.List;

import org.tinygroup.remoteconfig.config.Environment;

public interface EnvironmentManager {
	Environment add(Environment env, String versionId, String productId);

	void update(Environment env, String versionId, String productId);

	void delete(String envId, String versionId, String productId);

	Environment get(String envId, String versionId, String productId);

	List<Environment> query(String versionId, String productId);
}
