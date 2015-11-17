package org.tinygroup.redis.test.shard.method;

import org.tinygroup.jedis.ShardJedisSentinelManager;
import org.tinygroup.jedis.shard.TinyShardJedis;

public class SetValueTest extends Thread {

	private String id;
	private ShardJedisSentinelManager manager;

	public SetValueTest(String id, ShardJedisSentinelManager manager) {
		this.id = id;
		this.manager = manager;
	}

	@Override
	public void run() {
		System.out.println("[" + id + "]");
		TinyShardJedis jedis = getJedis();
		long preTime = System.currentTimeMillis();
		for (int i = 0; i < 1000; i++) {
			jedis.set(id + i, id + "_" + i);
		}
		System.out.println("[" + id + "]耗时："
				+ (System.currentTimeMillis() - preTime));
		returnJedis(jedis);
	}

	private TinyShardJedis getJedis() {
		//long preTime = System.currentTimeMillis();
		TinyShardJedis jedis = manager.getShardedJedis();
		// System.out.println("[" + id + "]获取链接耗时：" +
		// (System.currentTimeMillis() - preTime));
		return jedis;
	}

	private void returnJedis(TinyShardJedis jedis) {
		//long preTime = System.currentTimeMillis();
		manager.returnResource(jedis);
		// System.out.println("[" + id + "]返回链接耗时：" +
		// (System.currentTimeMillis() - preTime));
	}
}
