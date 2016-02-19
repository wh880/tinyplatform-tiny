/**
 * 
 */
package org.tinygroup.remoteconfig.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.remoteconfig.config.ConfigPath;
import org.tinygroup.remoteconfig.service.NodeCache;
import org.tinygroup.remoteconfig.service.inter.pojo.ConfigServiceItem;
import org.tinygroup.remoteconfig.service.utils.EnvironmentHelper;
import org.tinygroup.remoteconfig.service.utils.WebUtils;

/**
 * 
 * 此服务只操作模块
 * 
 * @author yanwj
 *
 */
public class RemoteConfigNodeServiceImpl extends RemoteConfigServiceImpl{

	protected static final Logger LOGGER = LoggerFactory
			.getLogger(RemoteConfigNodeServiceImpl.class);
	
	
	/**
	 * 初始环境发生变化的话，需要同时调整相同版本下其他环境的数据
	 * 
	 */
	public void delete(ConfigServiceItem item) {
		//必须是初始环境
		if (item != null && item.getConfigPath() != null) {
			if (StringUtils.equals(org.tinygroup.remoteconfig.service.Environment.DEFAULT.getName(), item.getConfigPath().getEnvironmentName())) {
				List<String> subNodes = new ArrayList<String>();
				deleteNode(item ,subNodes);
				//操作其他环境
				List<ConfigPath> otherConfigPath = EnvironmentHelper.getOtherEnvPath(client, item.getConfigPath());
				for (ConfigPath configPath : otherConfigPath) {
					ConfigServiceItem tempServiceItem = new ConfigServiceItem(item.getNode(), "", configPath);
					deleteNode(tempServiceItem ,subNodes);
				}
				for (String node : subNodes) {
					NodeCache.deleteNodeByNode(node);
				}
			}else {
				super.delete(item);
			}
		}
	}

	public void deletes(List<ConfigServiceItem> items) {
		for (ConfigServiceItem item : items) {
			delete(item);
		}
	}

	private void deleteNode(ConfigServiceItem item ,List<String> subNodes){
		client.delete(item.getNode(), item.getConfigPath());
		//删除缓存
		String node = WebUtils.createPath(item.getNode(), item.getConfigPath());
		NodeCache.deleteNodeByNode(node);
		for (Iterator<Entry<String,String>> iterator = NodeCache.getNodeCache().entrySet().iterator(); iterator.hasNext();) {
			Entry<String,String> entry = iterator.next();
			String key = entry.getKey();
			if (StringUtils.startsWith(key, node.concat("/"))) {
				subNodes.add(key);
			}
		}
	}
	
}
