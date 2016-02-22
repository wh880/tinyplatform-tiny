/**
 * 
 */
package org.tinygroup.remoteconfig.service.utils;

import org.apache.commons.lang.StringUtils;
import org.tinygroup.remoteconfig.config.ConfigPath;
import org.tinygroup.remoteconfig.service.inter.pojo.ConfigServiceItem;

/**
 * @author yanwj
 *
 */
public class WebUtils {

	public static ConfigServiceItem createConfigPath(String path ,String value){
		ConfigServiceItem item = new ConfigServiceItem("", value, PathHelper.createConfigPath(path));
		return item;
	}
	
	public static String createPath(String node ,ConfigPath configPath){
		String baseDir = PathHelper.createPath(configPath);
		if (StringUtils.isNotBlank(node)) {
			node = "/".concat(node);
		}else {
			return baseDir;
		}
		return baseDir.concat(node);
	}
	
}
