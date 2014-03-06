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
package org.tinygroup.tinydb;

import java.util.HashMap;

/**
 * Bean，用于描述各种对象
 */
public class Bean extends HashMap<String, Object> {
	private static final long serialVersionUID = 7766936015089695L;
	private String type;// 对象所属的类，命名规则与类相同,

	public void setProperty(String propertyName, Object property) {
		this.put(propertyName, property);
	}

	@SuppressWarnings("unchecked")
	public <T> T getProperty(String propertyName) {
		return (T) this.get(propertyName);
	}

	/**
	 * 
	 * @param type
	 */
	public Bean(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

}
