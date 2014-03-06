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
package org.tinygroup.weblayer.webcontext.session.impl;

import org.tinygroup.weblayer.webcontext.session.SessionAttributeInterceptor;
import org.tinygroup.weblayer.webcontext.session.SessionConfig;

/**
 * 用来控制session attributes的使用。
 *
 * @author renhui
 */
public abstract class AbstractSessionAttributeAccessController implements SessionAttributeInterceptor {
    private String modelKey;

    public void init(SessionConfig sessionConfig) {
        this.modelKey = sessionConfig.getModelKey();
    }

    public final Object onRead(String name, Object value) {
        if (modelKey.equals(name) || allowForAttribute(name, value == null ? null : value.getClass())) {
            return value;
        }

        return readInvalidAttribute(name, value);
    }

    public final Object onWrite(String name, Object value) {
        if (modelKey.equals(name) || allowForAttribute(name, value == null ? null : value.getClass())) {
            return value;
        }

        return writeInvalidAttribute(name, value);
    }

    protected abstract boolean allowForAttribute(String name, Class<?> type);

    protected abstract Object readInvalidAttribute(String name, Object value);

    protected abstract Object writeInvalidAttribute(String name, Object value);
}
