/**
 * 
 */
package org.tinygroup.remoteconfig.utils;

import org.apache.commons.lang.StringUtils;
import org.tinygroup.remoteconfig.model.RemoteConfig;
import org.tinygroup.remoteconfig.service.NodeCache;

/**
 * @author yanwj
 *
 */
public class ConfigParseUtils {

	public static RemoteConfig createConfig(String id){
		String node = NodeCache.getNodeById(id);
		if (StringUtils.isBlank(node)) {
			return null;
		}
		return parseNode(node);
	}
	
	private static RemoteConfig parseNode(String key){
		String app = "";
		String env = "";
		String version = "";
		String[] nodes = StringUtils.split(key, "/");
		if (nodes.length > 0) {
			app = nodes[0];
		}
		if (nodes.length > 1) {
			env = nodes[1];
		}
		if (nodes.length > 2) {
			version = nodes[2];
		}
		RemoteConfig config = new RemoteConfig(null, app, env, version);
		return config;
	}
	
}
