package org.tinygroup.jedis;

import org.tinygroup.jedis.config.JedisSentinelConfig;
import org.tinygroup.jedis.config.JedisSentinelConfigs;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisSentinelPool;

/**
 * JedisManager
 * 
 * @author yancheng11334
 * 
 */
public interface JedisSentinelManager {

	/**
	 * 添加JedisSentinel配置
	 * 
	 * @param configs
	 */
	void addJedisSentinelConfigs(JedisSentinelConfigs configs);

	/**
	 * 添加JedisSentinel配置
	 * 
	 * @param config
	 */
	 void addJedisSentinelConfig(JedisSentinelConfig config);

	/**
	 * 移除JedisSentinel配置
	 * 
	 * @param configs
	 */
	 void removeJedisSentinelConfigs(JedisSentinelConfigs configs);

	/**
	 * 移除JedisSentinel配置,并移除、销毁相关的连接池
	 * 
	 * @param config
	 */
	 void removeJedisSentinelConfig(JedisSentinelConfig config);

	

	/**
	 *  移除JedisSentinel连接池
	 * @param masterName
	 * @param destory 是否销毁，true为销毁
	 * @return
	 */
	 JedisSentinelPool removeJedisSentinelPool(String masterName,boolean destory);
	
	/**
	 * 获取JedisSentinel连接池，也是写连接池
	 * 
	 * @param masterName
	 *            masterName
	 * @return
	 */
	 JedisSentinelPool getJedisSentinelPool(String masterName);
	
	/**
	 * 获取jedis写连接
	 * 
	 * @param masterName
	 *            masterName
	 * @return
	 */
	 Jedis getWriteJedis(String masterName);
	
	/**
	 * 获取jedis读连接
	 * 
	 * @param masterName
	 *            masterName
	 * @return
	 */
	 Jedis getReadJedis(String masterName);
	
	/**
	 * 获取jedis读连接池 
	 * 
	 * @param masterName
	 *            masterName
	 * @return
	 */
	 JedisPool getReadJedisPool(String masterName);


}
