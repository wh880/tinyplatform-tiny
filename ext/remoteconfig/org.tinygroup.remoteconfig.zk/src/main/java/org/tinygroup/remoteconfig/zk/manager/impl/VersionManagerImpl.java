/**
 * 
 */
package org.tinygroup.remoteconfig.zk.manager.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.tinygroup.remoteconfig.config.ConfigPath;
import org.tinygroup.remoteconfig.config.Version;
import org.tinygroup.remoteconfig.manager.EnvironmentManager;
import org.tinygroup.remoteconfig.manager.VersionManager;
import org.tinygroup.remoteconfig.zk.client.ZKVersionManager;

/**
 * @author yanwj06282
 *
 */
public class VersionManagerImpl implements VersionManager {

	EnvironmentManager environmentManager;
	
	public void setEnvironmentManager(EnvironmentManager environmentManager) {
		this.environmentManager = environmentManager;
	}
	
	public Version add(Version version, String productId) {
		ConfigPath configPath = new ConfigPath();
		configPath.setProductName(productId);
		ZKVersionManager.set(version.getName(), version, configPath);
		return version;
	}

	public void update(Version version, String productId) {
		add(version, productId);
	}

	public void delete(String versionId, String productId) {
		ConfigPath configPath = new ConfigPath();
		configPath.setProductName(productId);
		ZKVersionManager.delete(versionId, configPath);
	}

	public Version get(String versionId, String productId) {
		ConfigPath configPath = new ConfigPath();
		configPath.setProductName(productId);
		Version version = ZKVersionManager.get(versionId, configPath);
		if (version == null) {
			return null;
		}
		version.setEnvironment(environmentManager.query(versionId, productId));
		return version;
	}

	public List<Version> query(String productId) {
		ConfigPath configPath = new ConfigPath();
		configPath.setProductName(productId);
		Map<String ,Version> versionMap = ZKVersionManager.getAll(configPath);
		List<Version> versions = new ArrayList<Version>();
		for (Iterator<String> iterator = versionMap.keySet().iterator(); iterator.hasNext();) {
			String versionId = iterator.next();
			Version version = get(versionId, productId);
			if (version != null) {
				versions.add(version);
			}
		}
		return versions;
	}

}
