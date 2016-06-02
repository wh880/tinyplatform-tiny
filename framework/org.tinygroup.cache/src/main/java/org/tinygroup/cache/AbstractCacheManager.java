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
package org.tinygroup.cache;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import org.tinygroup.cache.exception.CacheException;

/**
 * 
 * @author renhui
 * 
 */
public abstract class AbstractCacheManager implements CacheManager {

	protected BiMap<String, Cache> cacheMap = HashBiMap.create();
	
	/**
	 * 根据region创建相应的cache实例
	 * before 2.2.4
	 * 每次都会创建新的实例
	 * since 2.2.4
	 * 如果未创建，则创建实例，若已创建，则返回已创建的实例
	 * @param region
	 * @return
	 */
	public Cache createCache(String region) {
		if(cacheMap.containsKey(region)){
			return cacheMap.get(region);
		}
		Cache cache = newCache(region);
		if (cache == null) {
			throw new CacheException(String.format("region:%s,未在配置中定义", region));
		}
		cacheMap.put(region, cache);
		return cache;
	}

	protected abstract Cache newCache(String region);

	public void clearCache(Cache cache) {
		cache.clear();
	}

	public void clearCaches() {
		for (Cache cache : cacheMap.values()) {
			clearCache(cache);
		}
	}

	public void removeCaches() {
		for (Cache cache : cacheMap.values()) {
			removeCache(cache);
		}
	}

	public void removeCache(Cache cache) {
		clearCache(cache);
		internalRemoveCache(cache);
	}

	protected abstract void internalRemoveCache(Cache cache);
}
