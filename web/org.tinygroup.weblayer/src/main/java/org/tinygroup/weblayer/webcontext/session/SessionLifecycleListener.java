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
package org.tinygroup.weblayer.webcontext.session;

import javax.servlet.http.HttpSession;

/**
 * session生命周期对应的拦截器
 * @author renhui
 *
 */
public interface SessionLifecycleListener extends SessionInterceptor {
    /** 当session第一次被创建以后，被调用。 */
    void sessionCreated(HttpSession session);

    /** 当session被作废后以后，被调用。 */
    void sessionInvalidated(HttpSession session);

    /** 当session被访问的时候，被调用。 */
    void sessionVisited(HttpSession session);
}