package org.tinygroup.cache.redis;

import org.tinygroup.cache.exception.CacheException;
import org.tinygroup.cache.redis.config.JedisConfig;
import org.tinygroup.cache.redis.config.PoolConfig;
import org.tinygroup.commons.tools.StringUtil;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

public class JedisClient {

	private JedisConfig jedisConfig;

	private RedisCacheManager redisCacheManager;

	private String region;

	public JedisClient(String region) {
		this.region = region;
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

	public JedisPool createJedisPool() {
		try {
			jedisConfig = redisCacheManager.getJedisManager().getJedisConfig(
					region);

			if (jedisConfig == null) {
				throw new NullPointerException(String.format(
						"根据region:[%s]没有找到匹配的JedisConfig配置对象", region));
			}

			// 设置默认参数
			String host = StringUtil.isBlank(jedisConfig.getHost()) ? Protocol.DEFAULT_HOST
					: jedisConfig.getHost();
			int port = jedisConfig.getPort() <= 0 ? Protocol.DEFAULT_PORT
					: jedisConfig.getPort();
			int timeout = jedisConfig.getTimeout() < 0 ? Protocol.DEFAULT_TIMEOUT
					: jedisConfig.getTimeout();
			int database = jedisConfig.getDatabase() < 0 ? Protocol.DEFAULT_DATABASE
					: jedisConfig.getDatabase();

			// 实例化jedis连接池
			JedisPool jedisPool = new JedisPool(
					getJedisPoolConfig(jedisConfig), host, port, timeout,
					jedisConfig.getPassword(), database,
					jedisConfig.getClientName());

			return jedisPool;
		} catch (Exception e) {
			throw new CacheException(e);
		}

	}

	public JedisPoolConfig getJedisPoolConfig(JedisConfig jedisConfig) {
		JedisPoolConfig jedisPoolConfig = redisCacheManager.getJedisManager()
				.getJedisPoolConfig(jedisConfig.getId());
		PoolConfig poolConfig = jedisConfig.getPoolConfig();
		if (poolConfig != null) {
			jedisPoolConfig.setBlockWhenExhausted(getSafeBooleanValue(
					poolConfig.getBlockWhenExhausted(),
					jedisPoolConfig.getBlockWhenExhausted()));
			jedisPoolConfig.setEvictionPolicyClassName(getSafeStringValue(
					poolConfig.getEvictionPolicyClassName(),
					jedisPoolConfig.getEvictionPolicyClassName()));
			jedisPoolConfig.setLifo(getSafeBooleanValue(poolConfig.getLifo(),
					jedisPoolConfig.getLifo()));
			jedisPoolConfig.setMaxIdle(getSafeIntValue(poolConfig.getMaxIdle(),
					jedisPoolConfig.getMaxIdle()));
			jedisPoolConfig.setMaxTotal(getSafeIntValue(
					poolConfig.getMaxTotal(), jedisPoolConfig.getMaxTotal()));
			jedisPoolConfig.setMaxWaitMillis(getSafeLongValue(
					poolConfig.getMaxWaitMillis(),
					jedisPoolConfig.getMaxWaitMillis()));
			jedisPoolConfig.setMinEvictableIdleTimeMillis(getSafeLongValue(
					poolConfig.getMinEvictableIdleTimeMillis(),
					jedisPoolConfig.getMinEvictableIdleTimeMillis()));
			jedisPoolConfig.setMinIdle(getSafeIntValue(poolConfig.getMinIdle(),
					jedisPoolConfig.getMinIdle()));
			jedisPoolConfig.setNumTestsPerEvictionRun(getSafeIntValue(
					poolConfig.getNumTestsPerEvictionRun(),
					jedisPoolConfig.getNumTestsPerEvictionRun()));
			jedisPoolConfig.setSoftMinEvictableIdleTimeMillis(getSafeLongValue(
					poolConfig.getSoftMinEvictableIdleTimeMillis(),
					jedisPoolConfig.getSoftMinEvictableIdleTimeMillis()));
			jedisPoolConfig.setTestOnBorrow(getSafeBooleanValue(
					poolConfig.getTestOnBorrow(),
					jedisPoolConfig.getTestOnBorrow()));
			jedisPoolConfig.setTestOnCreate(getSafeBooleanValue(
					poolConfig.getTestOnCreate(),
					jedisPoolConfig.getTestOnCreate()));
			jedisPoolConfig.setTestOnReturn(getSafeBooleanValue(
					poolConfig.getTestOnReturn(),
					jedisPoolConfig.getTestOnReturn()));
			jedisPoolConfig.setTestWhileIdle(getSafeBooleanValue(
					poolConfig.getTestWhileIdle(),
					jedisPoolConfig.getTestWhileIdle()));
			jedisPoolConfig.setTimeBetweenEvictionRunsMillis(getSafeLongValue(
					poolConfig.getTimeBetweenEvictionRunsMillis(),
					jedisPoolConfig.getTimeBetweenEvictionRunsMillis()));
		}
		return jedisPoolConfig;
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
}
