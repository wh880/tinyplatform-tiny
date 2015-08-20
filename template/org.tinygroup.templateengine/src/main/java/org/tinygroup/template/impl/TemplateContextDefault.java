/**
 * Copyright (c) 1997-2013, www.tinygroup.org (luo_guo@icloud.com).
 * <p/>
 * Licensed under the GPL, Version 3.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.gnu.org/licenses/gpl.html
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.tinygroup.template.impl;

import org.tinygroup.context.Context;
import org.tinygroup.context.impl.ContextImpl;
import org.tinygroup.template.TemplateContext;

import java.util.Map;

/**
 * Created by luoguo on 2014/6/6.
 */
public class TemplateContextDefault extends ContextImpl implements TemplateContext {
    {
        put("Integer", Integer.class);
        put("Long", Long.class);
        put("Short", Short.class);
        put("Double", Double.class);
        put("Float", Float.class);
        put("Boolean", Boolean.class);
        put("String", String.class);
        put("Byte", Byte.class);
        put("Number", Number.class);
        put("Character", Character.class);
        put("Math", Math.class);
        put("System", Math.class);
    }

    public TemplateContextDefault() {

    }

    public TemplateContextDefault(Map dataMap) {
        super(dataMap);
    }


    public boolean exist(String name) {
        boolean exist = super.exist(name);
        if (exist) {
            return true;
        }
        Context parentContext = getParent();
        if (parentContext != null) {
            return parentContext.exist(name);
        }
        return false;
    }

    public <T> T get(String name) {
        T result = (T) super.get(name);
        if (result != null) {
            return result;
        }
        Context parentContext = getParent();
        if (parentContext != null) {
            return (T) parentContext.get(name);
        }
        return null;
    }

}
