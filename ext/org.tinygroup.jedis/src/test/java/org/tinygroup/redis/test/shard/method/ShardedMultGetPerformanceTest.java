/**
 * 
 */
package org.tinygroup.redis.test.shard.method;

import java.util.ArrayList;

import org.tinygroup.jedis.ShardJedisSentinelManager;
import org.tinygroup.jedis.impl.ShardJedisSentinelManagerFactory;
import org.tinygroup.tinyrunner.Runner;

/**
 * @author Administrator
 *
 */
public class ShardedMultGetPerformanceTest {

	public static void main(String[] args) {

		Runner.init("application.xml", new ArrayList<String>());
		ShardJedisSentinelManager manager = ShardJedisSentinelManagerFactory.getManager();
		//==============================
//		jedis.flushAll();
		
		for (int i = 0; i < 100; i++) {
			GetValueTest test = new GetValueTest("线程"+i, manager);
			test.start();
		}
		
		
//		manager.destroy();
	
	}
	
	
}
