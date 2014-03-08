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


import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.tinygroup.weblayer.WebContext;


/**
 * response的包装
 * @author renhui
 */
public class AbstractResponseWrapper extends HttpServletResponseWrapper {
    private WebContext context;

    /**
     * 创建一个response wrapper。
     *
     * @param context  request context
     * @param response response
     */
    public AbstractResponseWrapper(WebContext context, HttpServletResponse response) {
        super(response);

        this.context = context;
    }

    /**
     * 取得当前request所处的servlet context环境。
     *
     * @return <code>ServletContext</code>对象
     */
    public ServletContext getServletContext() {
        return getWebContext().getServletContext();
    }

    /**
     * 取得支持它们的<code>getWebContext</code>。
     *
     * @return <code>getWebContext</code>实例
     */
    public WebContext getWebContext() {
        return context;
    }

    /**
     * 取得字符串表示。
     *
     * @return 字符串表示
     */
    
    public String toString() {
        return "Http response within request context: " + getWebContext().toString();
    }
}