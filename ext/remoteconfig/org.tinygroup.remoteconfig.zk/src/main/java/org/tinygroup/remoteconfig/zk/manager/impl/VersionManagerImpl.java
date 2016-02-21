/**
 * 
 */
package org.tinygroup.remoteconfig.zk.manager.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.tinygroup.remoteconfig.config.ConfigPath;
import org.tinygroup.remoteconfig.config.Version;
import org.tinygroup.remoteconfig.manager.EnvironmentManager;
import org.tinygroup.remoteconfig.manager.VersionManager;

/**
 * @author yanwj06282
 *
 */
public class VersionManagerImpl extends BaseManager implements VersionManager {

	EnvironmentManager environmentManager;
	
	public Version add(Version version, String productId) {
		ConfigPath configPath = new ConfigPath();
		configPath.setProductName(productId);
		configItemManager.set(version.getName(), version.getVersion(), configPath);
		return version;
	}

	public void update(Version version, String productId) {
		add(version, productId);
	}

	public void delete(String versionId, String productId) {
		ConfigPath configPath = new ConfigPath();
		configPath.setProductName(productId);
		configItemManager.delete(versionId, configPath);
	}

	public Version get(String versionId, String productId) {
		ConfigPath configPath = new ConfigPath();
		configPath.setProductName(productId);
		String value = configItemManager.get(versionId, configPath);
		if (StringUtils.isBlank(value)) {
			return null;
		}
		Version version = new Version();
		version.setName(versionId);
		version.setVersion(value);
		version.setEnvironment(environmentManager.query(versionId, productId));
		return version;
	}

	public List<Version> query(String productId) {
		ConfigPath configPath = new ConfigPath();
		configPath.setProductName(productId);
		Map<String ,String> sunModuleMap = configItemManager.getALL(configPath);
		List<Version> versions = new ArrayList<Version>();
		for (Iterator<String> iterator = sunModuleMap.keySet().iterator(); iterator.hasNext();) {
			String versionId = iterator.next();
			Version version = get(versionId, productId);
			if (version != null) {
				versions.add(version);
			}
		}
		return versions;
	}

}
