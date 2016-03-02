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
import org.tinygroup.remoteconfig.config.Environment;
import org.tinygroup.remoteconfig.zk.utils.EnvPathHelper;
import org.tinygroup.remoteconfig.zk.utils.PathHelper;
import org.tinygroup.remoteconfig.zk.utils.SerializeUtil;

public class ZKDefaultEnvManager extends BaseManager{

	public static Environment get(String key){
		try {
			key = EnvPathHelper.createPath(key);
			LOGGER.logMessage(LogLevel.DEBUG, String.format("远程配置，获取环境[%s]" ,key));
			return getSimple(key);
		} catch (KeeperException e) {
			throw new BaseRuntimeException("0TE120119003", e ,key);
		} catch (InterruptedException e) {
			throw new BaseRuntimeException("0TE120119004", e ,key);
		}
	}

	public static Map<String, Environment> getAll() {
		Map<String, Environment> dataMap = new HashMap<String, Environment>();
		String node = EnvPathHelper.createPath("");
		LOGGER.logMessage(LogLevel.DEBUG, String.format("远程配置，批量获取环境[%s]" ,node));
		try {
			List<String> subNodes = zooKeeper.getChildren(node, false);
			if (subNodes != null && !subNodes.isEmpty()) {
				for (String subNode : subNodes) {
					Environment environment = getSimple(node.concat("/").concat(subNode));
					if (environment != null) {
						dataMap.put(subNode, environment);
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

	public static void set(String key, Environment environment) {
		key = EnvPathHelper.createPath(key);
		LOGGER.logMessage(LogLevel.DEBUG, String.format("远程配置，环境设值[%s=%s]" ,key ,environment));
		try {
			Stat stat = zooKeeper.exists(key, false);
			if (stat == null) {
				LOGGER.logMessage(LogLevel.DEBUG, String.format("环境设值[%s=%s]，环境不存在，自动创建" ,key ,environment));
				addSimple(key ,environment);
				return;
			}
			zooKeeper.setData(key, SerializeUtil.serialize(environment),stat.getVersion());
		} catch (Exception e) {
			throw new BaseRuntimeException("0TE120119002", e ,key ,environment);
		}
	}

	private static Stat addSimple(String node, Environment environment) throws BaseRuntimeException{
		try {
			Stat stat = zooKeeper.exists(node, false);
			if (stat == null) {
				//  create parent znodePath
				String parentPath = PathHelper.generateParentPath(node);
				if (StringUtils.isNotBlank(parentPath)) {
					Stat parentStat = zooKeeper.exists(parentPath, false);
					if (parentStat == null) {
						addSimple(parentPath ,new Environment());
					}
				}
				zooKeeper.create(node, SerializeUtil.serialize(environment), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			}else {
				LOGGER.logMessage(LogLevel.DEBUG, String.format("环境[%s]已经存在" ,node));
			}
			return zooKeeper.exists(node, true);
		} catch (KeeperException e) {
			throw new BaseRuntimeException("0TE120119011", e ,node ,environment);
		} catch (InterruptedException e) {
			throw new BaseRuntimeException("0TE120119012", e ,node ,environment);
		}
	}

	private static Environment getSimple(String key) throws KeeperException, InterruptedException{
		Stat stat = zooKeeper.exists(key, false);
		if (stat != null) {
			byte[] resultData = zooKeeper.getData(key, false, stat);
			if (resultData != null) {
				return SerializeUtil.unserialize(resultData);
			}
		}
		return null;
	}
	
	public static void delete(String key) {
		key = EnvPathHelper.createPath(key);
		LOGGER.logMessage(LogLevel.DEBUG, String.format("远程配置，删除环境[%s]" ,key));
		try {
			Stat stat = zooKeeper.exists(key, false);
			if (stat != null) {
				zooKeeper.delete(key, stat.getVersion());
			}else {
				LOGGER.logMessage(LogLevel.DEBUG, String.format("环境[%s]不存在" ,key));
			}
		} catch (KeeperException e) {
			throw new BaseRuntimeException("0TE120119007", e ,key);
		} catch (InterruptedException e) {
			throw new BaseRuntimeException("0TE120119008", e ,key);
		}
	}

}
