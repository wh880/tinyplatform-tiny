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
package org.tinygroup.jspengine.runtime;

/**
 * Implements javax.servlet.jsp.JspApplication
 */

import org.tinygroup.jspengine.Constants;
import org.tinygroup.jspengine.el.ExpressionFactoryImpl;

import javax.el.*;
import javax.servlet.ServletContext;
import javax.servlet.jsp.JspApplicationContext;
import java.util.*;

public class JspApplicationContextImpl implements JspApplicationContext {

    public JspApplicationContextImpl(ServletContext context) {
        this.context = context;
    }

    public void addELResolver(ELResolver resolver) {
        if ("true".equals(context.getAttribute(Constants.FIRST_REQUEST_SEEN))) {
            throw new IllegalStateException("Attempt to invoke addELResolver "
                + "after the application has already received a request");
        }

        elResolvers.add(0, resolver);
    }

    public ExpressionFactory getExpressionFactory() {
        if (expressionFactory == null) {
            expressionFactory = new ExpressionFactoryImpl();
        }
        return expressionFactory;
    }

    public void addELContextListener(ELContextListener listener) {
        listeners.add(listener);
    }

    protected ELContext createELContext(ELResolver resolver) {

        ELContext elContext = new ELContextImpl(resolver);

        // Notify the listeners
        Iterator<ELContextListener> iter = listeners.iterator();
        while (iter.hasNext()) {
            ELContextListener elcl = iter.next();
            elcl.contextCreated(new ELContextEvent(elContext));
        }
        return elContext;
    }

    protected static JspApplicationContextImpl findJspApplicationContext(ServletContext context) {

        JspApplicationContextImpl jaContext = map.get(context);
        if (jaContext == null) {
            jaContext = new JspApplicationContextImpl(context);
            map.put(context, jaContext);
        }
        return jaContext;
    }

    public static void removeJspApplicationContext(ServletContext context) {
        map.remove(context);
    }

    protected Iterator<ELResolver> getELResolvers() {
        return elResolvers.iterator();
    }

    private static Map<ServletContext, JspApplicationContextImpl> map =
            Collections.synchronizedMap(
                new HashMap<ServletContext, JspApplicationContextImpl>());

    private ArrayList<ELResolver> elResolvers = new ArrayList<ELResolver>();
    private ArrayList<ELContextListener> listeners =
            new ArrayList<ELContextListener>();
    private ServletContext context;
    private ExpressionFactory expressionFactory;
}

