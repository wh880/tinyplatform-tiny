package org.tinygroup.jedis.impl;

import java.util.HashMap;
import java.util.Map;

import org.tinygroup.beancontainer.BeanContainer;
import org.tinygroup.beancontainer.BeanContainerFactory;
import org.tinygroup.commons.tools.StringUtil;
import org.tinygroup.jedis.JedisManager;
import org.tinygroup.jedis.config.JedisConfig;
import org.tinygroup.jedis.config.JedisConfigs;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

public class JedisManagerImpl implements JedisManager {

	private Map<String, JedisConfig> jedisConfigMap = new HashMap<String, JedisConfig>();
	private Map<String, JedisPool> jedisPoolMap = new HashMap<String, JedisPool>();

	public void addJedisConfigs(JedisConfigs configs) {
		for (JedisConfig config : configs.getJedisConfigList()) {
			addJedisConfig(config);
		}
	}

	public void removeJedisConfigs(JedisConfigs configs) {
		for (JedisConfig config : configs.getJedisConfigList()) {
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
		return id == null ? null : jedisConfigMap.get(id);
	}

	public JedisPoolConfig getJedisPoolConfig(String id) {
		JedisConfig jedisConfig = getJedisConfig(id);
		return getJedisPoolConfig(jedisConfig);
	}

	public JedisPool getJedisPool(String jedisId) {
		if (jedisPoolMap.containsKey(jedisId)) {
			return jedisPoolMap.get(jedisId);
		}
		JedisPool pool = createJedisPool(jedisId);
		jedisPoolMap.put(jedisId, pool);
		return pool;
	}

	public JedisPool removeJedisPool(String jedisId) {
		return jedisPoolMap.remove(jedisPoolMap);
	}

	public String removeJedisPool(JedisPool pool) {
		String jedisId = null;
		for (String id : jedisPoolMap.keySet()) {
			if (pool == jedisPoolMap.get(jedisId)) {
				jedisId = id;
				break;
			}
		}
		if (jedisId != null) {
			jedisPoolMap.remove(jedisId);
		}
		return jedisId;
	}

	private JedisPool createJedisPool(String jedisId) {

		JedisConfig jedisConfig = getJedisConfig(jedisId);

		if (jedisConfig == null) {
			throw new NullPointerException(String.format("根据Id:[%s]没有找到匹配的JedisConfig配置对象", jedisId));
		}

		// 设置默认参数
		String host = StringUtil.isBlank(jedisConfig.getHost()) ? Protocol.DEFAULT_HOST : jedisConfig.getHost();
		int port = jedisConfig.getPort() <= 0 ? Protocol.DEFAULT_PORT : jedisConfig.getPort();
		int timeout = jedisConfig.getTimeout() < 0 ? Protocol.DEFAULT_TIMEOUT : jedisConfig.getTimeout();
		int database = jedisConfig.getDatabase() < 0 ? Protocol.DEFAULT_DATABASE : jedisConfig.getDatabase();
		// 实例化jedis连接池
		JedisPool jedisPool = new JedisPool(getJedisPoolConfig(jedisConfig), host, port, timeout,
				jedisConfig.getPassword(), database, jedisConfig.getClientName());

		return jedisPool;

	}

	private JedisPoolConfig getJedisPoolConfig(JedisConfig jedisConfig) {
		String poolConfig = jedisConfig.getPoolConfig();
		if (StringUtil.isBlank(poolConfig)) {
			return new JedisPoolConfig();
		}
		BeanContainer<?> container = BeanContainerFactory.getBeanContainer(this.getClass().getClassLoader());
		JedisPoolConfig jedisPoolConfig = (JedisPoolConfig) container.getBean(poolConfig);
		return jedisPoolConfig;
	}

}
