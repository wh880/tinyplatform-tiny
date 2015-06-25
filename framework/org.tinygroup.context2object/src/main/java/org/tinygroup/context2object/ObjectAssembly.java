/**
 *  Copyright (c) 1997-2013, www.tinygroup.org (tinygroup@126.com).
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
package org.tinygroup.context2object;

import org.tinygroup.context.Context;

/**
 * 对象组装接口
 * @author renhui
 *
 */
public interface ObjectAssembly<T> {
	
	/**
	 * 匹配type类型的组装
	 * @param type
	 * @return
	 */
	public boolean isMatch(Class<?> type);

	/**
	 * 根据上下文参数信息进行对象组装
	 * @param varName
	 * @param object
	 * @param context
	 */
	public void assemble(String varName,T object,Context context);
	
}
