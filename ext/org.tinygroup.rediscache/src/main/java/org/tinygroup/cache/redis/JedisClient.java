package org.tinygroup.cache.redis;

import org.tinygroup.commons.tools.StringUtil;
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
		jedisConfig = redisCacheManager.getJedisManager()
				.getJedisConfig(region);
	}

	public JedisConfig getJedisConfig() {
		return jedisConfig;
	}

	public void setJedisConfig(JedisConfig jedisConfig) {
		this.jedisConfig = jedisConfig;
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

	private boolean getSafeBooleanValue(String value, boolean defaultValue) {
		if (StringUtil.isBlank(value)) {
			return defaultValue;
		} else {
			return Boolean.parseBoolean(value);
		}
	}

	private String getSafeStringValue(String value, String defaultValue) {
		if (StringUtil.isBlank(value)) {
			return defaultValue;
		} else {
			return value;
		}
	}

	private int getSafeIntValue(String value, int defaultValue) {
		if (StringUtil.isBlank(value)) {
			return defaultValue;
		} else {
			return Integer.parseInt(value);
		}
	}

	private long getSafeLongValue(String value, long defaultValue) {
		if (StringUtil.isBlank(value)) {
			return defaultValue;
		} else {
			return Long.parseLong(value);
		}
	}

	public String getCharset() {
		return jedisConfig.getCharset() == null ? "utf-8" : jedisConfig
				.getCharset();
	}

	public JedisPool getJedisPool() {
		return redisCacheManager.getJedisManager().getJedisPool(region);
	}
}
