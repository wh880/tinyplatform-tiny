package org.tinygroup.redis.test.shard.method;

import java.util.ArrayList;

import org.tinygroup.jedis.ShardJedisSentinelManager;
import org.tinygroup.jedis.impl.ShardJedisSentinelManagerFactory;
import org.tinygroup.tinyrunner.Runner;

public class ShardedMethodTest {
	public static void main(String[] args) {
		Runner.init("application.xml", new ArrayList<String>());
		ShardJedisSentinelManager manager = ShardJedisSentinelManagerFactory.getManager();
		manager.destroy();
	
		
	}
}
