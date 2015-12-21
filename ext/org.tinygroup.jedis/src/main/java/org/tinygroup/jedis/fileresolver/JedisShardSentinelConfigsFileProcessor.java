package org.tinygroup.jedis.fileresolver;

import org.tinygroup.fileresolver.impl.AbstractFileProcessor;
import org.tinygroup.jedis.ShardJedisSentinelManager;
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

	private static final String JEDIS_SHARD_SENTINEL_CONFIG_EXT_NAME = ".jedisshardsentrinelconfig.xml";

	public static final String JEDIS_SHARD_SENTINEL_XSTREAM_NAME = "jedis";

	private ShardJedisSentinelManager shardJedisSentinelManager;

	
	public ShardJedisSentinelManager getShardJedisSentinelManager() {
		return shardJedisSentinelManager;
	}

	public void setShardJedisSentinelManager(
			ShardJedisSentinelManager shardJedisSentinelManager) {
		this.shardJedisSentinelManager = shardJedisSentinelManager;
	}

	public void process() {
		XStream stream = XStreamFactory.getXStream(JEDIS_SHARD_SENTINEL_XSTREAM_NAME);
		
	}

	@Override
	protected boolean checkMatch(FileObject fileObject) {
		return fileObject.getFileName().endsWith(JEDIS_SHARD_SENTINEL_CONFIG_EXT_NAME);
	}

}
