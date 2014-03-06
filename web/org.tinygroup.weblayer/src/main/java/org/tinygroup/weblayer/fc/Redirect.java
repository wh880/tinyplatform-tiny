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
package org.tinygroup.weblayer.fc;

import org.tinygroup.context.Context;
import org.tinygroup.flow.ComponentInterface;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.weblayer.WebContext;

/**
 * 
 * 功能说明:重定向组件
 * <p>
 * 开发时间: 2013-5-2 <br>
 * 功能描述: 用于重定向到新的页面，这个是通过浏览器实现的重定向<br>
 */
public class Redirect implements ComponentInterface {
	private static final Logger logger = LoggerFactory
			.getLogger(Redirect.class);
	String path;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void execute(Context context) {
		try {
			WebContext webContext = (WebContext) context;
			webContext.getResponse().sendRedirect(path);
		} catch (Exception e) {
			logger.errorMessage("Redirect到地址[{}]出错，错误原因：{}", e, path,
					e.getMessage());
		}
	}

}
