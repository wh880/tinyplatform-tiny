/**
 * 
 */
package org.tinygroup.remoteconfig.service.bank;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.remoteconfig.IRemoteConfigConstant;
import org.tinygroup.remoteconfig.config.ConfigPath;
import org.tinygroup.remoteconfig.service.DefaultEnvironment;
import org.tinygroup.remoteconfig.service.NodeCache;
import org.tinygroup.remoteconfig.service.inter.pojo.ConfigServiceItem;
import org.tinygroup.remoteconfig.service.utils.EnvironmentHelper;
import org.tinygroup.remoteconfig.service.utils.PathHelper;
import org.tinygroup.remoteconfig.service.utils.WebUtils;

/**
 * 
 * 此服务只操作模块
 * 
 * @author yanwj
 *
 */
public class RemoteConfigTreeServiceImpl extends RemoteConfigServiceImpl{

	protected static final Logger LOGGER = LoggerFactory
			.getLogger(RemoteConfigTreeServiceImpl.class);
	
	@Override
	public void add(ConfigServiceItem item) {
		super.add(item);
		//设置模块标志位
		String nodeName = WebUtils.createPath(item.getKey(), item.getConfigPath());
		//初始化环境
		if (StringUtils.split(nodeName, "/").length == 2) {
			initEnv(nodeName);
		}
		setDefaultModelFalg(item);
	}
	
	/**
	 * 初始环境发生变化的话，需要同时调整相同版本下其他环境的数据
	 * 
	 */
	public void delete(ConfigServiceItem item) {
		//必须是初始环境
		if (item != null && item.getConfigPath() != null) {
			if (StringUtils.equals(DefaultEnvironment.DEFAULT.getName(), item.getConfigPath().getEnvironmentName())) {
				List<String> subNodes = new ArrayList<String>();
				deleteNode(item ,subNodes);
				//操作其他环境
				List<ConfigPath> otherConfigPath = EnvironmentHelper.getOtherEnvPath(client, item.getConfigPath());
				for (ConfigPath configPath : otherConfigPath) {
					ConfigServiceItem tempServiceItem = new ConfigServiceItem(item.getKey(), "", configPath);
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
		client.delete(item.getKey(), item.getConfigPath());
		//删除缓存
		String node = WebUtils.createPath(item.getKey(), item.getConfigPath());
		NodeCache.deleteNodeByNode(node);
		for (Iterator<Entry<String,String>> iterator = NodeCache.getNodeCache().entrySet().iterator(); iterator.hasNext();) {
			Entry<String,String> entry = iterator.next();
			String key = entry.getKey();
			if (StringUtils.startsWith(key, node.concat("/"))) {
				subNodes.add(key);
			}
		}
	}
	
	/**
	 * 设置默认模块表示，用以区分模块和非模块
	 * 
	 * @param serviceItem
	 */
	protected void setDefaultModelFalg(ConfigServiceItem serviceItem){
		serviceItem.setKey(PathHelper.getConfigPath(serviceItem.getKey() ,IRemoteConfigConstant.MODULE_FLAG));
		serviceItem.setValue(IRemoteConfigConstant.MODULE_FLAG);
		super.add(serviceItem);
	}
	
	/**
	 * 判断当前应用配置环境是否初始化
	 * 如果已经存在，则跳过
	 * 如果未初始化，则初始化
	 * 
	 */
	private void initEnv(String parentNode) {
		for (DefaultEnvironment env : DefaultEnvironment.getAllEnv()) {
			String node = PathHelper.getConfigPath(parentNode ,env.getName());
			ConfigServiceItem envItem = new ConfigServiceItem("" ,env.getDesc() ,PathHelper.createConfigPath(node));
			super.add(envItem);
			setDefaultModelFalg(envItem);
		}
	}
	
}
