/**
 * 
 */
package org.tinygroup.remoteconfig.zk.manager.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
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

	private static final Logger LOGGER = LoggerFactory
			.getLogger(VersionManagerImpl.class);
	
	EnvironmentManager environmentManager;
	
	public void setEnvironmentManager(EnvironmentManager environmentManager) {
		this.environmentManager = environmentManager;
	}
	
	public Version add(Version version, String productId) {
		LOGGER.logMessage(LogLevel.DEBUG, "远程配置，增加版本[项目=%s ,版本=%s]" ,productId ,version.getVersion());
		ConfigPath configPath = new ConfigPath();
		configPath.setProductName(productId);
		try {
			ZKVersionManager.set(version.getName(), version, configPath);
		} catch (Exception e) {
			LOGGER.logMessage(LogLevel.ERROR,"远程配置，增加版本失败[项目=%s ,版本=%s]" ,e ,productId ,version.getName());
			return null;
		}
		return version;
	}

	public void update(Version version, String productId) {
		LOGGER.logMessage(LogLevel.DEBUG, "远程配置，更新版本[项目=%s ,版本=%s]" ,productId ,version.getName());
		ConfigPath configPath = new ConfigPath();
		configPath.setProductName(productId);
		try {
			ZKVersionManager.set(version.getName(), version, configPath);
		} catch (Exception e) {
			LOGGER.logMessage(LogLevel.ERROR,"远程配置，更新版本失败[项目=%s ,版本=%s]" ,e ,productId ,version.getName());
		}
	}

	public void delete(String versionId, String productId) {
		LOGGER.logMessage(LogLevel.ERROR,"远程配置，删除版本[项目=%s ,版本=%s]" ,productId ,versionId);
		ConfigPath configPath = new ConfigPath();
		configPath.setProductName(productId);
		try {
			ZKVersionManager.delete(versionId, configPath);
		} catch (Exception e) {
			LOGGER.logMessage(LogLevel.ERROR,"远程配置，删除版本失败[项目=%s ,版本=%s]" ,e ,productId ,versionId);
		}
	}

	public Version get(String versionId, String productId) {
		LOGGER.logMessage(LogLevel.DEBUG, "远程配置，获取版本[项目=%s ,版本=%s]", productId ,versionId);
		ConfigPath configPath = new ConfigPath();
		configPath.setProductName(productId);
		try {
			Version version = ZKVersionManager.get(versionId, configPath);
			if (version == null) {
				return null;
			}
			version.setEnvironment(environmentManager.query(versionId, productId));
			return version;
		} catch (Exception e) {
			LOGGER.logMessage(LogLevel.ERROR,"远程配置，获取版本失败[项目=%s ,版本=%s]" ,e ,productId ,versionId);
		}
		return null;
	}

	public List<Version> query(String productId) {
		LOGGER.logMessage(LogLevel.DEBUG, "远程配置，批量获取版本[项目=%s]" ,productId);
		ConfigPath configPath = new ConfigPath();
		configPath.setProductName(productId);
		List<Version> versions = new ArrayList<Version>();
		try {
			Map<String ,Version> versionMap = ZKVersionManager.getAll(configPath);
			for (Iterator<String> iterator = versionMap.keySet().iterator(); iterator.hasNext();) {
				String versionId = iterator.next();
				Version version = get(versionId, productId);
				if (version != null) {
					versions.add(version);
				}
			}
		} catch (Exception e) {
			LOGGER.logMessage(LogLevel.ERROR,"远程配置，批量获取版本失败[项目=%s]" ,e ,productId );
		}
		return versions;
	}

}
