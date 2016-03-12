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
import org.tinygroup.remoteconfig.config.ConfigPath;
import org.tinygroup.remoteconfig.config.Module;
import org.tinygroup.remoteconfig.zk.utils.PathHelper;
import org.tinygroup.remoteconfig.zk.utils.SerializeUtil;

public class ZKModuleManager extends BaseManager{

	public static Module get(String key ,ConfigPath configPath){
		try {
			key = PathHelper.createPath(key ,configPath);
			return getSimple(key);
		} catch (KeeperException e) {
			throw new BaseRuntimeException("0TE120119003", e ,key);
		} catch (InterruptedException e) {
			throw new BaseRuntimeException("0TE120119004", e ,key);
		}
	}
	
	public static Map<String, Module> getAll(ConfigPath configPath) {
		Map<String, Module> dataMap = new HashMap<String, Module>();
		String node = PathHelper.createPath("" ,configPath);
		try {
			List<String> subNodes = zooKeeper.getChildren(node, false);
			if (subNodes != null && !subNodes.isEmpty()) {
				for (String subNode : subNodes) {
					Module module = getSimple(node.concat("/").concat(subNode));
					if (module != null) {
						dataMap.put(subNode, module);
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

	public static void set(String key, Module module, ConfigPath configPath) {
		key = PathHelper.createPath(key ,configPath);
		try {
			Stat stat = zooKeeper.exists(key, false);
			if (stat == null) {
				addSimple(key ,module);
				return;
			}
			zooKeeper.setData(key, SerializeUtil.serialize(module),stat.getVersion());
		} catch (Exception e) {
			throw new BaseRuntimeException("0TE120119002", e ,key ,module);
		}
	}

	private static Stat addSimple(String node, Module module) throws BaseRuntimeException{
		try {
			Stat stat = zooKeeper.exists(node, false);
			if (stat == null) {
				//  create parent znodePath
				String parentPath = PathHelper.generateParentPath(node);
				if (StringUtils.isNotBlank(parentPath)) {
					Stat parentStat = zooKeeper.exists(parentPath, false);
					if (parentStat == null) {
						addSimple(parentPath ,new Module());
					}
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

	private static Module getSimple(String key) throws KeeperException, InterruptedException{
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
