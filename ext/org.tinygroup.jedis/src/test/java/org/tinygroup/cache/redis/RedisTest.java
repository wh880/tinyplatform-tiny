package org.tinygroup.cache.redis;

import java.util.ArrayList;

import junit.framework.Assert;

import org.tinygroup.beancontainer.BeanContainerFactory;
import org.tinygroup.jedis.JedisManager;
import org.tinygroup.tinyrunner.Runner;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * 验证基本的Cache接口<br>
 * 具体的redis配置地址请修改sample.jedisconfig.xml
 */
public class RedisTest {
	public static void main(String[] args) {
		Runner.init(null, new ArrayList<String>());
		JedisManager manager = BeanContainerFactory.getBeanContainer(RedisTest.class.getClassLoader())
				.getBean("jedisManager");
		JedisPool pool = manager.getJedisPool("server01");
		Jedis jedis = pool.getResource();
		jedis.set("name", "aaa");
		String value = jedis.get("name");
		Assert.assertEquals("aaa", value);

	}

}
