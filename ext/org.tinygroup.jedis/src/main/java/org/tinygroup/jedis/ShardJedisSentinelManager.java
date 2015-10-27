package org.tinygroup.jedis;

import org.tinygroup.jedis.config.ShardJedisSentinelConfigs;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;

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
	 ShardedJedis getWriteShardedJedis();
	 
	 Jedis getWriteJedis(String key);
	
	/**
	 * 获取jedis读连接
	 * 
	 * @param masterName
	 *            masterName
	 * @return
	 */
	 ShardedJedis getReadShardedJedis();
	 
	 
	 Jedis getReadJedis(String key);
	 
	 void destroy();
	
	

}
