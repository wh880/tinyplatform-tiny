package org.tinygroup.jedis.fileresolver;

import org.tinygroup.fileresolver.impl.AbstractFileProcessor;
import org.tinygroup.jedis.ShardJedisSentinelManager;
import org.tinygroup.jedis.config.ShardJedisSentinelConfigs;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.vfs.FileObject;
import org.tinygroup.xstream.XStreamFactory;

import com.thoughtworks.xstream.XStream;

/**
 * Jedis配置文件处理器，加载Jedis客户端配置信息
 * 
 * @author yancheng11334
 * 
 */
public class JedisShardSentinelConfigsFileProcessor extends AbstractFileProcessor {

	private static final String JEDIS_SHARD_SENTRINEL_CONFIG_EXT_NAME = ".jedisshardsentrinelconfig.xml";

	private static final String JEDIS_SHARD_SENTRINEL_XSTREAM_NAME = "jedis";

	private ShardJedisSentinelManager shardJedisSentinelManager;

	



	public ShardJedisSentinelManager getShardJedisSentinelManager() {
		return shardJedisSentinelManager;
	}

	public void setShardJedisSentinelManager(
			ShardJedisSentinelManager shardJedisSentinelManager) {
		this.shardJedisSentinelManager = shardJedisSentinelManager;
	}

	public void process() {
		XStream stream = XStreamFactory.getXStream(JEDIS_SHARD_SENTRINEL_XSTREAM_NAME);
		for (FileObject fileObject : deleteList) {
			LOGGER.logMessage(LogLevel.INFO, "正在移除Redis分片主从集群配置文件[{0}]",
					fileObject.getAbsolutePath());
			ShardJedisSentinelConfigs shardJedisSentinelConfigs = (ShardJedisSentinelConfigs) caches.get(fileObject
					.getAbsolutePath());
			if (shardJedisSentinelConfigs != null) {
				shardJedisSentinelManager.removeJedisSentinelConfigs(shardJedisSentinelConfigs);
				caches.remove(fileObject.getAbsolutePath());
			}
			LOGGER.logMessage(LogLevel.INFO, "移除Redis分片主从集群配置文件[{0}]结束",
					fileObject.getAbsolutePath());
		}
		for (FileObject fileObject : changeList) {
			LOGGER.logMessage(LogLevel.INFO, "正在加载Redis分片主从集群配置文件[{0}]",
					fileObject.getAbsolutePath());
			ShardJedisSentinelConfigs oldShardJedisSentinelConfigs = (ShardJedisSentinelConfigs) caches.get(fileObject
					.getAbsolutePath());
			if (oldShardJedisSentinelConfigs != null) {
				shardJedisSentinelManager.removeJedisSentinelConfigs(oldShardJedisSentinelConfigs);
			}
			ShardJedisSentinelConfigs shardJedisSentinelConfigs = (ShardJedisSentinelConfigs) stream
					.fromXML(fileObject.getInputStream());
			shardJedisSentinelManager.addJedisSentinelConfigs(shardJedisSentinelConfigs);
			caches.put(fileObject.getAbsolutePath(), shardJedisSentinelConfigs);
			LOGGER.logMessage(LogLevel.INFO, "加载Redis分片主从集群配置文件[{0}]结束",
					fileObject.getAbsolutePath());
		}
	}

	@Override
	protected boolean checkMatch(FileObject fileObject) {
		return fileObject.getFileName().endsWith(JEDIS_SHARD_SENTRINEL_CONFIG_EXT_NAME);
	}

}
