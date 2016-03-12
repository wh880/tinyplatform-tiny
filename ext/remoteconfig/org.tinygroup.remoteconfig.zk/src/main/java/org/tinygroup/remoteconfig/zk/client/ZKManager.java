package org.tinygroup.remoteconfig.zk.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.Stat;
import org.tinygroup.exception.BaseRuntimeException;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.remoteconfig.config.ConfigPath;
import org.tinygroup.remoteconfig.config.Module;
import org.tinygroup.remoteconfig.zk.utils.PathHelper;
import org.tinygroup.remoteconfig.zk.utils.SerializeUtil;

public class ZKManager extends BaseManager{

	public static String get(String key ,ConfigPath configPath){
		try {
			key = PathHelper.createPath(key ,configPath);
			return getSimple(key);
		} catch (KeeperException e) {
			throw new BaseRuntimeException("0TE120119003", e ,key);
		} catch (InterruptedException e) {
			throw new BaseRuntimeException("0TE120119004", e ,key);
		}
	}

	public static Map<String, String> getAll(ConfigPath configPath) {
		Map<String, String> dataMap = new HashMap<String, String>();
		String node = PathHelper.createPath("" ,configPath);
		try {
			List<String> subNodes = zooKeeper.getChildren(node, false);
			if (subNodes != null && !subNodes.isEmpty()) {
				for (String subNode : subNodes) {
					String znodeValue = getSimple(node.concat("/").concat(subNode));
					if (znodeValue != null) {
						dataMap.put(subNode, znodeValue);
					}
				}
			}
		} catch (KeeperException e) {
			//当没有找到子项时，会抛出此异常，我们不做任何处理
		} catch (InterruptedException e) {
			throw new BaseRuntimeException("0TE120119006", e ,node);
		}
		return dataMap;
	}

	public static void set(String key, String value, ConfigPath configPath) {
		key = PathHelper.createPath(key ,configPath);
		try {
			Stat stat = zooKeeper.exists(key, false);
			if (stat == null) {
				LOGGER.logMessage(LogLevel.DEBUG, String.format("节点设值[%s=%s]，节点不存在，自动创建" ,key ,value));
				addSimple(key ,value);
				return;
			}
			zooKeeper.setData(key, value.getBytes(),stat.getVersion());
		} catch (Exception e) {
			throw new BaseRuntimeException("0TE120119002", e ,key ,value);
		}
	}

	private static Stat addSimple(String node, String value) throws BaseRuntimeException{
		try {
			Stat stat = zooKeeper.exists(node, false);
			if (stat == null) {
				//  create parent znodePath
				String parentPath = PathHelper.generateParentPath(node);
				if (StringUtils.isNotBlank(parentPath)) {
					Stat parentStat = zooKeeper.exists(parentPath, false);
					if (parentStat == null) {
						addModule(parentPath, new Module());
					}
				}
				zooKeeper.create(node, value.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			}else {
				LOGGER.logMessage(LogLevel.DEBUG, String.format("节点[%s]已经存在" ,node));
			}
			return zooKeeper.exists(node, true);
		} catch (KeeperException e) {
			throw new BaseRuntimeException("0TE120119011", e ,node ,value);
		} catch (InterruptedException e) {
			throw new BaseRuntimeException("0TE120119012", e ,node ,value);
		}
	}
	
	private static Stat addModule(String node, Module module) throws BaseRuntimeException{
		try {
			Stat stat = zooKeeper.exists(node, false);
			if (stat == null) {
				//  create parent znodePath
				String parentPath = PathHelper.generateParentPath(node);
				if (StringUtils.isNotBlank(parentPath)) {
					Stat parentStat = zooKeeper.exists(parentPath, false);
					if (parentStat == null) {
						addModule(parentPath ,new Module());
					}
				}
				String name = StringUtils.substringAfterLast(node, "/");
				if (StringUtils.isBlank(module.getName())) {
					module.setName(name);
				}
				if (StringUtils.isBlank(module.getModuleName())) {
					module.setModuleName(name);
				}
				zooKeeper.create(node, SerializeUtil.serialize(module), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			}
			return zooKeeper.exists(node, true);
		} catch (KeeperException e) {
			throw new BaseRuntimeException("0TE120119011", e ,node ,module);
		} catch (InterruptedException e) {
			throw new BaseRuntimeException("0TE120119012", e ,node ,module);
		}
	}

	private static String getSimple(String key) throws KeeperException, InterruptedException{
		Stat stat = zooKeeper.exists(key, false);
		if (stat != null) {
			byte[] resultData = zooKeeper.getData(key, false, stat);
			if (resultData != null) {
				try {
					Object obj =  SerializeUtil.unserialize(resultData);
					if (obj == null) {
						return new String(resultData);
					}else if (obj instanceof String) {
						return obj.toString();
					}
				} catch (ClassCastException e) {
					
				}
			}
		}
		return null;
	}
	
}
