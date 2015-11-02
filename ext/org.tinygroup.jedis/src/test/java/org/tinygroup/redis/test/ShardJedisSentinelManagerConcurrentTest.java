package org.tinygroup.redis.test;

import java.util.ArrayList;

import junit.framework.Assert;

import org.tinygroup.beancontainer.BeanContainerFactory;
import org.tinygroup.jedis.ShardJedisSentinelManager;
import org.tinygroup.jedis.shard.TinyShardJedis;
import org.tinygroup.tinyrunner.Runner;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;

public class ShardJedisSentinelManagerConcurrentTest {
	public static void main(String[] args) {
		Runner.init("application.xml", new ArrayList<String>());
		JedisPoolConfig jedisConfig = BeanContainerFactory.getBeanContainer(
				RedisTest.class.getClassLoader()).getBean("jedisConfig");
		ShardJedisSentinelManager manager = BeanContainerFactory
				.getBeanContainer(RedisTest.class.getClassLoader()).getBean(
						"shardJedisSentinelManager");
		manager.init(jedisConfig);
		for (int i = 0; i < 55; i++) {
			testReadAndWrite(manager, "WriteAndRead" + i + i + i);
		}
		System.out.println("||||||||||||||||||||||||||||||||||||||||||、");
		for(int i = 0 ; i < 11;i++){
			try {
				Thread t = new Thread( new ThreadNew(manager,100));
				t.start();
			} catch (Exception e) {
				System.out.println("=========================================================");
			}
			
		}
		try {
			Thread.sleep(15*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		manager.destroy();
		System.out.println("======FINAL END=============");
	}
	private static void testReadAndWrite(ShardJedisSentinelManager manager,
			String key) {
		System.out.println("===================");
		TinyShardJedis shardedJedis = manager.getShardedJedis();
		// shardedJedis.getShard获得的链接操作数据和直接通过TinyShardJedis是一样
		Jedis write = shardedJedis.getShard(key);
		// 只有这个种情况，是对读服务器进行服务器进行操作
		Jedis read = shardedJedis.getReadShard(key);
		System.out.println(write);
		System.out.println(read);
		Assert.assertNotSame(write, read);
		manager.returnResource(shardedJedis);
		System.out.println("===================");
	}
	

}
