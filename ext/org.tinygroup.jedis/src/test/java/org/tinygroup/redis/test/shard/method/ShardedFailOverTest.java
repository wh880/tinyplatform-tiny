package org.tinygroup.redis.test.shard.method;

import java.util.ArrayList;

import org.tinygroup.beancontainer.BeanContainerFactory;
import org.tinygroup.jedis.ShardJedisSentinelManager;
import org.tinygroup.jedis.shard.TinyShardJedis;
import org.tinygroup.redis.test.RedisTest;
import org.tinygroup.redis.test.shard.ShardJedisSentinelManagerImplTest;
import org.tinygroup.tinyrunner.Runner;

import redis.clients.jedis.JedisPoolConfig;

public class ShardedFailOverTest {
	public static void main(String[] args) {
		Runner.init("application.xml", new ArrayList<String>());
		JedisPoolConfig jedisConfig = BeanContainerFactory.getBeanContainer(
				ShardJedisSentinelManagerImplTest.class.getClassLoader())
				.getBean("jedisConfig");
		ShardJedisSentinelManager manager = BeanContainerFactory
				.getBeanContainer(RedisTest.class.getClassLoader()).getBean(
						"shardJedisSentinelManager");
		manager.init(jedisConfig);
		
		for(int i = 0;i<1000;i++){
			try {
				write(manager);
				System.out.println("Ok:"+System.currentTimeMillis());
			} catch (Exception e) {
				System.out.println("Exception:"+System.currentTimeMillis());
				
			}
		}
		
	}
	//测试主备切换影响
	//启动后关闭其所访问的主备服务器中的主服务器
	//看是否会对写产生影响
	//aaa分片是分布到了4xxxx端口系列的服务器上
	public static void write(ShardJedisSentinelManager manager){
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		TinyShardJedis jedis2 = manager.getShardedJedis();
		
		jedis2.set("aaa", "aaa");
		manager.returnResource(jedis2);
	}
}
