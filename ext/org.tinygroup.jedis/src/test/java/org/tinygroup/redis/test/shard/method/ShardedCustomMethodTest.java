package org.tinygroup.redis.test.shard.method;

import java.util.ArrayList;
import java.util.Set;

import junit.framework.Assert;

import org.tinygroup.beancontainer.BeanContainerFactory;
import org.tinygroup.jedis.ShardJedisSentinelManager;
import org.tinygroup.jedis.shard.TinyShardJedis;
import org.tinygroup.redis.test.RedisTest;
import org.tinygroup.redis.test.shard.ShardJedisSentinelManagerImplTest;
import org.tinygroup.tinyrunner.Runner;

import redis.clients.jedis.JedisPoolConfig;

public class ShardedCustomMethodTest {
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
		//==============================
		jedis.flushAll();
		//==============================
		for (int i = 0; i < 20; i++) {
			jedis.set("aaa" + i, "aaa" + i);
		}
		jedis.set("b1", "b1");
		Set<String> value = jedis.keys("aaa?");
		Set<String> value2 = jedis.keys("aaa*");
		Assert.assertEquals(10, value.size());
		Assert.assertEquals(20, value2.size());
		//==============================
		int num = jedis.deleteMatchKey("aaa?");
		int num1 = jedis.deleteMatchKey("aaa*");
		Assert.assertEquals(10, num);
		Assert.assertEquals(10, num1);
		//==============================
		int num2 = jedis.deleteMatchKey("b?");
		Assert.assertEquals(1, num2);
		jedis.flushAll();
		num2 = jedis.deleteMatchKey("b?");
		Assert.assertEquals(0, num2);
		//==============================
		manager.destroy();
	}
}
