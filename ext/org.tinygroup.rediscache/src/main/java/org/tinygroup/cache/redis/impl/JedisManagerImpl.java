package org.tinygroup.cache.redis.impl;

import java.util.HashMap;
import java.util.Map;

import org.tinygroup.cache.redis.JedisManager;
import org.tinygroup.cache.redis.config.JedisConfig;
import org.tinygroup.cache.redis.config.JedisConfigs;

import redis.clients.jedis.JedisPoolConfig;

public class JedisManagerImpl implements JedisManager{

	private Map<String,JedisConfig> jedisConfigMap=new HashMap<String,JedisConfig>();
	
	public void addJedisConfigs(JedisConfigs configs) {
		for(JedisConfig config:configs.getJedisConfigList()){
			addJedisConfig(config);
		}
	}

	public void removeJedisConfigs(JedisConfigs configs) {
		for(JedisConfig config:configs.getJedisConfigList()){
			removeJedisConfig(config);
		}
	}

	public void addJedisConfig(JedisConfig config) {
		jedisConfigMap.put(config.getId(), config);
	}

	public void removeJedisConfig(JedisConfig config) {
		jedisConfigMap.remove(config.getId());
	}

	public JedisConfig getJedisConfig(String id) {
		return id==null?null:jedisConfigMap.get(id);
	}

	public JedisPoolConfig getJedisPoolConfig(String id) {
		return new JedisPoolConfig();
	}

}
