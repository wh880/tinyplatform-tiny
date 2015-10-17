package org.tinygroup.jedis.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.tinygroup.commons.tools.StringUtil;
import org.tinygroup.jedis.config.JedisConfig;
import org.tinygroup.jedis.config.JedisSentinelConfig;
import org.tinygroup.jedis.util.JedisUtil;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.util.Pool;

public class JedisSentinelClient {
	private List<JedisPool> jedisPools = new ArrayList<JedisPool>();
	private List<String> poolKeys = new ArrayList<String>();
	private JedisSentinelPool jedisSentinelPool;
	private String masterNamel;
	private static final Logger logger = LoggerFactory
			.getLogger(JedisSentinelClient.class);

	public JedisSentinelClient(JedisSentinelConfig config) {
		masterNamel = config.getMasterName();
		initJedisSentinelPool(config);
		initJedisPools(config);
	}

	private void initJedisPools(JedisSentinelConfig config) {
		List<JedisConfig> configs = config.getJedisConfigList();
		for (JedisConfig jedisConfig : configs) {
			if (!poolKeys.contains(jedisConfig.getId())) {
				JedisPool pool = JedisUtil.createJedisPool(jedisConfig, this
						.getClass().getClassLoader());
				jedisPools.add(pool);
			}

		}
	}

	private void initJedisSentinelPool(JedisSentinelConfig config) {
		String masterName = config.getMasterName();
		if (StringUtil.isBlank(masterName)) {
			throw new RuntimeException("JedisSentinelPool配置的masterName不可为空."
					+ config.toString());
		}
		String sentinesls = config.getSentinels();
		if (StringUtil.isBlank(sentinesls)) {
			throw new RuntimeException("JedisSentinelPool配置的sentinesls不可为空."
					+ config.toString());
		}
		Set<String> sentineslSet = getSentineslSet(sentinesls);
		int database = config.getDatabase();
		int timeout = config.getTimeout();
		String password = config.getPassword();
		String poolConfig = config.getPoolConfig();
		JedisPoolConfig jedisPoolCondig = JedisUtil.getJedisPoolConfig(
				poolConfig, this.getClass().getClassLoader());
		jedisSentinelPool = new JedisSentinelPool(masterName, sentineslSet,
				jedisPoolCondig, timeout, password, database);
	}

	private Set<String> getSentineslSet(String sentinesls) {
		String[] sentineslArray = sentinesls.split(",");
		Map<String, String> map = new HashMap<String, String>();
		for (String sentinesl : sentineslArray) {
			map.put(sentinesl, sentinesl);
		}
		return map.keySet();
	}

	public void destroy() {
		destroy(jedisSentinelPool);
		for (JedisPool pool : jedisPools) {
			destroy(pool);
		}
	}

	private void destroy(Pool<Jedis> pool) {
		try {
			pool.destroy();
		} catch (Exception e) {
			logger.errorMessage("销毁{}的连接池{}时出错", e, masterNamel,
					pool.toString());
		}

	}

	public JedisSentinelPool getJedisSentinelPool() {
		return jedisSentinelPool;
	}

	public Jedis getWriteJedis() {
		return getJedisSentinelPool().getResource();
	}

	public Jedis getReadJedis() {
		return getReadJedisPool().getResource();
	}

	public JedisPool getReadJedisPool() {
		// TODO:负载均衡
		return jedisPools.get(0);
	}

}
