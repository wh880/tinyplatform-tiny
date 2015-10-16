/**
 *  Copyright (c) 1997-2013, www.tinygroup.org (luo_guo@icloud.com).
 *
 *  Licensed under the GPL, Version 3.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.gnu.org/licenses/gpl.html
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.tinygroup.cache.jcs;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.jcs.JCS;
import org.apache.commons.jcs.access.GroupCacheAccess;
import org.apache.commons.jcs.engine.control.group.GroupAttrName;
import org.apache.commons.jcs.engine.control.group.GroupId;
import org.tinygroup.cache.Cache;
import org.tinygroup.cache.CacheManager;
import org.tinygroup.cache.exception.CacheException;

public class JcsCache implements Cache {
	private GroupCacheAccess groupCacheAccess;
	private CacheManager cacheManager;
	private static final String DEFAULT_GROUP_NAME = "defaultGroup";

	public JcsCache() {
	}

	private void checkSerializable(Object object) {
		if (!(object instanceof Serializable)) {
			throw new RuntimeException("对象必须实现Serializable接口");
		}
	}

	public Object get(String key) {
		return groupCacheAccess.getFromGroup(key, DEFAULT_GROUP_NAME);
	}

	public void put(String key, Object object) {
		checkSerializable(object);
		try {
			groupCacheAccess.putInGroup(key, DEFAULT_GROUP_NAME, object);
		} catch (Exception e) {
			throw new CacheException(e);
		}
	}

	public void putSafe(String key, Object object) {
		GroupAttrName<String> groupKey = getGroupAttrName(DEFAULT_GROUP_NAME,
				key);
		if (groupCacheAccess.getCacheControl().get(groupKey) != null) {
			throw new CacheException(
					"putSafe failed.  Object exists in the cache for key ["
							+ groupKey
							+ "].  Remove first or use a non-safe put to override the value.");
		}
		put(key, object);
	}

	public void put(String groupName, String key, Object object) {
		checkSerializable(object);
		try {
			groupCacheAccess.putInGroup(key, groupName, object);
		} catch (Exception e) {
			throw new CacheException(e);
		}
	}

	private GroupAttrName<String> getGroupAttrName(String group, String name) {
		GroupId gid = new GroupId(groupCacheAccess.getCacheControl()
				.getCacheName(), group);
		return new GroupAttrName<String>(gid, name);
	}

	public Object get(String groupName, String key) {
		try {
			return groupCacheAccess.getFromGroup(key, groupName);
		} catch (Exception e) {
			throw new CacheException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public Set<String> getGroupKeys(String group) {
		return groupCacheAccess.getGroupKeys(group);
	}

	public void cleanGroup(String group) {
		groupCacheAccess.invalidateGroup(group);
	}

	public void clear() {
		try {
			groupCacheAccess.clear();
		} catch (Exception e) {
			throw new CacheException(e);
		}
	}

	public void remove(String key) {
		try {
			groupCacheAccess.removeFromGroup(key, DEFAULT_GROUP_NAME);
		} catch (Exception e) {
			throw new CacheException(e);
		}

	}

	public void remove(String group, String key) {
		try {
			groupCacheAccess.removeFromGroup(key, group);
		} catch (Exception e) {
			throw new CacheException(e);
		}

	}

	public String getStats() {
		return groupCacheAccess.getStats();
	}

	public int freeMemoryElements(int numberToFree) {
		try {
			return groupCacheAccess.freeMemoryElements(numberToFree);
		} catch (org.apache.commons.jcs.access.exception.CacheException e) {
			throw new CacheException(e);
		}
	}

	public void init(String region) {
		try {
			groupCacheAccess = JCS.getGroupCacheInstance(region);
		} catch (Exception e) {
			throw new CacheException(e);
		}
	}

	public void destroy() {
		cacheManager.removeCache(this);
	}

	public void setCacheManager(CacheManager manager) {
		this.cacheManager = manager;
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

	public void remove(String[] keys) {
		if (keys != null && keys.length > 0) {
			for (int i = 0; i < keys.length; i++) {
				remove(keys[i]);
			}
		}
	}

	public void remove(String group, String[] keys) {
		if (keys != null && keys.length > 0) {
			for (int i = 0; i < keys.length; i++) {
				remove(group ,keys[i]);
			}
		}	
	}
	
}
