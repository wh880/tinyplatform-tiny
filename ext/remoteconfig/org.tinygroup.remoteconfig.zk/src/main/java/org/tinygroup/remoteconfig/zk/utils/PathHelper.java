package org.tinygroup.remoteconfig.zk.utils;

import org.apache.commons.lang.StringUtils;
import org.tinygroup.remoteconfig.config.ConfigPath;
import org.tinygroup.remoteconfig.zk.client.IRemoteConfigZKConstant;

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
	 * 获取全路径
	 * 
	 * @param config
	 * @return
	 */
	public static String createPath(ConfigPath configPath) {
		String url = "";
		if (configPath != null) {
			if (StringUtils.isNotBlank(configPath.getProductName())) {
				url = url.concat(appendSplit(configPath.getProductName()));
			}
			if (StringUtils.isNotBlank(configPath.getVersionName())) {
				url = url.concat(appendSplit(configPath.getVersionName()));
			}
			if (StringUtils.isNotBlank(configPath.getEnvironmentName())) {
				url = url.concat(appendSplit(configPath.getEnvironmentName()));
			}
			if (StringUtils.isNotBlank(configPath.getModulePath())) {
				url = url.concat(appendSplit(configPath.getModulePath()));
			}
			return url;
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
	
	public static String createPath(String node ,ConfigPath configPath){
		String baseDir = IRemoteConfigZKConstant.REMOTE_BASE_DIR + PathHelper.createPath(configPath);
		if (StringUtils.isNotBlank(node) ) {
			node = appendSplit(node);
		}else {
			return baseDir;
		}
		return baseDir.concat(node);
	}
	
	public static String appendSplit(String node){
		if (!StringUtils.startsWith(node, "/")) {
			node = "/".concat(node);
		}
		return node;
	}
	
}
