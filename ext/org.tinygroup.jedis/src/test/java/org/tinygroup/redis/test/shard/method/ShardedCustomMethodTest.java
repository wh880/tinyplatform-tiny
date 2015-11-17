package org.tinygroup.redis.test.shard.method;

import java.util.ArrayList;
import java.util.Set;

import junit.framework.Assert;

import org.tinygroup.jedis.ShardJedisSentinelManager;
import org.tinygroup.jedis.impl.ShardJedisSentinelManagerFactory;
import org.tinygroup.jedis.shard.TinyShardJedis;
import org.tinygroup.tinyrunner.Runner;

public class ShardedCustomMethodTest {
	public static void main(String[] args) {
		Runner.init("application.xml", new ArrayList<String>());
		ShardJedisSentinelManager manager = ShardJedisSentinelManagerFactory.getManager();
		TinyShardJedis jedis = manager.getShardedJedis();
		//==============================
		jedis.flushAll();
		//==============================
		for (int i = 0; i < 20; i++) {
			jedis.set("aaa" + i, "aaa" + i);
		}
		jedis.set("b1", "b1");
		manager.returnResource(jedis);
		jedis = manager.getShardedJedis();
		Assert.assertEquals(jedis.get("b1"),"b1");
		Set<String> value = jedis.keys("aaa?");
		Set<String> value2 = jedis.keys("aaa*");
		Assert.assertEquals(10, value.size());
		Assert.assertEquals(20, value2.size());
		//==============================
		int num = jedis.deleteMatchKey("aaa?");
		int num1 = jedis.deleteMatchKey("aaa*");
		Assert.assertEquals(10, num);
		Assert.assertEquals(10, num1);
		//==============================
		int num2 = jedis.deleteMatchKey("b?");
		Assert.assertEquals(1, num2);
		jedis.flushAll();
		num2 = jedis.deleteMatchKey("b?");
		Assert.assertEquals(0, num2);
		//==============================
		manager.destroy();
	}
}
