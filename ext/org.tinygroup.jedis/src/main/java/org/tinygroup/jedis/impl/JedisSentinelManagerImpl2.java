package org.tinygroup.jedis.impl;

import java.util.HashMap;
import java.util.Map;

import org.tinygroup.jedis.JedisSentinelManager;
import org.tinygroup.jedis.config.JedisSentinelConfig;
import org.tinygroup.jedis.config.JedisSentinelConfigs;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisSentinelPool;

public class JedisSentinelManagerImpl2 implements JedisSentinelManager {

	private Map<String, JedisSentinelConfig> jedisSentinelConfigMap = new HashMap<String, JedisSentinelConfig>();
	private Map<String, JedisSentinelClient2> JedisSentinelClient2Map = new HashMap<String, JedisSentinelClient2>();

	public void addJedisSentinelConfigs(JedisSentinelConfigs configs) {
		for (JedisSentinelConfig config : configs.getJedisSentinelConfigsList()) {
			addJedisSentinelConfig(config);
		}
	}

	public void addJedisSentinelConfig(JedisSentinelConfig config) {
		String masterName = config.getMasterName();
		jedisSentinelConfigMap.put(masterName, config);

	}

	public void removeJedisSentinelConfigs(JedisSentinelConfigs configs) {
		for (JedisSentinelConfig config : configs.getJedisSentinelConfigsList()) {
			removeJedisSentinelConfig(config);
		}
	}

	public void removeJedisSentinelConfig(JedisSentinelConfig config) {
		String masterName = config.getMasterName();
		jedisSentinelConfigMap.remove(masterName);
		removeJedisSentinelPool(masterName, true);

	}

	public JedisSentinelConfig getJedisSentinelConfig(String masterName) {
		return jedisSentinelConfigMap.get(masterName);

	}

	public JedisSentinelPool removeJedisSentinelPool(String masterName,
			boolean destory) {

		return null;
	}

	public JedisSentinelPool getJedisSentinelPool(String masterName) {
		return getJedisSentinelClient2(masterName).getJedisSentinelPool();
	}

	public JedisSentinelClient2 getJedisSentinelClient2(String masterName) {
		if (!JedisSentinelClient2Map.containsKey(masterName)) {
			JedisSentinelClient2Map.put(masterName,
					createJedisSentinelClient2(masterName));

		}
		return JedisSentinelClient2Map.get(masterName);
	}

	private JedisSentinelClient2 createJedisSentinelClient2(String masterName) {
		if (!jedisSentinelConfigMap.containsKey(masterName)) {
			throw new RuntimeException("未找到" + masterName
					+ "对应的JedisSentinel配置");
		}
		return new JedisSentinelClient2(jedisSentinelConfigMap.get(masterName));

	}

	public Jedis getWriteJedis(String masterName) {
		return getJedisSentinelClient2(masterName).getWriteJedis();
	}

	public Jedis getReadJedis(String masterName) {
		return getJedisSentinelClient2(masterName).getReadJedis();
	}

	public JedisPool getReadJedisPool(String masterName) {
		return getJedisSentinelClient2(masterName).getReadJedisPool();
	}

	
}
