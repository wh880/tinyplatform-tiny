package org.tinygroup.remoteconfig.zk.utils;

import org.apache.commons.lang.StringUtils;
import org.tinygroup.remoteconfig.zk.client.IRemoteConfigZKConstant;

public class EnvPathHelper {
	
	public static String createPath(String node){
		String baseDir = IRemoteConfigZKConstant.REMOTE_ENVIRONMENT_BASE_DIR ;
		if (StringUtils.isNotBlank(node) ) {
			node = PathHelper.appendSplit(node);
		}else {
			return baseDir;
		}
		return baseDir.concat(node);
	}
	
}
