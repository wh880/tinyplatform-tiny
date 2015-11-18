package org.tinygroup.redis.test.shard.method;

import java.util.ArrayList;

import org.tinygroup.jedis.ShardJedisSentinelManager;
import org.tinygroup.jedis.impl.ShardJedisSentinelManagerFactory;
import org.tinygroup.jedis.shard.TinyShardJedis;
import org.tinygroup.tinyrunner.Runner;

import redis.clients.jedis.Jedis;

public class TestSingleThreadTime {
	public static void main(String[] args) {
		Runner.init("application.xml", new ArrayList<String>());
		ShardJedisSentinelManager manager = ShardJedisSentinelManagerFactory
				.getManager();
		TinyShardJedis shardedJedis = manager.getShardedJedis();
		shardedJedis.flushAll();
		// 这里直接操作jedis
		Jedis write = shardedJedis.getShard("a");
		long pre = System.currentTimeMillis();
		for (int i = 0; i < 1000; i++) {
			write.set("a" + i, "a" + i);
		}
		System.out.println("jedis set" + (System.currentTimeMillis() - pre));
		// =====================================
		pre = System.currentTimeMillis();
		for (int i = 0; i < 1000; i++) {
			write.get("a" + i);
		}
		System.out.println("jedis get" + (System.currentTimeMillis() - pre));
		// =====================================
		manager.returnResource(shardedJedis);
		// =====================================
		shardedJedis = manager.getShardedJedis();
		shardedJedis.flushAll();
		// =====================================
		pre = System.currentTimeMillis();
		for (int i = 0; i < 1000; i++) {
			shardedJedis.set("a" + i, "a" + i);
		}
		System.out.println("shardedJedis set"
				+ (System.currentTimeMillis() - pre));
		manager.returnResource(shardedJedis);
		// =====================================
		shardedJedis = manager.getShardedJedis();
		pre = System.currentTimeMillis();
		for (int i = 0; i < 1000; i++) {
			shardedJedis.get("a" + i);
		}
		System.out.println("shardedJedis get"
				+ (System.currentTimeMillis() - pre));
		manager.returnResource(shardedJedis);
		// =====================================
		manager.destroy();
	}
}
