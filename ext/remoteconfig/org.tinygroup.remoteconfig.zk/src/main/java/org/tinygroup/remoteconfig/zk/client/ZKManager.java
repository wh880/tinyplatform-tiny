package org.tinygroup.remoteconfig.zk.client;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.tinygroup.exception.BaseRuntimeException;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.remoteconfig.config.ConfigPath;
import org.tinygroup.remoteconfig.zk.config.RemoteConfig;
import org.tinygroup.remoteconfig.zk.config.RemoteEnvironment;
import org.tinygroup.remoteconfig.zk.utils.PathHelper;

public class ZKManager{

	protected static final Logger LOGGER = LoggerFactory
			.getLogger(ZKConfigClientImpl.class);
	
	private static ZooKeeper zooKeeper;
	
	private static RemoteConfig config;
	
	public static RemoteConfig getConfig() {
		return config;
	}
	
	static {
		if (zooKeeper == null) {
			LOGGER.logMessage(LogLevel.INFO, "开始客户端远程配置初始化...");
			config = RemoteEnvironment.getConfig();
			try {
				LOGGER.logMessage(LogLevel.INFO, "创建远程链接...");
				zooKeeper = new ZooKeeper(config.getUrls(), 2000 ,null);
				LOGGER.logMessage(LogLevel.INFO, "远程链接成功...");
				//创建项目基础节点,不用监听
				LOGGER.logMessage(LogLevel.INFO, "初始化ZK根节点");
				LOGGER.logMessage(LogLevel.INFO, "客户端远程配置初始化完成");
			} catch (IOException e) {
				throw new BaseRuntimeException("0TE120119001" ,e ,config.toString());
			}
		}
	}
	
	public static boolean exists(String key ,ConfigPath configPath){
		try {
			key = PathHelper.createPath(key ,configPath);
			LOGGER.logMessage(LogLevel.DEBUG, String.format("远程配置，判断节点是否存在[%s]" ,key));
			Stat stat = zooKeeper.exists(key, false);
			if (stat == null) {
				return false;
			}else {
				return true;
			}
		}  catch (KeeperException e) {
			throw new BaseRuntimeException(e);
		} catch (InterruptedException e) {
			throw new BaseRuntimeException(e);
		}
	}

	public static String get(String key ,ConfigPath configPath){
		try {
			key = PathHelper.createPath(key ,configPath);
			LOGGER.logMessage(LogLevel.DEBUG, String.format("远程配置，获取节点[%s]" ,key));
			return getSimple(key);
		} catch (KeeperException e) {
			throw new BaseRuntimeException("0TE120119003", e ,key);
		} catch (InterruptedException e) {
			throw new BaseRuntimeException("0TE120119004", e ,key);
		}
	}

	public static Map<String, String> getALL(ConfigPath configPath) {
		Map<String, String> dataMap = new HashMap<String, String>();
		String node = PathHelper.createPath("" ,configPath);
		LOGGER.logMessage(LogLevel.DEBUG, String.format("远程配置，批量获取节点[%s]" ,node));
		try {
			List<String> subNodes = zooKeeper.getChildren(node, false);
			if (subNodes != null && !subNodes.isEmpty()) {
				for (String subNode : subNodes) {
					String znodeValue = getSimple(node.concat("/").concat(subNode));
					dataMap.put(subNode, znodeValue);
				}
			}
		} catch (KeeperException e) {
			throw new BaseRuntimeException("0TE120119005", e ,node);
		} catch (InterruptedException e) {
			throw new BaseRuntimeException("0TE120119006", e ,node);
		}
		return dataMap;
	}

	public static void stop() {
		if (zooKeeper != null) {
			try {
				zooKeeper.close();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static void set(String key, String value, ConfigPath configPath) {
		key = PathHelper.createPath(key ,configPath);
		LOGGER.logMessage(LogLevel.DEBUG, String.format("远程配置，节点设值[%s=%s]" ,key ,value));
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
						addSimple(parentPath ,"");
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

	public static void delete(String key, ConfigPath configPath) {
		key = PathHelper.createPath(key ,configPath);
		LOGGER.logMessage(LogLevel.DEBUG, String.format("远程配置，删除单节点[%s]" ,key));
		clearSimple(key);
	}

	private static void deleteSimple(String node){
		try {
			Stat stat = zooKeeper.exists(node, false);
			if (stat != null) {
				zooKeeper.delete(node, stat.getVersion());
			}else {
				LOGGER.logMessage(LogLevel.DEBUG, String.format("节点[%s]不存在" ,node));
			}
		} catch (KeeperException e) {
			throw new BaseRuntimeException("0TE120119007", e ,node);
		} catch (InterruptedException e) {
			throw new BaseRuntimeException("0TE120119008", e ,node);
		}
	}
	
	private static void clearSimple(String node){
		try {
			Stat stat = zooKeeper.exists(node, false);
			if (stat != null) {
				for (String subNode : zooKeeper.getChildren(node, false)) {
					clearSimple(node.concat("/").concat(subNode));
				}
				deleteSimple(node);
			}
		} catch (KeeperException e) {
			throw new BaseRuntimeException("0TE120119009", e ,node);
		} catch (InterruptedException e) {
			throw new BaseRuntimeException("0TE120119010", e ,node);
		}
	}
	
	private static String getSimple(String key) throws KeeperException, InterruptedException{
		Stat stat = zooKeeper.exists(key, false);
		if (stat != null) {
			byte[] resultData = zooKeeper.getData(key, false, stat);
			if (resultData != null) {
				String value = new String(resultData);
				return value;
			}
		}
		return null;
	}
	
}
