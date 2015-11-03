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
		
		try {
			Thread.sleep(15000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		TinyShardJedis jedis2 = manager.getShardedJedis();
		jedis2.set("aaa", "aaa");
		// manager.destroy();

	}
}
