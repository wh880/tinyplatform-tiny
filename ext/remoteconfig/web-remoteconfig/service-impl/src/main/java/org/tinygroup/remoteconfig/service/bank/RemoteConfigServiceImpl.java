/**
 * 
 */
package org.tinygroup.remoteconfig.service.bank;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.tinygroup.exception.BaseRuntimeException;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.remoteconfig.IRemoteConfigConstant;
import org.tinygroup.remoteconfig.manager.ConfigItemManager;
import org.tinygroup.remoteconfig.service.NodeCache;
import org.tinygroup.remoteconfig.service.inter.RemoteConfigService;
import org.tinygroup.remoteconfig.service.inter.pojo.ConfigServiceItem;
import org.tinygroup.remoteconfig.service.utils.WebUtils;

/**
 * @author yanwj
 *
 */
public class RemoteConfigServiceImpl implements RemoteConfigService{

	protected static final Logger LOGGER = LoggerFactory
			.getLogger(RemoteConfigServiceImpl.class);
	
	ConfigItemManager client;
	
	public void setClient(ConfigItemManager client) {
		this.client = client;
	}

	public void add(ConfigServiceItem item) {
		try {
			client.set(item.getKey(), item.getValue() ,item.getConfigPath());
			NodeCache.createNodeId(WebUtils.createPath(item.getKey(), item.getConfigPath()), null);
		} catch (BaseRuntimeException e) {
			LOGGER.errorMessage(String.format("增加操作失败[%s=%s]", item.getKey(), item.getValue()) ,e);
		}
	}

	/**
	 * 树节点不允许修改key
	 */
	public void set(String oldId ,ConfigServiceItem item) {
		try {
			String newPath = WebUtils.createPath(item.getKey(), item.getConfigPath());
			String oldNode = NodeCache.getNodeById(oldId);
			//先判断是不是处理模块，如果是模块，则不允许修改key
			Map<String, String> itemMap = getAll(item);
			
			//如果路径发生变化
			if (!StringUtils.equals(newPath, oldNode)) {
				if (itemMap.get(IRemoteConfigConstant.MODULE_FLAG) == null) {
					NodeCache.updateIdCache(oldId ,newPath);
					delete(WebUtils.createConfigPath(oldNode, ""));
					return;
				}
				LOGGER.errorMessage(String.format("模块无法修改key[%s=%s]", item.getKey(), item.getValue()));
			}else {
				//普通修改，key未变化
				client.set(item.getKey(), item.getValue() ,item.getConfigPath());
			}
		} catch (BaseRuntimeException e) {
			LOGGER.errorMessage(String.format("设值操作失败[%s=%s]", item.getKey(), item.getValue()) ,e);
		}
	}

	public String get(ConfigServiceItem item) {
		NodeCache.createNodeId(WebUtils.createPath(item.getKey(), item.getConfigPath()), null);
		return client.get(item.getKey(), item.getConfigPath());
	}

	public Map<String ,String> getAll(ConfigServiceItem item) {
		try {
			
			Map<String, String> results = client.getALL(item.getConfigPath());
			for (Iterator<String> iterator = results.keySet().iterator(); iterator.hasNext();) {
				String type = iterator.next();
				NodeCache.createNodeId(WebUtils.createPath(type, item.getConfigPath()), null);
			}
			return results;
		} catch (Exception e) {
			return new HashMap<String ,String>();
		}
	}

	public void delete(ConfigServiceItem item) {
		client.delete(item.getKey(), item.getConfigPath());
		//删除缓存
		String node = WebUtils.createPath(item.getKey(), item.getConfigPath());
		NodeCache.deleteNodeByNode(node);
		List<String> subNodes = new ArrayList<String>();
		for (Iterator<String> iterator = NodeCache.getNodeCache().keySet().iterator(); iterator.hasNext();) {
			String key = iterator.next();
			if (StringUtils.startsWith(key, node.concat("/"))) {
				subNodes.add(key);
			}
		}
		for (String key : subNodes) {
			NodeCache.deleteNodeByNode(key);
		}
	}

	public void deletes(List<ConfigServiceItem> items) {
		for (ConfigServiceItem item : items) {
			delete(item);
		}
	}

	public boolean isExit(ConfigServiceItem item){
		return client.exists(item.getKey(), item.getConfigPath());
	}
	
}
