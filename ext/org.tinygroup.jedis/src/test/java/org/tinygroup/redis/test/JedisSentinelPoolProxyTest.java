package org.tinygroup.redis.test;

import java.util.ArrayList;

import junit.framework.Assert;

import org.tinygroup.beancontainer.BeanContainerFactory;
import org.tinygroup.jedis.JedisSentinelManager;
import org.tinygroup.jedis.impl.JedisSentinelClient2;
import org.tinygroup.tinyrunner.Runner;

public class JedisSentinelPoolProxyTest {
	
	public static void main(String[] args) {
		Runner.init(null, new ArrayList<String>());
		JedisSentinelManager manager = BeanContainerFactory.getBeanContainer(RedisTest.class.getClassLoader())
				.getBean("jedisSentinelManager");
		JedisSentinelClient2 client2 = manager.getJedisSentinelClient2("master1");
		String value = "value";
		client2.getWriteJedis().set("JedisSentinelPoolProxyTest", value);
		Assert.assertEquals(value, client2.getReadJedis().get("JedisSentinelPoolProxyTest"));
		
	}
}
