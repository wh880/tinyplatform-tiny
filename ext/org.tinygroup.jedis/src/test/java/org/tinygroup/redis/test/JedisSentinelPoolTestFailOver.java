package org.tinygroup.redis.test;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.Protocol;

public class JedisSentinelPoolTestFailOver {
	public static void main(String[] args) {
		Map<String,String> map = new HashMap<String,String>();
		String sentinel = "192.168.51.29:33333";
		map.put(sentinel, sentinel);
		JedisSentinelPool pool = new JedisSentinelPool("master1", map.keySet() ,IJedisConstant.PASSWOPD);
		Jedis jedis = pool.getResource();
		jedis.set("a", "aaaaaaaaaaaaaaaaaaa");
		pool.destroy();
		String host = "192.168.51.29";
		JedisPool jPool = new JedisPool(new GenericObjectPoolConfig(),host, 11111 ,Protocol.DEFAULT_TIMEOUT ,IJedisConstant.PASSWOPD);
		System.out.println(jPool.getResource().get("a"));
		Assert.assertEquals("aaaaaaaaaaaaaaaaaaa", jPool.getResource().get("a"));
		jPool.destroy();
	}
}
