/**
 *  Copyright (c) 1997-2013, tinygroup.org (luo_guo@live.cn).
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
 * --------------------------------------------------------------------------
 *  版权 (c) 1997-2013, tinygroup.org (luo_guo@live.cn).
 *
 *  本开源软件遵循 GPL 3.0 协议;
 *  如果您不遵循此协议，则不被允许使用此文件。
 *  你可以从下面的地址获取完整的协议文本
 *
 *       http://www.gnu.org/licenses/gpl.html
 */
package org.tinygroup.context;

import java.util.Collection;

import java.util.Map;
import java.util.Set;

/**
 * 把Context包装成Map
 * 
 * @author luoguo
 * 
 */
public class Context2Map implements Map<String, Object> {
	private Context context = null;

	public Context2Map(Context context) {
		this.context = context;
	}

	public int size() {
		throw new RuntimeException("This method is not supported.");
	}

	public boolean isEmpty() {
		throw new RuntimeException("This method is not supported.");
	}

	public boolean containsKey(Object key) {
		return context.get(key.toString()) != null;
	}

	public boolean containsValue(Object value) {
		throw new RuntimeException("This method is not supported.");
	}

	public Object get(Object key) {
		return context.get(key.toString());
	}

	public Object put(String key, Object value) {
		context.put(key, value);
		return value;
	}

	public Object remove(Object key) {
		throw new RuntimeException("This method is not supported.");
	}

	public void putAll(Map<? extends String, ? extends Object> map) {
		for (String key : map.keySet()) {
			context.put(key, map.get(key));
		}
	}

	public void clear() {
		throw new RuntimeException("This method is not supported.");
	}

	public Set<String> keySet() {
		throw new RuntimeException("This method is not supported.");
	}

	public Collection<Object> values() {
		throw new RuntimeException("This method is not supported.");
	}

	public Set<java.util.Map.Entry<String, Object>> entrySet() {
		throw new RuntimeException("This method is not supported.");
	}

}
