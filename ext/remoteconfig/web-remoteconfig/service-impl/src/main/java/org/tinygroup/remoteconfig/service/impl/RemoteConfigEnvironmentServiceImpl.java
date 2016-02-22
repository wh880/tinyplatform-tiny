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
import org.tinygroup.remoteconfig.manager.EnvironmentManager;
import org.tinygroup.remoteconfig.service.inter.RemoteConfigEnvService;

/**
 * @author yanwj
 *
 */
public class RemoteConfigEnvironmentServiceImpl implements RemoteConfigEnvService{

	EnvironmentManager environmentManager;
	
	public void setEnvironmentManager(EnvironmentManager environmentManager) {
		this.environmentManager = environmentManager;
	}
	
	protected static final Logger LOGGER = LoggerFactory
			.getLogger(RemoteConfigEnvironmentServiceImpl.class);

	public void add(Environment environment,
			String versionId, String productId) {
		environmentManager.add(environment, versionId, productId);
	}

	public void set(Environment oldEnvironment,Environment newEnvironment,
			String versionId, String productId) {
		try {
			//如果路径发生变化
			if (!StringUtils.equals(oldEnvironment.getName(), newEnvironment.getName())) {
				LOGGER.errorMessage(String.format("环境无法修改key[%s=%s]", newEnvironment.getName(), newEnvironment.getEnvironment()));
				return;
			}else {
				//普通修改，key未变化
				environmentManager.update(newEnvironment, versionId, productId);
			}
		} catch (BaseRuntimeException e) {
			LOGGER.errorMessage(String.format("环境设值操作失败[%s=%s]", newEnvironment.getName(), newEnvironment.getEnvironment()) ,e);
		}
	}

	public void delete(Environment environment,String versionId, String productId) {
		environmentManager.delete(environment.getName(), versionId, productId);
	}

	public org.tinygroup.remoteconfig.config.Environment get(
			org.tinygroup.remoteconfig.config.Environment environment,
			String versionId, String productId) {
		return environmentManager.get(environment.getName(), versionId, productId);
	}

	public List<org.tinygroup.remoteconfig.config.Environment> query(
			org.tinygroup.remoteconfig.config.Environment environment,
			String versionId, String productId) {
		return environmentManager.query(versionId, productId);
	}
	
}
