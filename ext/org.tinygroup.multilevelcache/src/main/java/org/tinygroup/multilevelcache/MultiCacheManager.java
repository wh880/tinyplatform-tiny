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
package org.tinygroup.multilevelcache;

import org.tinygroup.cache.AbstractCacheManager;
import org.tinygroup.cache.Cache;
import org.tinygroup.cache.CacheManager;

/**
 * 
 * @author renhui
 * 
 */
public class MultiCacheManager extends AbstractCacheManager {

	private CacheManager cacheManager1;
	private CacheManager cacheManager2;
	static MultiCacheManager multiCacheManager;
	
	private MultiCacheManager() {
	}

	public void setCacheManager1(CacheManager cacheManager1) {
		this.cacheManager1 = cacheManager1;
	}

	public void setCacheManager2(CacheManager cacheManager2) {
		this.cacheManager2 = cacheManager2;
	}

	public static MultiCacheManager getInstance() {
		if (multiCacheManager == null) {
			multiCacheManager = new MultiCacheManager();
		}
		return multiCacheManager;
	}

	public void shutDown() {
		removeCaches();
		cacheMap.clear();
	}

	@Override
	protected MultiCache newCache(String region) {
		MultiCache cache = new MultiCache();
		Cache cache1 = cacheManager1.createCache(region);
		Cache cache2 = cacheManager2.createCache(region);
		cache1.setCacheManager(cacheManager1);
		cache2.setCacheManager(cacheManager2);
		cache.setCache1(cache1);
		cache.setCache2(cache2);
        cache.init(region);
		return cache;
	}

	@Override
	protected void internalRemoveCache(Cache cache) {
		cache.clear();
	}

}
