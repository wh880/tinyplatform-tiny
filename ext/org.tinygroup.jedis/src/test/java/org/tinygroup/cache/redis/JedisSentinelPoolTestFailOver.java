package org.tinygroup.cache.redis;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisSentinelPool;

public class JedisSentinelPoolTestFailOver {
	public static void main(String[] args) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("192.168.51.29:33333", "192.168.51.29:33333");
		JedisSentinelPool pool = new JedisSentinelPool("master1", map.keySet());
		Jedis jedis = pool.getResource();
		jedis.set("a", "aaaaaaaaaaaaaaaaaaa");
		JedisPool jPool = new JedisPool("192.168.51.29", 11111);
		System.out.println(jPool.getResource().get("a"));
		Assert.assertEquals("aaaaaaaaaaaaaaaaaaa", jPool.getResource().get("a"));
	}
}
