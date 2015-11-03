package org.tinygroup.redis.test;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisSentinelPool;

public class JedisSentinelPoolTestFailOver {
	public static void main(String[] args) {
		Map<String,String> map = new HashMap<String,String>();
		String sentinel = "192.168.51.29:33333";
		map.put(sentinel, sentinel);
		JedisSentinelPool pool = new JedisSentinelPool("master1", map.keySet());
		Jedis jedis = pool.getResource();
		jedis.set("a", "aaaaaaaaaaaaaaaaaaa");
		String host = "192.168.51.29";
		JedisPool jPool = new JedisPool(host, 11111);
		System.out.println(jPool.getResource().get("a"));
		Assert.assertEquals("aaaaaaaaaaaaaaaaaaa", jPool.getResource().get("a"));
		
	}
}
