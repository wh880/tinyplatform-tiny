/**
 * 
 */
package org.tinygroup.remoteconfig.zk.client;

import java.io.IOException;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.tinygroup.exception.BaseRuntimeException;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.remoteconfig.config.ConfigPath;
import org.tinygroup.remoteconfig.config.Environment;
import org.tinygroup.remoteconfig.zk.config.RemoteConfig;
import org.tinygroup.remoteconfig.zk.config.RemoteEnvironment;
import org.tinygroup.remoteconfig.zk.utils.PathHelper;

/**
 * @author Administrator
 *
 */
public class BaseManager {

	protected static final Logger LOGGER = LoggerFactory
			.getLogger(BaseManager.class);
	
	protected static ZooKeeper zooKeeper;
	
	protected static RemoteConfig config;
	
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
				ZKManager.set("", IRemoteConfigZKConstant.REMOTE_ENVIRONMENT_BASE_DIR, null);
				Environment env = new Environment();
				env.setEnvironment(IRemoteConfigZKConstant.REMOTE_ENVIRONMENT_BASE_DIR);
				ZKDefaultEnvManager.set("", env);
				LOGGER.logMessage(LogLevel.INFO, "根节点创建完毕");
				LOGGER.logMessage(LogLevel.INFO, "客户端远程配置初始化完成");
			} catch (IOException e) {
				throw new BaseRuntimeException("0TE120119001" ,e ,config.toString());
			}
		}
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
	
	public static void delete(String key, ConfigPath configPath) {
		key = PathHelper.createPath(key ,configPath);
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
	
	public static boolean exists(String key ,ConfigPath configPath){
		try {
			key = PathHelper.createPath(key ,configPath);
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
	
}
