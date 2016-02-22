/**
 * 
 */
package org.tinygroup.remoteconfig.service.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.tinygroup.remoteconfig.config.ConfigPath;
import org.tinygroup.remoteconfig.manager.ConfigItemManager;
import org.tinygroup.remoteconfig.service.DefaultEnvironment;

/**
 * @author yanwj
 *
 */
public class EnvironmentHelper {

	/**
	 * 获取其他环境，除了初始环境以外的所有环境
	 * 
	 * @param client
	 * @param defaultPath
	 * @return
	 */
	public static List<ConfigPath> getOtherEnvPath(ConfigItemManager client ,ConfigPath defaultPath){
		List<ConfigPath> otherPath = new ArrayList<ConfigPath>();
		ConfigPath tempConfigPath = new ConfigPath();
		tempConfigPath.setVersionName(defaultPath.getVersionName());
		tempConfigPath.setProductName(defaultPath.getProductName());
		Map<String ,String> envMap = client.getALL(tempConfigPath);
		for (Iterator<String> iterator = envMap.keySet().iterator(); iterator.hasNext();) {
			String key = iterator.next();
			if ( StringUtils.equals(DefaultEnvironment.DEFAULT.getName(), key)) {
				continue;
			}
			ConfigPath configPath = new ConfigPath();
			configPath.setVersionName(defaultPath.getVersionName());
			configPath.setProductName(defaultPath.getProductName());
			configPath.setEnvironmentName(key);
			configPath.setModulePath(defaultPath.getModulePath());
			otherPath.add(configPath);
		}
		return otherPath;
	}
	
	public static ConfigPath getDefaultEnvPath(ConfigPath configPath){
		ConfigPath tempConfigPath = new ConfigPath();
		tempConfigPath.setVersionName(configPath.getVersionName());
		tempConfigPath.setProductName(configPath.getProductName());
		tempConfigPath.setEnvironmentName(DefaultEnvironment.DEFAULT.getName());
		tempConfigPath.setModulePath(configPath.getModulePath());
		return tempConfigPath;
	}
	
}
