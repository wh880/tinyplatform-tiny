/**
 * 
 */
package org.tinygroup.multilevelcache;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.tinygroup.cache.Cache;
import org.tinygroup.cache.CacheManager;

/**
 * @author Administrator
 *
 */
public class MultiCache implements Cache{

	Cache cache1;
	
	Cache cache2;
	
	public void setCache1(Cache cache1) {
		this.cache1 = cache1;
	}

	public void setCache2(Cache cache2) {
		this.cache2 = cache2;
	}

	public void init(String region) {
		cache1.init(region);
		cache2.init(region);
	}

	public Object get(String key) {
		Object obj = cache1.get(key);
		if (obj == null) {
			obj = cache2.get(key);
			cache1.put(key, obj);
		}
		return obj;
	}

	public Object get(String group, String key) {
		Object obj = cache1.get(group ,key);
		if (obj == null) {
			obj = cache2.get(group ,key);
			cache1.put(group,key, obj);
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
		cache1.put(key ,object);
		cache2.put(key ,object);
	}

	public void putSafe(String key, Object object) {
		cache1.putSafe(key ,object);
		cache2.putSafe(key ,object);
	}

	public void put(String groupName, String key, Object object) {
		cache1.put(groupName ,key ,object);
		cache2.put(groupName ,key ,object);
	}

	public Set<String> getGroupKeys(String group) {
		Set<String> keys = cache1.getGroupKeys(group);
		if (keys == null || keys.size() == 0) {
			return cache2.getGroupKeys(group);
		}
		return null;
	}

	public void cleanGroup(String group) {
		cache1.cleanGroup(group);
		cache2.cleanGroup(group);
	}

	public void clear() {
		cache1.clear();
		cache2.clear();
	}

	public void remove(String key) {
		cache1.remove(key);
		cache2.remove(key);
	}

	public void remove(String group, String key) {
		cache1.remove(group ,key);
		cache2.remove(group ,key);
		
	}

	public void remove(String[] keys) {
		cache1.remove(keys);
		cache2.remove(keys);
	}

	public void remove(String group, String[] keys) {
		cache1.remove(group ,keys);
		cache2.remove(group ,keys);
	}

	public String getStats() {
		// TODO Auto-generated method stub
		return null;
	}

	public int freeMemoryElements(int numberToFree) {
		// TODO Auto-generated method stub
		return 0;
	}

	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	public void setCacheManager(CacheManager manager) {
		// TODO Auto-generated method stub
		
	}

}
