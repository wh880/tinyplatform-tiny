/**
 * 
 */
package org.tinygroup.remoteconfig.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.tinygroup.exception.BaseRuntimeException;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.remoteconfig.config.Environment;
import org.tinygroup.remoteconfig.config.Version;
import org.tinygroup.remoteconfig.manager.VersionManager;
import org.tinygroup.remoteconfig.service.DefaultEnvironment;
import org.tinygroup.remoteconfig.service.inter.RemoteConfigEnvService;
import org.tinygroup.remoteconfig.service.inter.RemoteConfigVersionService;

/**
 * @author yanwj
 *
 */
public class RemoteConfigVersionServiceImpl implements RemoteConfigVersionService{

	protected static final Logger LOGGER = LoggerFactory
			.getLogger(RemoteConfigVersionServiceImpl.class);
	
	VersionManager versionManager;
	
	RemoteConfigEnvService remoteConfigEnvService;
	
	public void setVersionManager(VersionManager versionManager) {
		this.versionManager = versionManager;
	}
	
	public void setRemoteConfigEnvService(
			RemoteConfigEnvService remoteConfigEnvService) {
		this.remoteConfigEnvService = remoteConfigEnvService;
	}
	
	public void add(Version version, String productId) {
		versionManager.add(version, productId);
		//初始化环境
		initEnv(version.getName(), productId);
	}

	public void set(Version oldVersion, Version newVersion, String productId) {
		try {
			//如果路径发生变化
			if (!StringUtils.equals(oldVersion.getName(), newVersion.getName())) {
				LOGGER.errorMessage(String.format("版本无法修改key[%s=%s]", newVersion.getName(), newVersion.getVersion()));
				return;
			}else {
				//普通修改，key未变化
				versionManager.update(newVersion, productId);
			}
		} catch (BaseRuntimeException e) {
			LOGGER.errorMessage(String.format("版本设值操作失败[%s=%s]", newVersion.getName(), newVersion.getVersion()) ,e);
		}
	}

	public void delete(Version version, String productId) {
		versionManager.delete(version.getName(), productId);
	}

	public Version get(Version version, String productId) {
		return versionManager.get(version.getName(), productId);
	}

	public List<Version> query(Version version, String productId) {
		return versionManager.query(productId);
	}

	private void initEnv(String versionId , String produceId) {
		for (DefaultEnvironment env : DefaultEnvironment.getAllEnv()) {
			Environment environment = new Environment();
			environment.setName(env.getName());
			environment.setEnvironment(env.getDesc());
			remoteConfigEnvService.add(environment, versionId, produceId);
		}
	}
	
}
