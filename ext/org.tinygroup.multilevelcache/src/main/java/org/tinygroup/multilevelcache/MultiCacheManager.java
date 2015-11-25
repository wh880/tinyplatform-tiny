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

/**
 * 
 * @author renhui
 * 
 */
public class MultiCacheManager extends AbstractCacheManager {

	MultiCacheManager multiCacheManager;
	
	private MultiCacheManager() {
	}

	public static MultiCacheManager getInstance() {
		return new MultiCacheManager();
	}

	public void shutDown() {
		removeCaches();
		cacheMap.clear();
	}

	@Override
	protected Cache newCache(String region) {
		MultiCache cache = new MultiCache();
        cache.init(region);
		return cache;
	}

	@Override
	protected void internalRemoveCache(Cache cache) {
		cache.clear();
	}

}
