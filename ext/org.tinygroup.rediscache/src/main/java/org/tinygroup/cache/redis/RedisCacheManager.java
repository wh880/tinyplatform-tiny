package org.tinygroup.cache.redis;

import org.tinygroup.cache.AbstractCacheManager;
import org.tinygroup.cache.Cache;

public class RedisCacheManager extends AbstractCacheManager{

	private JedisManager jedisManager;
	
	public JedisManager getJedisManager() {
		return jedisManager;
	}

	public void setJedisManager(JedisManager jedisManager) {
		this.jedisManager = jedisManager;
	}

	public void shutDown() {
		removeCaches();
		cacheMap.clear();
	}

	protected Cache newCache(String region) {
		RedisCache cache = new RedisCache();
		cache.setCacheManager(this);
		cache.init(region);
		return cache;
	}

	protected void internalRemoveCache(Cache cache) {
		cache.clear();
	}

}
