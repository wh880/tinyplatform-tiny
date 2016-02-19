package org.tinygroup.remoteconfig.manager;

import java.util.List;

import org.tinygroup.remoteconfig.config.Version;

public interface VersionManager {
	Version add(Version version, String productId);

	void update(Version version, String productId);

	void delete(String versionId, String productId);

	Version get(String versionId, String productId);

	List<Version> query(String productId);
}
