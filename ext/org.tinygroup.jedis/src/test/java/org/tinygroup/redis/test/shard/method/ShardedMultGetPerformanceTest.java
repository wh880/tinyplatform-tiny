/**
 * 
 */
package org.tinygroup.redis.test.shard.method;

import java.util.ArrayList;

import org.tinygroup.beancontainer.BeanContainerFactory;
import org.tinygroup.jedis.ShardJedisSentinelManager;
import org.tinygroup.jedis.shard.TinyShardJedis;
import org.tinygroup.redis.test.RedisTest;
import org.tinygroup.redis.test.shard.ShardJedisSentinelManagerImplTest;
import org.tinygroup.tinyrunner.Runner;

import redis.clients.jedis.JedisPoolConfig;

/**
 * @author Administrator
 *
 */
public class ShardedMultGetPerformanceTest {

	public static void main(String[] args) {

		Runner.init("application.xml", new ArrayList<String>());
		JedisPoolConfig jedisConfig = BeanContainerFactory.getBeanContainer(
				ShardJedisSentinelManagerImplTest.class.getClassLoader())
				.getBean("jedisConfig");
		ShardJedisSentinelManager manager = BeanContainerFactory
				.getBeanContainer(RedisTest.class.getClassLoader()).getBean(
						"shardJedisSentinelManager");
		manager.init(jedisConfig);
		//==============================
//		jedis.flushAll();
		
		for (int i = 0; i < 100; i++) {
			SetValueTest test = new ShardedMultGetPerformanceTest().new SetValueTest("线程"+i, manager);
			test.start();
		}
		
		
//		manager.destroy();
	
	}
	
	public class SetValueTest extends Thread{
		
		private String id;
		private ShardJedisSentinelManager manager;
		
		public SetValueTest(String id ,ShardJedisSentinelManager manager) {
			this.id = id;
			this.manager = manager;
		}
		
		@Override
		public void run() {
			long preTime = System.currentTimeMillis();
			for (int i = 0; i < 100; i++) {
				TinyShardJedis jedis = getJedis();
				String value = jedis.get(id + i);
				returnJedis(jedis);
			}
			System.out.println("[" + id + "]耗时：" + (System.currentTimeMillis() - preTime));
		}
		
		private TinyShardJedis getJedis(){
			long preTime = System.currentTimeMillis();
			TinyShardJedis jedis = manager.getShardedJedis();
//			System.out.println("[" + id + "]获取链接耗时：" + (System.currentTimeMillis() - preTime));
			return jedis;
		}
		
		private void returnJedis(TinyShardJedis jedis){
			long preTime = System.currentTimeMillis();
			manager.returnResource(jedis);
//			System.out.println("[" + id + "]返回链接耗时：" + (System.currentTimeMillis() - preTime));
		}
		
	}
	
}
