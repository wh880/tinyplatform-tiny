package org.tinygroup.jedis.util;

import java.util.ArrayList;
import java.util.List;

import org.tinygroup.beancontainer.BeanContainer;
import org.tinygroup.beancontainer.BeanContainerFactory;
import org.tinygroup.commons.tools.StringUtil;
import org.tinygroup.jedis.config.JedisConfig;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.Protocol;
import redis.clients.jedis.exceptions.JedisConnectionException;

public class JedisUtil {
	private JedisUtil() {
	}

	static int i = 0;
	
	public static void plusForTest(){
		i++;
	}
	public static int getForTest(){
		return i;
	}
	public static JedisPool createJedisPool(JedisConfig jedisConfig,
			ClassLoader loader) {
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
		JedisPool jedisPool = new JedisPool(getJedisPoolConfig(
				jedisConfig.getPoolConfig(), loader), host, port, timeout,
				jedisConfig.getPassword(), database,
				jedisConfig.getClientName());

		return jedisPool;
	}

	public static JedisPoolConfig getJedisPoolConfig(String poolConfig,
			ClassLoader loader) {
		if (StringUtil.isBlank(poolConfig)) {
			return new JedisPoolConfig();
		}
		BeanContainer<?> container = BeanContainerFactory
				.getBeanContainer(loader);
		JedisPoolConfig jedisPoolConfig = (JedisPoolConfig) container
				.getBean(poolConfig);
		return jedisPoolConfig;
	}

	public static JedisShardInfo createJedisShardInfo(JedisConfig jedisConfig) {
		JedisShardInfo info = new JedisShardInfo(jedisConfig.getHost(),
				jedisConfig.getPort(), jedisConfig.getTimeout());
		info.setPassword(jedisConfig.getPassword());
		return info;
	}
	
	public static String toSimpleString(String host,int port) {
		return host + ":" + port;
	}
	
	public static Jedis choose(List<Jedis> list) {
		if (list.size() == 0) {
			return null;
		}
		int totalWeight = list.size();
		int random = (int) (Math.random() * totalWeight);
		Jedis j = list.get(random);
		try {
			j.connect();
			return j;
		} catch (JedisConnectionException ex) {
			List<Jedis> newList = newList(list, j);
			return choose(newList);
		}
	}

	private static List<Jedis> newList(List<Jedis> jedises, Jedis jedis) {
		List<Jedis> list = new ArrayList<Jedis>();
		for (Jedis j : jedises) {
			if (j == jedis) {
				continue;
			}
			list.add(j);
		}
		return list;
	}
}
