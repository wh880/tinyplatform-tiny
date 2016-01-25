package org.tinygroup.redis.test.shard.method;

import java.util.ArrayList;

import org.tinygroup.jedis.ShardJedisSentinelManager;
import org.tinygroup.jedis.impl.ShardJedisSentinelManagerFactory;
import org.tinygroup.jedis.shard.TinyShardJedis;
import org.tinygroup.redis.test.IJedisConstant;
import org.tinygroup.tinyrunner.Runner;

import redis.clients.jedis.Jedis;

public class ShardedTimeTest {
	/**
	 * @param args
	 */
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Runner.init("application.xml", new ArrayList<String>());
		ShardJedisSentinelManager manager = ShardJedisSentinelManagerFactory.getManager();
		TinyShardJedis jedis = manager.getShardedJedis();
		test();
		jedis.flushAll();
		testShard(jedis);
		testSingle(jedis);
		
		manager.destroy();
	}

	private static void testShard(TinyShardJedis jedis) {
		System.out.println("Shard set begin");
		long time1 = System.currentTimeMillis();
		for (int i = 0; i < 2000; i++) {
			jedis.set("aaa" + i, "aaa" + i);
		}
		long time2 = System.currentTimeMillis();
		long time = time2 - time1;
		System.out.println("spend:" + time );
		System.out.println("average:" + (time / 2000d)
				+ "");
		System.out.println("end");
		// 
		System.out.println("Shard get begin");
		time1 = System.currentTimeMillis();
		for (int i = 0; i < 2000; i++) {
			jedis.get("aaa" + i);
		}
		time2 = System.currentTimeMillis();
		time = time2 - time1;
		System.out.println("spend:" + time );
		System.out.println("average:" + (time / 2000d)
				+ "");
		System.out.println("end");
	}

	private static void testSingle(TinyShardJedis jedis) {
		System.out.println("single set begin");
		long time1 = System.currentTimeMillis();
		for (int i = 0; i < 2000; i++) {
			jedis.set("aaa" + 1, "aaa" + 1);
		}
		long time2 = System.currentTimeMillis();
		long time = time2 - time1;
		System.out.println("spend:" + time );
		System.out.println("average:" + (time / 2000d)
				+ "");
		System.out.println("end");
		// 
		System.out.println("single get begin");
		time1 = System.currentTimeMillis();
		for (int i = 0; i < 2000; i++) {
			jedis.get("aaa" + 1);
		}
		time2 = System.currentTimeMillis();
		time = time2 - time1;
		System.out.println("spend:" + time );
		System.out.println("average:" + (time / 2000d)
				+ "");
		System.out.println("end");
	}

	private static void test() {

		System.out.println("get begin");
		long time1 = System.currentTimeMillis();
		Jedis jedis = new Jedis("192.168.51.29", 11111);
		jedis.auth(IJedisConstant.PASSWOPD);
		for (int i = 0; i < 2000; i++) {
			jedis.get("aaa" + 1);
		}
		long time2 = System.currentTimeMillis();
		long time = time2 - time1;
		System.out.println("spend:" + time );
		System.out.println("average:" + (time / 2000d));
		System.out.println("end");
	}
}
