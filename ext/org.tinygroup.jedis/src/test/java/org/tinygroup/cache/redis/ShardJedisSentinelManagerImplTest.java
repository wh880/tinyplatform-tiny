package org.tinygroup.cache.redis;

import java.util.ArrayList;

import junit.framework.Assert;

import org.tinygroup.beancontainer.BeanContainerFactory;
import org.tinygroup.jedis.impl.ShardJedisSentinelManagerImpl;
import org.tinygroup.tinyrunner.Runner;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.ShardedJedis;

public class ShardJedisSentinelManagerImplTest {
	public static void main(String[] args) {
		Runner.init("application.xml", new ArrayList<String>());
		JedisPoolConfig jedisConfig = BeanContainerFactory.getBeanContainer(RedisTest.class.getClassLoader())
				.getBean("jedisConfig");
		ShardJedisSentinelManagerImpl manager = BeanContainerFactory.getBeanContainer(RedisTest.class.getClassLoader())
				.getBean("shardJedisSentinelManager");
		manager.init(jedisConfig);
		//===================
		ShardedJedis jedis = manager.getReadShardedJedis();
		jedis.del("ShardJedisSentinelManagerImplTest");
		jedis.set("ShardJedisSentinelManagerImplTest","ShardJedisSentinelManagerImplTest");
		System.out.println(jedis.get("ShardJedisSentinelManagerImplTest"));
		Assert.assertEquals("ShardJedisSentinelManagerImplTest", jedis.get("ShardJedisSentinelManagerImplTest"));
		//===================
		ShardedJedis jedis2 = manager.getWriteShardedJedis();
		jedis.del("ShardJedisSentinelManagerImplTest2");
		jedis.set("ShardJedisSentinelManagerImplTest2","ShardJedisSentinelManagerImplTest2");
		System.out.println(jedis2.get("ShardJedisSentinelManagerImplTest2"));
		Assert.assertEquals("ShardJedisSentinelManagerImplTest2", jedis2.get("ShardJedisSentinelManagerImplTest2"));
		//===================
		Jedis write = manager.getWriteJedis("WriteAndRead");
		Jedis read = manager.getReadJedis("WriteAndRead");
		System.out.println(write);
		System.out.println(read);
		Assert.assertNotSame(write, read);
		//===================
		manager.destroy();
		
	}
}
