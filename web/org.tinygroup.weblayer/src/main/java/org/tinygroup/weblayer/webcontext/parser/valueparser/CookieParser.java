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
package org.tinygroup.weblayer.webcontext.parser.valueparser;

/**
 * <code>CookieParser</code>是用来解析和添加HTTP请求中的cookies的接口。
 * <p>
 * 注意：<code>CookieParser</code>永远使用<code>ISO-8859-1</code>编码来处理cookie的名称和值。
 * </p>
 *
 * @author renhui
 */
public interface CookieParser extends ValueParser {
    int AGE_SESSION = -1;
    int AGE_DELETE  = 0;

    /** 设置session cookie。 */
    void setCookie(String name, String value);

    /**
     * Set a persisten cookie on the client that will expire after a maximum age
     * (given in seconds).
     */
    void setCookie(String name, String value, int seconds_age);

    /** Remove a previously set cookie from the client machine. */
    void removeCookie(String name);
}
