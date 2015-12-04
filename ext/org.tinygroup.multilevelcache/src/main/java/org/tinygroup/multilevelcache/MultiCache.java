/**
 * 
 */
package org.tinygroup.multilevelcache;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.tinygroup.cache.Cache;
import org.tinygroup.cache.CacheManager;

/**
 * @author Administrator
 *
 */
public class MultiCache implements Cache{

	/**
	 * 1级缓存
	 */
	Cache cacheFirst;
	
	/**
	 * 2级缓存
	 */
	Cache cacheSecond;
	
	CacheManager manager;
	
	public MultiCache() {
	}
	
	public MultiCache(Cache cacheFirst, Cache cacheSecond) {
		super();
		this.cacheFirst = cacheFirst;
		this.cacheSecond = cacheSecond;
	}

	/**
	 * 1，2级缓存，分别自己初始化，防止缓存重复
	 */
	public void init(String region) {
		
	}

	public Object get(String key) {
		Object obj = cacheFirst.get(key);
		if (obj == null) {
			obj = cacheSecond.get(key);
			cacheFirst.put(key, obj);
		}
		return obj;
	}

	public Object get(String group, String key) {
		Object obj = cacheFirst.get(group ,key);
		if (obj == null) {
			obj = cacheSecond.get(group ,key);
			cacheFirst.put(group,key, obj);
		}
		return obj;
	}

	public Object[] get(String[] keys) {
		List<Object> objs = new ArrayList<Object>();
		if (keys != null && keys.length > 0) {
			for (int i = 0; i < keys.length; i++) {
				objs.add(get(keys[i]));
			}
		}
		return objs.toArray();
	}

	public Object[] get(String group, String[] keys) {
		List<Object> objs = new ArrayList<Object>();
		if (keys != null && keys.length > 0) {
			for (int i = 0; i < keys.length; i++) {
				objs.add(get(group ,keys[i]));
			}
		}
		return objs.toArray();
	}

	public void put(String key, Object object) {
		cacheFirst.put(key ,object);
		cacheSecond.put(key ,object);
	}

	public void putSafe(String key, Object object) {
		cacheFirst.putSafe(key ,object);
		cacheSecond.putSafe(key ,object);
	}

	public void put(String groupName, String key, Object object) {
		cacheFirst.put(groupName ,key ,object);
		cacheSecond.put(groupName ,key ,object);
	}

	public Set<String> getGroupKeys(String group) {
		Set<String> firstKeys = cacheFirst.getGroupKeys(group);
		if (firstKeys != null && firstKeys.size() > 0) {
			return firstKeys;
		}
		Set<String> secondKeys = cacheSecond.getGroupKeys(group);
		if (secondKeys != null && secondKeys.size() > 0) {
			return secondKeys;
		}
		return new HashSet<String>();
	}

	public void cleanGroup(String group) {
		cacheFirst.cleanGroup(group);
		cacheSecond.cleanGroup(group);
	}

	public void clear() {
		cacheFirst.clear();
		cacheSecond.clear();
	}

	public void remove(String key) {
		cacheFirst.remove(key);
		cacheSecond.remove(key);
	}

	public void remove(String group, String key) {
		cacheFirst.remove(group ,key);
		cacheSecond.remove(group ,key);
		
	}

	public void remove(String[] keys) {
		cacheFirst.remove(keys);
		cacheSecond.remove(keys);
	}

	public void remove(String group, String[] keys) {
		cacheFirst.remove(group ,keys);
		cacheSecond.remove(group ,keys);
	}

	public String getStats() {
		StringBuffer sb = new StringBuffer();
		sb.append("first cache:" + cacheFirst.getStats() + "\r\n");
		sb.append("second cache:" + cacheSecond.getStats() + "\r\n");
		return sb.toString();
	}

	/**
	 * 同时释放1，2级缓存内存空间
	 * 只返回两者释放最大值
	 */
	public int freeMemoryElements(int numberToFree) {
		int firstNum = 0;
		int secondNum = 0;
		try {
			firstNum = cacheFirst.freeMemoryElements(numberToFree);
		} catch (Exception e) {
			//异常内存，异常不处理
		}
		try {
			secondNum = cacheSecond.freeMemoryElements(numberToFree);
		} catch (Exception e) {
			//
		}
		
		return firstNum > secondNum ? firstNum :secondNum;
	}

	/**
	 * 外部处理，不在这里处理，原因和init方法同理，本类只使用缓存，不涉及初始化和销毁
	 */
	public void destroy() {
		
	}

	public void setCacheManager(CacheManager manager) {
		this.manager = manager;
	}

}
