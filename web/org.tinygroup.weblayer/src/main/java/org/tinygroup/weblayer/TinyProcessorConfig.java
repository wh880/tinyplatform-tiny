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
package org.tinygroup.weblayer;




/**
 * processor的配置对象
 * @author renhui
 *
 */
public interface TinyProcessorConfig  extends BasicTinyConfig{
	
	/**
	 * 请求的url是否匹配定义的映射正则表达式
	 * @param url
	 * @return
	 */
	public boolean isMatch(String url);
	
   	
}
