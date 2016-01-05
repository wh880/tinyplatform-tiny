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

import org.tinygroup.urlrestful.config.Rules;

/**
 * restful管理器
 * @author renhui
 *
 */
public interface UrlRestfulManager {
	
	String URL_RESTFUL_XSTREAM ="urlrestful";
	
	/**
	 * 增加restful配置信息
	 * @param Rules
	 */
	public void addRules(Rules Rules);
	/**
	 * 移除restful配置信息
	 * @param Rules
	 */
	public void removeRules(Rules Rules);
	
	/**
	 * 根据请求路径、请求的方法以及请求头的accept 组装此次请求的上下文对象
	 * @param requestPath
	 * @param httpMethod
	 * @param accept
	 * @return
	 */
	public Context getContext(String requestPath, String httpMethod, String accept);
	

}
