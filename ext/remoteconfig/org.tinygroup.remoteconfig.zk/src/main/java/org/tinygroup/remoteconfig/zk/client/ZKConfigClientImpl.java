package org.tinygroup.remoteconfig.zk.client;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.tinygroup.exception.BaseRuntimeException;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.remoteconfig.IRemoteConfigConstant;
import org.tinygroup.remoteconfig.RemoteConfigReadClient;
import org.tinygroup.remoteconfig.config.ConfigPath;
import org.tinygroup.remoteconfig.manager.ConfigItemReader;
import org.tinygroup.remoteconfig.model.RemoteConfig;
import org.tinygroup.remoteconfig.model.RemoteEnvironment;
import org.tinygroup.remoteconfig.utils.PathHelper;


public class ZKConfigClientImpl implements ConfigItemReader ,RemoteConfigReadClient{

	protected static final Logger LOGGER = LoggerFactory
			.getLogger(ZKConfigClientImpl.class);
	
	ConfigPath configPath;
	
	private ZooKeeper zooKeeper;
	private String baseDir;
	
	protected String createBaseNode(){
		return IRemoteConfigConstant.REMOTE_BASE_DIR + PathHelper.createPath(configPath);
//		return ZKHelper.createURL(RemotEnvironment.getConfig());
	}
	
	public boolean exists(String key) throws BaseRuntimeException{
		try {
			key = pathFix(key);
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

	public String get(String key) throws BaseRuntimeException {
		try {
			key = pathFix(key);
			LOGGER.logMessage(LogLevel.DEBUG, String.format("远程配置，获取节点[%s]" ,key));
			Stat stat = this.zooKeeper.exists(key, false);
			if (stat != null) {
				byte[] resultData = this.zooKeeper.getData(key, false, stat);
				if (resultData != null) {
					String value = new String(resultData);
					LOGGER.logMessage(LogLevel.DEBUG, String.format("节点[%s=%s]" ,key ,value));
					return value;
				}
			}else {
				LOGGER.logMessage(LogLevel.DEBUG, String.format("节点[%s]不存在" ,key));
			}
		} catch (KeeperException e) {
			throw new BaseRuntimeException("0TE120119003", e ,key);
		} catch (InterruptedException e) {
			throw new BaseRuntimeException("0TE120119004", e ,key);
		}
		return null;
	}


	public Map<String, String> getALL() throws BaseRuntimeException {
		Map<String, String> dataMap = new HashMap<String, String>();
		String node = pathFix("");
		LOGGER.logMessage(LogLevel.DEBUG, String.format("远程配置，批量获取节点[%s]" ,node));
		try {
			List<String> subNodes = this.zooKeeper.getChildren(node, false);
			if (subNodes != null && !subNodes.isEmpty()) {
				for (String subNode : subNodes) {
					String znodeValue = get(subNode);
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


	private String pathFix(String node){
		if (StringUtils.isNotBlank(node)) {
			node = "/".concat(node);
		}else {
			return baseDir;
		}
		return baseDir.concat(node);
	}
	
	public void start() {
		if (zooKeeper != null) {
			return;
		}
		LOGGER.logMessage(LogLevel.INFO, "开始客户端远程配置初始化...");
		RemoteConfig config = RemoteEnvironment.getConfig();
		baseDir = createBaseNode();
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

	public void stop() {
		if (zooKeeper != null) {
			try {
				zooKeeper.close();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void setConfigPath(ConfigPath configPath) {
		this.configPath = configPath;
		
	}
	
}
