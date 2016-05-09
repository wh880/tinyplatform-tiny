package org.tinygroup.cache.redis;

import org.tinygroup.jedis.config.JedisConfig;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisClient {

	private JedisConfig jedisConfig;

	private RedisCacheManager redisCacheManager;

	private String region;

	public JedisClient(String region, RedisCacheManager redisCacheManager) {
		this.region = region;
		this.redisCacheManager = redisCacheManager;	
	}
    
	public synchronized JedisConfig getJedisConfig() {
		//为了解决aopcache初始化优先于框架加载配置的顺序问题，将真正的初始化放到调用时触发
		if(jedisConfig==null){
		   jedisConfig = redisCacheManager.getJedisManager()
					.getJedisConfig(region);
		}
		return jedisConfig;
	}

	public RedisCacheManager getRedisCacheManager() {
		return redisCacheManager;
	}

	public void setRedisCacheManager(RedisCacheManager redisCacheManager) {
		this.redisCacheManager = redisCacheManager;
	}

	public JedisPoolConfig getJedisPoolConfig(JedisConfig jedisConfig) {
		return redisCacheManager.getJedisManager().getJedisPoolConfig(
				jedisConfig.getId());
	}

	public String getCharset() {
		return getJedisConfig().getCharset() == null ? "utf-8" : getJedisConfig()
				.getCharset();
	}

	public JedisPool getJedisPool() {
		return redisCacheManager.getJedisManager().getJedisPool(region);
	}
}
