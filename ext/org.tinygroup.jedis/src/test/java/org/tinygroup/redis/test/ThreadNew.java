package org.tinygroup.redis.test;

import junit.framework.Assert;

import org.tinygroup.jedis.ShardJedisSentinelManager;
import org.tinygroup.jedis.shard.TinyShardJedis;

import redis.clients.jedis.Jedis;

public class ThreadNew implements Runnable {
	ShardJedisSentinelManager manager ;
	int times;
	
	public ThreadNew(ShardJedisSentinelManager manager,int times) {
		this.manager = manager;
		this.times = times;
	}

	public void run() {
		for (int i = 0; i < times; i++) {
			testReadAndWrite(manager, "WriteAndRead" + i + i + i);
		}
		System.out.println("=========================END================================");
	}
	

	private void testReadAndWrite(ShardJedisSentinelManager manager,
			String key) {
		System.out.println("----------------------");
		TinyShardJedis shardedJedis = manager.getShardedJedis();
		// shardedJedis.getShard获得的链接操作数据和直接通过TinyShardJedis是一样
		Jedis write = shardedJedis.getShard(key);
		// 只有这个种情况，是对读服务器进行服务器进行操作
		Jedis read = shardedJedis.getReadShard(key);
		System.out.println(write);
		System.out.println(read);
		Assert.assertNotSame(write, read);
		manager.returnResource(shardedJedis);
		System.out.println("----------------------");
	}
}
