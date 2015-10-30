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

	/**
	 * 获取jedis写连接
	 * 
	 * @param masterName
	 *            masterName
	 * @return
	 */
	TinyShardJedis getWriteShardedJedis();
	void init(GenericObjectPoolConfig poolConfig);
	/**
	 * 获取jedis读连接
	 * 
	 * @param masterName
	 *            masterName
	 * @return
	 */
	TinyShardJedis getReadShardedJedis();
	
	TinyShardJedis getShardedJedis();

	void destroy();

	void returnResourceObject(TinyShardJedis resource);

	void returnResource(TinyShardJedis resource);

	void returnBrokenResource(TinyShardJedis resource);

}
