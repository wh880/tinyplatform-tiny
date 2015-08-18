package org.tinygroup.cache.redis;

import java.util.HashMap;
import java.util.Map;

import org.tinygroup.beancontainer.BeanContainerFactory;
import org.tinygroup.cache.Cache;
import org.tinygroup.cache.CacheManager;
import org.tinygroup.tinytestutil.AbstractTestUtil;

/**
 * 验证RedisCache的性能<br>
 * 具体的redis配置地址请修改sample.jedisconfig.xml
 */
public class RedisCachePerformanceTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
        AbstractTestUtil.init(null, true);
		
		CacheManager manager = BeanContainerFactory.getBeanContainer(RedisCacheTest.class.getClassLoader()).getBean("redisCacheManager");
		
		Cache cache = manager.createCache("server01");
		Map<String,Food> foods = new HashMap<String,Food>();
		foods.put("food1", new Food("大蒜","蔬菜",7.0));
		foods.put("food2", new Food("炸酱面","面食",20.99));
		foods.put("food3", new Food("德国火腿","干货",225.0));
		
		long time = System.currentTimeMillis();
		for(int i=0;i<10000;i++){
		    cache.put("food", foods.get("food1"));
		    cache.remove("food");
		}
		System.out.println("cost time:"+(System.currentTimeMillis()-time)+"ms");
		
	}

}
