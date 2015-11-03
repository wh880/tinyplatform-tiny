package org.tinygroup.redis.test.shard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.tinygroup.jedis.impl.ShardedJedisSentinelPool;

public class ShardedJedisSentinelPoolTest {
	public static void main(String[] args) {
		List<String> masters = new ArrayList<String>();
		Map<String, String> sentinelsMap = new HashMap<String, String>();
		String sentinel = "192.168.51.29:33333";
		sentinelsMap.put(sentinel, sentinel);
		masters.add("master1");
		ShardedJedisSentinelPool tsjsl = new ShardedJedisSentinelPool(masters, sentinelsMap.keySet());
		tsjsl.getResource().set("a", "1111");
		Assert.assertEquals("1111", tsjsl.getResource().get("a"));
		System.out.println(tsjsl.getResource().get("a"));
		tsjsl.destroy();
	}
}
