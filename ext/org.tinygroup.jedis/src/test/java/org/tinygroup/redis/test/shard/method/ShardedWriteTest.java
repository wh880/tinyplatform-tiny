package org.tinygroup.redis.test.shard.method;

import java.util.ArrayList;

import org.tinygroup.beancontainer.BeanContainerFactory;
import org.tinygroup.jedis.ShardJedisSentinelManager;
import org.tinygroup.jedis.shard.TinyShardJedis;
import org.tinygroup.redis.test.RedisTest;
import org.tinygroup.redis.test.shard.ShardJedisSentinelManagerImplTest;
import org.tinygroup.tinyrunner.Runner;

import redis.clients.jedis.JedisPoolConfig;

public class ShardedWriteTest {
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
		jedis.getReadShard("aaa").get("a");
		System.out.println("写从:");
		try {
			jedis.getReadShard("aaa").set("a", "b");
			System.out.println("写从未发生异常:错误！");
		} catch (Exception e) {
			System.out.println("写从发生异常:正确");
		}
		manager.returnResource(jedis);
		manager.destroy();
		
	}
}
