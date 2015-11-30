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
	Cache cache_first;
	
	/**
	 * 2级缓存
	 */
	Cache cache_second;
	
	public MultiCache() {
	}
	
	public MultiCache(Cache cache_first, Cache cache2_second) {
		super();
		this.cache_first = cache_first;
		this.cache_second = cache2_second;
	}

	public void init(String region) {
	}

	public Object get(String key) {
		Object obj = cache_first.get(key);
		if (obj == null) {
			obj = cache_second.get(key);
			cache_first.put(key, obj);
		}
		return obj;
	}

	public Object get(String group, String key) {
		Object obj = cache_first.get(group ,key);
		if (obj == null) {
			obj = cache_second.get(group ,key);
			cache_first.put(group,key, obj);
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
		cache_first.put(key ,object);
		cache_second.put(key ,object);
	}

	public void putSafe(String key, Object object) {
		cache_first.putSafe(key ,object);
		cache_second.putSafe(key ,object);
	}

	public void put(String groupName, String key, Object object) {
		cache_first.put(groupName ,key ,object);
		cache_second.put(groupName ,key ,object);
	}

	public Set<String> getGroupKeys(String group) {
		Set<String> keys = new HashSet<String>();
		Set<String> fkeys = cache_first.getGroupKeys(group);
		if (fkeys != null) {
			keys.addAll(fkeys);
		}
		Set<String> skeys = cache_second.getGroupKeys(group);
		if (skeys != null) {
			keys.addAll(skeys);
		}
		return keys;
	}

	public void cleanGroup(String group) {
		cache_first.cleanGroup(group);
		cache_second.cleanGroup(group);
	}

	public void clear() {
		cache_first.clear();
		cache_second.clear();
	}

	public void remove(String key) {
		cache_first.remove(key);
		cache_second.remove(key);
	}

	public void remove(String group, String key) {
		cache_first.remove(group ,key);
		cache_second.remove(group ,key);
		
	}

	public void remove(String[] keys) {
		cache_first.remove(keys);
		cache_second.remove(keys);
	}

	public void remove(String group, String[] keys) {
		cache_first.remove(group ,keys);
		cache_second.remove(group ,keys);
	}

	public String getStats() {
		StringBuffer sb = new StringBuffer();
		sb.append("cache1:" + cache_first.getStats() + "\r\n");
		sb.append("cache2:" + cache_second.getStats() + "\r\n");
		return sb.toString();
	}

	
	public int freeMemoryElements(int numberToFree) {
		int fnum = 0;
		int snum = 0;
		try {
			fnum = cache_first.freeMemoryElements(numberToFree);
		} catch (Exception e) {
			
		}
		try {
			snum = cache_second.freeMemoryElements(numberToFree);
		} catch (Exception e) {
		}
		
		return fnum > snum ? fnum :snum;
	}

	public void destroy() {
	}

	public void setCacheManager(CacheManager manager) {
		
	}

}
