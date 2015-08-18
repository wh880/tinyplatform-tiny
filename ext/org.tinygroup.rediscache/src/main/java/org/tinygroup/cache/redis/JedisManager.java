package org.tinygroup.cache.redis;

import org.tinygroup.cache.redis.config.JedisConfig;
import org.tinygroup.cache.redis.config.JedisConfigs;

import redis.clients.jedis.JedisPoolConfig;

/**
 * JedisManager
 * @author yancheng11334
 *
 */
public interface JedisManager {

	public void addJedisConfigs(JedisConfigs configs);
	
	public void removeJedisConfigs(JedisConfigs configs);
	
    public void addJedisConfig(JedisConfig config);
	
	public void removeJedisConfig(JedisConfig config);
	
	public JedisConfig getJedisConfig(String id);
	
	public JedisPoolConfig getJedisPoolConfig(String id);
	
}
