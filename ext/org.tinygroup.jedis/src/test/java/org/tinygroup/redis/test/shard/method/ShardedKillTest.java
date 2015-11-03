package org.tinygroup.redis.test.shard.method;

import java.util.ArrayList;

import junit.framework.Assert;

import org.tinygroup.beancontainer.BeanContainerFactory;
import org.tinygroup.jedis.ShardJedisSentinelManager;
import org.tinygroup.jedis.shard.TinyShardJedis;
import org.tinygroup.redis.test.RedisTest;
import org.tinygroup.redis.test.shard.ShardJedisSentinelManagerImplTest;
import org.tinygroup.tinyrunner.Runner;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;

public class ShardedKillTest {
	public static void main(String[] args) {
		Runner.init("application.xml", new ArrayList<String>());
		JedisPoolConfig jedisConfig = BeanContainerFactory.getBeanContainer(
				ShardJedisSentinelManagerImplTest.class.getClassLoader())
				.getBean("jedisConfig");
		ShardJedisSentinelManager manager = BeanContainerFactory
				.getBeanContainer(RedisTest.class.getClassLoader()).getBean(
						"shardJedisSentinelManager");
		manager.init(jedisConfig);
		TinyShardJedis jedis = manager.getShardedJedis();
		System.out.println("读从:");
		jedis.del("aaa");
		Assert.assertTrue(!jedis.exists("aaa"));
		jedis.set("aaa", "aaa");

		Jedis writeShard = jedis.getShard("aaa");
		Jedis readShard = jedis.getReadShard("aaa");
		readShard.get("a");
		System.out.println("写从:");
		try {
			readShard.set("a", "b");
			System.out.println("写从未发生异常:错误！");
			Assert.assertTrue(false);
		} catch (Exception e) {
			System.out.println("写从发生异常:正确");
			Assert.assertTrue(true);
		}
		manager.returnResource(jedis);
		try {
			Thread.sleep(15000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		TinyShardJedis jedis2 = manager.getShardedJedis();
		jedis2.set("aaa", "aaa");
		manager.destroy();

	}
}
