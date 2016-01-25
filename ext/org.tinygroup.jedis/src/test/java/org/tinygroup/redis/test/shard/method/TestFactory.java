package org.tinygroup.redis.test.shard.method;

import java.util.ArrayList;

import junit.framework.Assert;

import org.tinygroup.jedis.ShardJedisSentinelManager;
import org.tinygroup.jedis.impl.ShardJedisSentinelManagerFactory;
import org.tinygroup.jedis.shard.TinyShardJedis;
import org.tinygroup.tinyrunner.Runner;

import redis.clients.jedis.exceptions.JedisConnectionException;

public class TestFactory {
	public static void main(String[] args) {
		Runner.init("application.xml", new ArrayList<String>());
		ShardJedisSentinelManager manager = ShardJedisSentinelManagerFactory
				.getManager();
		ShardJedisSentinelManagerFactory.addResource("/jedis2.jedisshardsentrinelconfig.xml");
		ShardJedisSentinelManager manager2 = ShardJedisSentinelManagerFactory
				.getManager("aaa");
		TinyShardJedis shardedJedis = manager.getShardedJedis();
		shardedJedis.set("a", "b");
		Assert.assertEquals("b", shardedJedis.get("a"));
		manager.returnBrokenResource(shardedJedis);
		manager.destroy();
		
		//未授权的
		try {
			TinyShardJedis shardedJedis2 = manager2.getShardedJedis();
			shardedJedis2.set("a", "b1");
			Assert.assertEquals("b1", shardedJedis2.get("a"));
			manager2.returnBrokenResource(shardedJedis2);
			Assert.fail();
		} catch (JedisConnectionException e) {
		}
		manager2.destroy();
	}
}
