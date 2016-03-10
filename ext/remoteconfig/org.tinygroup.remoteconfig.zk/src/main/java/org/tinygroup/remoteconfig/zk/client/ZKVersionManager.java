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
import org.tinygroup.remoteconfig.config.Version;
import org.tinygroup.remoteconfig.zk.utils.PathHelper;
import org.tinygroup.remoteconfig.zk.utils.SerializeUtil;

public class ZKVersionManager extends BaseManager{

	public static Version get(String key ,ConfigPath configPath){
		try {
			key = PathHelper.createPath(key ,configPath);
			return getSimple(key);
		} catch (KeeperException e) {
			throw new BaseRuntimeException("0TE120119003", e ,key);
		} catch (InterruptedException e) {
			throw new BaseRuntimeException("0TE120119004", e ,key);
		}
	}
	
	public static Map<String, Version> getAll(ConfigPath configPath) {
		Map<String, Version> dataMap = new HashMap<String, Version>();
		String node = PathHelper.createPath("" ,configPath);
		try {
			List<String> subNodes = zooKeeper.getChildren(node, false);
			if (subNodes != null && !subNodes.isEmpty()) {
				for (String subNode : subNodes) {
					Version version = getSimple(node.concat("/").concat(subNode));
					if (version != null) {
						dataMap.put(subNode, version);
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

	public static void set(String key, Version version, ConfigPath configPath) {
		key = PathHelper.createPath(key ,configPath);
		
		try {
			Stat stat = zooKeeper.exists(key, false);
			if (stat == null) {
				LOGGER.logMessage(LogLevel.DEBUG, String.format("节点设值[%s=%s]，节点不存在，自动创建" ,key ,version));
				addSimple(key ,version);
				return;
			}
			zooKeeper.setData(key, SerializeUtil.serialize(version),stat.getVersion());
		} catch (Exception e) {
			throw new BaseRuntimeException("0TE120119002", e ,key ,version);
		}
	}

	private static Stat addSimple(String node, Version version) throws BaseRuntimeException{
		try {
			Stat stat = zooKeeper.exists(node, false);
			if (stat == null) {
				//  create parent znodePath
				String parentPath = PathHelper.generateParentPath(node);
				if (StringUtils.isNotBlank(parentPath)) {
					Stat parentStat = zooKeeper.exists(parentPath, false);
					if (parentStat == null) {
						addSimple(parentPath ,new Version());
					}
				}
				zooKeeper.create(node, SerializeUtil.serialize(version), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			}else {
				LOGGER.logMessage(LogLevel.DEBUG, String.format("节点[%s]已经存在" ,node));
			}
			return zooKeeper.exists(node, true);
		} catch (KeeperException e) {
			throw new BaseRuntimeException("0TE120119011", e ,node ,version);
		} catch (InterruptedException e) {
			throw new BaseRuntimeException("0TE120119012", e ,node ,version);
		}
	}

	private static Version getSimple(String key) throws KeeperException, InterruptedException{
		Stat stat = zooKeeper.exists(key, false);
		if (stat != null) {
			byte[] resultData = zooKeeper.getData(key, false, stat);
			if (resultData != null) {
				return SerializeUtil.unserialize(resultData);
			}
		}
		return null;
	}
	
}
