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
package org.tinygroup.weblayer.webcontext;

import org.tinygroup.weblayer.WebContext;

/**
 * 
 * 功能说明:默认的上下文实现，如果filter没有自定义增强上下文的对象，那么就返回此对象

 * 开发人员: renhui <br>
 * 开发时间: 2013-7-1 <br>
 * <br>
 */
public class DefaultWebContext extends AbstractWebContextWrapper {

	public DefaultWebContext() {
		super();
	}

	public DefaultWebContext(WebContext wrappedContext) {
		super(wrappedContext);
	}

	
}
