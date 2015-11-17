/**
 * 
 */
package org.tinygroup.redis.test.shard.method;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.tinygroup.jedis.ShardJedisSentinelManager;
import org.tinygroup.jedis.impl.ShardJedisSentinelManagerFactory;
import org.tinygroup.jedis.shard.TinyShardJedis;
import org.tinygroup.tinyrunner.Runner;

import redis.clients.jedis.Jedis;

/**
 * @author Administrator
 * 
 */
public class ShardedReadAverageTest {

	public static void main(String[] args) {

		Runner.init("application.xml", new ArrayList<String>());
		ShardJedisSentinelManager manager = ShardJedisSentinelManagerFactory
				.getManager();
		// -----------------写数据---------------------
		long setPretime = System.currentTimeMillis();
		TinyShardJedis jedis = manager.getShardedJedis();
		jedis.flushAll();

		int times = 1000;
		for (int i = 0; i < times; i++) {
			jedis.set("a" + i, "a" + i);
		}

		jedis.resetState();
		manager.returnResource(jedis);
		System.out.println("set数据时间："
				+ (System.currentTimeMillis() - setPretime));

		// --------------------开始取链接测试-------------------------
		Map<Integer, Integer> totle = new HashMap<Integer, Integer>();

		long pretime = System.currentTimeMillis();
		for (int i = 0; i < times; i++) {
			TinyShardJedis readJedis = manager.getShardedJedis();
			Jedis Jedis = readJedis.getReadShard("a" + i);
			int port = Jedis.getClient().getPort();

			if (totle.get(port) == null) {
				totle.put(port, 0);
			}
			totle.put(port, totle.get(port) + 1);
			manager.returnResource(readJedis);
		}
		System.out.println("get数据时间：" + (System.currentTimeMillis() - pretime));
		manager.destroy();

		// --------------打印结果--------------
		System.out.println("运行次数:" + times);
		for (Iterator<Integer> iterator = totle.keySet().iterator(); iterator
				.hasNext();) {
			int port = iterator.next();
			System.out.println("端口[" + port + "]调用次数:" + totle.get(port));
		}
	}

	public class SetValueTest extends Thread {

		private String id;
		private ShardJedisSentinelManager manager;

		public SetValueTest(String id, ShardJedisSentinelManager manager) {
			this.id = id;
			this.manager = manager;
		}

		@Override
		public void run() {
			long preTime = System.currentTimeMillis();
			for (int i = 0; i < 100; i++) {
				TinyShardJedis jedis = getJedis();
				// String value =
				jedis.get(id + i);
				returnJedis(jedis);
			}
			System.out.println("[" + id + "]耗时："
					+ (System.currentTimeMillis() - preTime));
		}

		private TinyShardJedis getJedis() {
			// long preTime = System.currentTimeMillis();
			TinyShardJedis jedis = manager.getShardedJedis();
			// System.out.println("[" + id + "]获取链接耗时：" +
			// (System.currentTimeMillis() - preTime));
			return jedis;
		}

		private void returnJedis(TinyShardJedis jedis) {
			// long preTime = System.currentTimeMillis();
			manager.returnResource(jedis);
			// System.out.println("[" + id + "]返回链接耗时：" +
			// (System.currentTimeMillis() - preTime));
		}

	}

}
