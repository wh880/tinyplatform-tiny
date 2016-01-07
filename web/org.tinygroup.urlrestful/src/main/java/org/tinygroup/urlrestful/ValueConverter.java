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
package org.tinygroup.urlrestful;


/**
 * 参数值转换器
 */
public interface ValueConverter{
	
	String HANDLER_BEAN = "restfulStyleSubstitutionHandler";
	/**
	 * 根据参数值来判断是否匹配此值转换器
	 * @param value
	 * @return
	 */
	boolean isMatch(String value);

	/**
	 * 根据参数值转换成想要的对象
	 * @param value
	 * @return
	 */
	Object convert(String value);
}
