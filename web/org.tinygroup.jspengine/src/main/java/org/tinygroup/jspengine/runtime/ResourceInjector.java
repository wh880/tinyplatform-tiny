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

import javax.servlet.ServletContext;
import javax.servlet.jsp.tagext.JspTag;

/**
 * Interface for injecting injectable resources into tag handler instances.
 *
 * @author Jan Luehe
 */
public interface ResourceInjector {

    /**
     * Associates this ResourceInjector with the component environment of the
     * given servlet context.
     *
     * @param servletContext The servlet context 
     */
    public void setContext(ServletContext servletContext);


    /**
     * Injects the injectable resources from the component environment 
     * associated with this ResourceInjector into the given tag handler
     * instance. 
     *
     * <p>Any @PostConstruct methods on the class (and super-classes)
     * of the instance will be invoked after injection.
     *
     * @param handler The tag handler instance to be injected
     *
     * @throws Exception if an error occurs during injection
     */
    public void inject(JspTag handler) throws Exception;


    /**
     * Invokes any @PreDestroy methods defined on the instance's class
     * (and super-classes).
     *
     * @param handler The tag handler instance whose @PreDestroy methods
     * to call
     */
    public void preDestroy(JspTag handler);

}
