package org.tinygroup.remoteconfig.zk.utils;

import org.apache.commons.lang.StringUtils;
import org.tinygroup.remoteconfig.IRemoteConfigConstant;
import org.tinygroup.remoteconfig.config.ConfigPath;
import org.tinygroup.remoteconfig.model.RemoteConfig;

public class PathHelper {

	/**
	 * 获取父亲节点
	 * 
	 * @param znodePath
	 *            必须多于两级
	 * @return parent znodePath
	 */
	public static String generateParentPath(String znodePath) {
		String parentPath = null;
		if (znodePath != null && znodePath.lastIndexOf("/") != -1
				&& znodePath.lastIndexOf("/") > 0) {
			parentPath = znodePath.substring(0, znodePath.lastIndexOf("/"));
		}
		return parentPath;
	}

	/**
	 * 获取系统根路径
	 * 
	 * @param config
	 * @return
	 */
	public static String createURL(RemoteConfig config) {
		String url = IRemoteConfigConstant.REMOTE_BASE_DIR;
		if (StringUtils.isNotBlank(config.getApp())) {
			url = url.concat("/").concat(config.getApp());
		}
		if (StringUtils.isNotBlank(config.getEnv())) {
			url = url.concat("/").concat(config.getEnv());
		}
		if (StringUtils.isNotBlank(config.getVersion())) {
			url = url.concat("/").concat(config.getVersion());
		}
		return url;
	}

	/**
	 * 获取系统根路径
	 * 
	 * @param config
	 * @return
	 */
	public static String createPath(ConfigPath configPath) {
		String url = "";
		if (configPath != null) {
			if (StringUtils.isNotBlank(configPath.getProductName())) {
				url = url.concat("/").concat(configPath.getProductName());
			}
			if (StringUtils.isNotBlank(configPath.getVersionName())) {
				url = url.concat("/").concat(configPath.getVersionName());
			}
			if (StringUtils.isNotBlank(configPath.getEnvironmentName())) {
				url = url.concat("/").concat(configPath.getEnvironmentName());
			}
			if (StringUtils.isNotBlank(configPath.getModulePath())) {
				if (!StringUtils.startsWith(configPath.getModulePath(), "/")) {
					url = url.concat("/");
				}
				url = url.concat(configPath.getModulePath());
			}
			return url;
		}
		return url;
	}
	
	/**
	 * 获取项目下所有的环境
	 * 
	 * @param config
	 * @return
	 */
	public static String createAppPath(ConfigPath configPath) {
		String url = IRemoteConfigConstant.REMOTE_BASE_DIR;
		if (configPath != null) {
			if (StringUtils.isNotBlank(configPath.getProductName())) {
				url = url.concat("/").concat(configPath.getProductName());
			}
			if (StringUtils.isNotBlank(configPath.getVersionName())) {
				url = url.concat("/").concat(configPath.getVersionName());
			}
		}
		return url;
	}
	
	/**
	 * 获取系统根路径
	 * 
	 * @param config
	 * @return
	 */
	public static String getConfigPath(String...nodes) {
		String p = "";
		for (String node : nodes) {
			if (StringUtils.isNotBlank(node)) {
				p = p.concat("/").concat(node);
			}
		}
		return p.substring(1, p.length());
	}
	
	public static ConfigPath createConfigPath(String path){
		String[] paths = StringUtils.split(path , "/");
		ConfigPath configPath = new ConfigPath();
		if (paths != null && paths.length > 0) {
			if (paths.length > 0) {
				configPath.setProductName(paths[0]);
			}
			if (paths.length > 1) {
				configPath.setVersionName(paths[1]);
			}
			if (paths.length > 2) {
				configPath.setEnvironmentName(paths[2]);
			}
			if (paths.length > 3) {
				StringBuffer modulePath = new StringBuffer();
				for (int i = 3; i < paths.length; i++) {
					if (i != 0) {
						modulePath.append("/");
					}
					modulePath.append(paths[i]);
				}
				configPath.setModulePath(modulePath.toString());
			}
		}
		return configPath;
	}
	
	public static ConfigPath createConfigPath(String app ,String version , String env ,String modulePath){
		ConfigPath configPath = new ConfigPath();
		configPath.setProductName(app);
		configPath.setVersionName(version);
		configPath.setEnvironmentName(env);
		configPath.setModulePath(modulePath);
		return configPath;
	}
	
	public static String createPath(String node ,ConfigPath configPath){
		String baseDir = IRemoteConfigConstant.REMOTE_BASE_DIR + PathHelper.createPath(configPath);
		if (StringUtils.isNotBlank(node)) {
			node = "/".concat(node);
		}else {
			return baseDir;
		}
		return baseDir.concat(node);
	}
	
}
