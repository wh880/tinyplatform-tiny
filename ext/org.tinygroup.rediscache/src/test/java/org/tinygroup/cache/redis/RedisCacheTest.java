package org.tinygroup.cache.redis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.tinygroup.beancontainer.BeanContainerFactory;
import org.tinygroup.cache.Cache;
import org.tinygroup.cache.CacheManager;
import org.tinygroup.tinyrunner.Runner;

import junit.framework.Assert;

/**
 * 验证基本的Cache接口<br>
 * 具体的redis配置地址请修改sample.jedisconfig.xml
 */
public class RedisCacheTest{

	public static void main(String[] args) {
        Runner.init(null, new ArrayList<String>());
		
		CacheManager manager = BeanContainerFactory.getBeanContainer(RedisCacheTest.class.getClassLoader()).getBean("redisCacheManager");
		
		Cache cache1 = manager.createCache("server01");
		Cache cache2 = manager.createCache("server02");
		Assert.assertNotNull(cache1);
		Assert.assertNotNull(cache2);
		
		Integer age=new Integer(80);
		Map<String,Food> foods = new HashMap<String,Food>();
		foods.put("food1", new Food("大蒜","蔬菜",7.0));
		foods.put("food2", new Food("炸酱面","面食",20.99));
		foods.put("food3", new Food("德国火腿","干货",225.0));
		
		
		cache1.put("name", "abc");
		cache2.putSafe("age",age);
		for(Entry<String, Food> entry:foods.entrySet()){
			cache1.put("food", entry.getKey(), entry.getValue());
			cache2.put("food", entry.getKey(), entry.getValue());
		}
		
		Assert.assertTrue("abc".equals(cache1.get("name")));
		Assert.assertTrue(age.equals(cache2.get("age")));
		Assert.assertTrue(cache1.getGroupKeys("food").size()==3);
		Assert.assertTrue(foods.get("food2").equals(cache1.get("food","food2")));
		
		cache1.remove("name");
		Assert.assertNull(cache1.get("name"));
		cache2.remove("age");
		Assert.assertNull(cache2.get("age"));
		cache1.remove("food","food2");
		Assert.assertNull(cache1.get("food", "food2"));
		Assert.assertTrue(cache1.getGroupKeys("food").size()==2);
		cache1.cleanGroup("food");
		Assert.assertTrue(cache1.getGroupKeys("food").size()==0);
		cache2.remove("food");
		Assert.assertTrue(cache2.getGroupKeys("food").size()==0);
		
		cache1.put("aa", "123");
		cache1.put("bb", "456");
		Assert.assertEquals(2, cache1.get(new String[]{"aa" ,"bb"}).length);
		
		cache1.put("group" ,"aa", "111");
		cache1.put("group", "bb", "222");
		cache1.put("group1", "cc", "333");
		cache1.put("group1", "dd", "444");
		Assert.assertEquals(2, cache1.get("group" ,new String[]{"aa" ,"bb"}).length);
		Assert.assertEquals(2, cache1.get("group1" ,new String[]{"cc" ,"dd"}).length);
		
		cache1.put("aa", "111");
		cache1.put("bb", "222");
		cache1.remove(new String[]{"aa" ,"bb"});
		Assert.assertNull(cache1.get(new String[]{"aa" ,"bb"})[0]);
		Assert.assertNull(cache1.get(new String[]{"aa" ,"bb"})[1]);
		
		cache1.put("group" ,"aa", "111");
		cache1.put("group", "bb", "222");
		cache1.put("group1", "cc", "333");
		cache1.put("group1", "dd", "444");
		cache1.remove("group", new String[]{"aa" ,"bb"});
		Assert.assertNull(cache1.get("group", new String[]{"aa" ,"bb"})[0]);
		Assert.assertNull(cache1.get("group", new String[]{"aa" ,"bb"})[1]);
		
		System.out.println(cache1.getStats());
		
		manager.shutDown();
	}

}
