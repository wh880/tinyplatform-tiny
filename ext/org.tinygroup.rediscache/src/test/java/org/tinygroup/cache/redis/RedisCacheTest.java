package org.tinygroup.cache.redis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.tinygroup.beancontainer.BeanContainerFactory;
import org.tinygroup.cache.Cache;
import org.tinygroup.cache.CacheManager;
import org.tinygroup.tinyrunner.Runner;

import junit.framework.TestCase;

/**
 * 验证基本的Cache接口<br>
 * 具体的redis配置地址请修改sample.jedisconfig.xml
 */
public class RedisCacheTest extends TestCase{

	
	public void testGetSet() {
        Runner.init(null, new ArrayList<String>());
		
		CacheManager manager = BeanContainerFactory.getBeanContainer(RedisCacheTest.class.getClassLoader()).getBean("redisCacheManager");
		
		Cache cache1 = manager.createCache("server01");
		Cache cache2 = manager.createCache("server02");
		assertNotNull(cache1);
		assertNotNull(cache2);
		
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
		
		assertTrue("abc".equals(cache1.get("name")));
		assertTrue(age.equals(cache2.get("age")));
		assertTrue(cache1.getGroupKeys("food").size()==3);
		assertTrue(foods.get("food2").equals(cache1.get("food","food2")));
		
		cache1.remove("name");
		assertNull(cache1.get("name"));
		cache2.remove("age");
		assertNull(cache2.get("age"));
		cache1.remove("food","food2");
		assertNull(cache1.get("food", "food2"));
		assertTrue(cache1.getGroupKeys("food").size()==2);
		cache1.cleanGroup("food");
		assertTrue(cache1.getGroupKeys("food").size()==0);
		cache2.remove("food");
		assertTrue(cache2.getGroupKeys("food").size()==0);
		
		cache1.put("aa", "123");
		cache1.put("bb", "456");
		assertEquals(2, cache1.get(new String[]{"aa" ,"bb"}).length);
		
		cache1.put("group" ,"aa", "111");
		cache1.put("group", "bb", "222");
		cache1.put("group1", "cc", "333");
		cache1.put("group1", "dd", "444");
		assertEquals(2, cache1.get("group" ,new String[]{"aa" ,"bb"}).length);
		assertEquals(2, cache1.get("group1" ,new String[]{"cc" ,"dd"}).length);
		
		cache1.put("aa", "111");
		cache1.put("bb", "222");
		cache1.remove(new String[]{"aa" ,"bb"});
		assertNull(cache1.get(new String[]{"aa" ,"bb"})[0]);
		assertNull(cache1.get(new String[]{"aa" ,"bb"})[1]);
		
		cache1.put("group" ,"aa", "111");
		cache1.put("group", "bb", "222");
		cache1.put("group1", "cc", "333");
		cache1.put("group1", "dd", "444");
		cache1.remove("group", new String[]{"aa" ,"bb"});
		assertNull(cache1.get("group", new String[]{"aa" ,"bb"})[0]);
		assertNull(cache1.get("group", new String[]{"aa" ,"bb"})[1]);
		
		System.out.println(cache1.getStats());
		
		manager.shutDown();
	}

}
