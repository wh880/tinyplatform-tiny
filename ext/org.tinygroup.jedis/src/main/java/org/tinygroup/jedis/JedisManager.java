package org.tinygroup.jedis;

import org.tinygroup.jedis.config.JedisConfig;
import org.tinygroup.jedis.config.JedisConfigs;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * JedisManager
 * 
 * @author yancheng11334
 * 
 */
public interface JedisManager {

	/**
	 * 添加jedis配置
	 * 
	 * @param configs
	 */
	void addJedisConfigs(JedisConfigs configs);

	/**
	 * 添加jedis配置
	 * 
	 * @param config
	 */
	void addJedisConfig(JedisConfig config);

	/**
	 * 移除jedis配置
	 * 
	 * @param configs
	 */
	void removeJedisConfigs(JedisConfigs configs);

	/**
	 * 移除jedis配置,并移除相关的连接池
	 * 
	 * @param config
	 */
	void removeJedisConfig(JedisConfig config);

	/**
	 * 获取jedis配置
	 * 
	 * @param jedisId
	 *            jedis配置id
	 * @return
	 */
	JedisConfig getJedisConfig(String jedisId);

	/**
	 * 获取jedis连接池配置
	 * 
	 * @param jedisId
	 *            jedis配置id
	 * @return
	 */
	JedisPoolConfig getJedisPoolConfig(String jedisId);

	/**
	 * 获取jedis连接池配置
	 * 
	 * @param jedisId
	 *            jedis配置id
	 * @return
	 */
	JedisPool getJedisPool(String jedisId);

	/**
	 * 移除jedis连接池
	 * 
	 * @param jedisId
	 *            jedis配置id
	 * @return
	 */
	JedisPool removeJedisPool(String jedisId);

	/**
	 * 移除jedis连接池
	 * 
	 * @param pool
	 *            jedis连接池
	 * @return
	 */
	String removeJedisPool(JedisPool pool);

}
