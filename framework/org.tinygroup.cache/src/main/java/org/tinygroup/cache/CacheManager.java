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

/**
 * 缓存管理接口
 * @author renhui
 *
 */
public interface CacheManager {

	/**
	 * 根据region创建相应的cache实例
	 * @param region
	 * @return
	 */
	Cache createCache(String region);
	/**
	 * 清除cache指定的缓存的内容
	 * @param cache
	 */
	void clearCache(Cache cache);
	/**
	 * 清楚所有缓存的内容
	 */
	void clearCaches();
	/**
	 * 从缓存管理器中移除缓存
	 * @param cache
	 */
	void removeCache(Cache cache);
	/**
	 * 从管理器中移除所有缓存
	 */
	void removeCaches();
	/**
	 * 缓存关闭接口
	 */
	void  shutDown();
	
}
