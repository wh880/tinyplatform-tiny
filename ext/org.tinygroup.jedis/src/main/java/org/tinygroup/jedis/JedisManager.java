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

	public void addJedisConfigs(JedisConfigs configs);

	public void removeJedisConfigs(JedisConfigs configs);

	public void addJedisConfig(JedisConfig config);

	public void removeJedisConfig(JedisConfig config);

	public JedisConfig getJedisConfig(String id);

	public JedisPoolConfig getJedisPoolConfig(String id);

	public JedisPool getJedisPool(String jedisId);

	JedisPool removeJedisPool(String jedisId);

	String removeJedisPool(JedisPool pool);
}
