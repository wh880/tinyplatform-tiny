/**
 * 
 */
package org.tinygroup.redis.test.shard.method;

import java.util.ArrayList;

import org.tinygroup.jedis.ShardJedisSentinelManager;
import org.tinygroup.jedis.impl.ShardJedisSentinelManagerFactory;
import org.tinygroup.jedis.shard.TinyShardJedis;
import org.tinygroup.tinyrunner.Runner;

/**
 * @author Administrator
 * 
 */
public class ShardedMultSetPerformanceTest {

	public static void main(String[] args) {

		Runner.init("application.xml", new ArrayList<String>());
		ShardJedisSentinelManager manager = ShardJedisSentinelManagerFactory
				.getManager();
		TinyShardJedis jedis = manager.getShardedJedis();
		// ==============================
		jedis.flushAll();
		manager.returnResource(jedis);
		for (int i = 0; i < 100; i++) {
			SetValueTest test = new SetValueTest("线程" + i, manager);
			test.start();
		}

		// manager.destroy();

	}
}
