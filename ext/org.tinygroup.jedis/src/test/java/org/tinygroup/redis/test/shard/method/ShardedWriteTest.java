package org.tinygroup.redis.test.shard.method;

import java.util.ArrayList;

import junit.framework.Assert;

import org.tinygroup.jedis.ShardJedisSentinelManager;
import org.tinygroup.jedis.impl.ShardJedisSentinelManagerFactory;
import org.tinygroup.jedis.shard.TinyShardJedis;
import org.tinygroup.tinyrunner.Runner;

import redis.clients.jedis.Jedis;

public class ShardedWriteTest {
	public static void main(String[] args) {
		Runner.init("application.xml", new ArrayList<String>());
		ShardJedisSentinelManager manager = ShardJedisSentinelManagerFactory.getManager();
		TinyShardJedis jedis = manager.getShardedJedis();
		System.out.println("读从:");
		// 因为写过一次，所以写状态被改为了true，于是此jedis从此以后不能在获得读连接，永远是写连接了
		jedis.set("aaa", "aaa");
		//因此获取的读和写链接从此以后都是写连接了
		Assert.assertEquals(jedis.getShard("aaa"), jedis.getReadShard("aaa"));
		//此处后面先删，再读
		//如果不采用前面提到的方案，有可能主从数据尚未同步，导致读出来的数据和期望不同
		jedis.del("aaa");
		Assert.assertTrue(!jedis.exists("aaa"));
		
		
		manager.returnResource(jedis);
		jedis = manager.getShardedJedis();
		Jedis readShard = jedis.getReadShard("aaa");
		System.out.println("写从:");
		try {
			readShard.set("aaa", "aaa");
			System.out.println("写从无异常:错误");
			Assert.assertTrue(false);
		} catch (Exception e) {
			System.out.println("写从异常:正确");
			Assert.assertTrue(true);
		}
		manager.returnResource(jedis);
		manager.destroy();
		
	}
}
