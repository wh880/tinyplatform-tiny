package org.tinygroup.redis.test.shard;

import java.util.ArrayList;

import junit.framework.Assert;

import org.tinygroup.beancontainer.BeanContainerFactory;
import org.tinygroup.jedis.ShardJedisSentinelManager;
import org.tinygroup.jedis.shard.TinyShardJedis;
import org.tinygroup.redis.test.RedisTest;
import org.tinygroup.tinyrunner.Runner;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;

public class ShardJedisSentinelManagerImplTest {
	public static void main(String[] args) {
		Runner.init("application.xml", new ArrayList<String>());
		JedisPoolConfig jedisConfig = BeanContainerFactory.getBeanContainer(
				ShardJedisSentinelManagerImplTest.class.getClassLoader()).getBean("jedisConfig");
		ShardJedisSentinelManager manager = BeanContainerFactory
				.getBeanContainer(RedisTest.class.getClassLoader()).getBean(
						"shardJedisSentinelManager");
		manager.init(jedisConfig);
		for(int i = 0 ; i < 1000 ; i ++){
			// ===================
			test(manager, "ShardJedisSentinelManagerImplTest"+i+i+i);
			// ===================
		}
		for(int i = 0 ; i < 1000 ; i ++){
			// ===================
			testReadAndWrite(manager, "WriteAndRead"+i+i+i);
			// ===================
		}
		
		manager.destroy();

	}

	private static void test(ShardJedisSentinelManager manager, String key) {
		System.out.println("===================");
		//直接通过TinyShardJedis操作的话，实际是对主从数据库中的主服务器进行操作(即对写服务器进行操作)
		TinyShardJedis jedis = manager.getShardedJedis();
		TinyShardJedis jedis2 = manager.getShardedJedis();
		jedis.del(key);
		jedis.set(key, key);
		System.out.println(jedis.get(key));
		System.out.println("manager.getShardedJedis():" + jedis2);
		Assert.assertEquals(key, jedis.get(key));
		manager.returnResource(jedis);
		manager.returnResource(jedis2);
		System.out.println("===================");
	}

	private static void testReadAndWrite(ShardJedisSentinelManager manager,
			String key) {
		System.out.println("===================");
		TinyShardJedis shardedJedis = manager.getShardedJedis();
		//shardedJedis.getShard获得的链接操作数据和直接通过TinyShardJedis是一样
		Jedis write = shardedJedis.getShard(key);
		//只有这个种情况，是对读服务器进行服务器进行操作
		Jedis read = shardedJedis.getReadShard(key);
		System.out.println(write);
		System.out.println(read);
		Assert.assertNotSame(write, read);
		manager.returnResource(shardedJedis);
		System.out.println("===================");
	}
}
