package org.tinygroup.jedis;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.tinygroup.jedis.config.ShardJedisSentinelConfigs;
import org.tinygroup.jedis.shard.TinyShardJedis;

/**
 * JedisManager
 * 
 * @author yancheng11334
 * 
 */
public interface ShardJedisSentinelManager {

	/**
	 * 添加JedisSentinel配置
	 * 
	 * @param configs
	 */
	void addJedisSentinelConfigs(ShardJedisSentinelConfigs configs);

	/**
	 * 移除JedisSentinel配置
	 * 
	 * @param configs
	 */
	void removeJedisSentinelConfigs(ShardJedisSentinelConfigs configs);

	void init(GenericObjectPoolConfig poolConfig);
	
	TinyShardJedis getShardedJedis();

	void destroy();

	void returnResourceObject(TinyShardJedis resource);

	void returnResource(TinyShardJedis resource);

	void returnBrokenResource(TinyShardJedis resource);

}
