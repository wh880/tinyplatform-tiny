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
package org.tinygroup.dbrouter.factory;

import org.tinygroup.dbrouter.RouterManager;
import org.tinygroup.factory.BeanFactory;
import org.tinygroup.factory.Factory;
import org.tinygroup.factory.config.Beans;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.xstream.XStreamFactory;

import com.thoughtworks.xstream.XStream;

/**
 * 功能说明:创建集群管理对象的类

 * 开发人员: renhui <br>
 * 开发时间: 2013-12-25 <br>
 * <br>
 */
public class RouterManagerBeanFactory {

    private static Factory factory;
    private static RouterManager manager;
    private static Logger logger = LoggerFactory.getLogger(RouterManagerBeanFactory.class);

    public static final String DB_ROUTER_BEANS_XML = "/dbrouterbeans.xml";

    static {
        factory = BeanFactory.getFactory();
        XStream xStream = XStreamFactory.getXStream();
        logger.logMessage(LogLevel.INFO, "加载Bean配置文件{}开始...", DB_ROUTER_BEANS_XML);
        try {
            Beans beans = (Beans) xStream.fromXML(RouterManagerBeanFactory.class.getResourceAsStream(DB_ROUTER_BEANS_XML));
            factory.addBeans(beans);
            factory.init();
            logger.logMessage(LogLevel.INFO, "加载Bean配置文件{}结束。", DB_ROUTER_BEANS_XML);
        } catch (Throwable e) {
            logger.errorMessage("加载Bean配置文件{}时发生错误", e, DB_ROUTER_BEANS_XML);
        }
    }

    public static RouterManager getManager() {
        if (manager == null) {
            manager = factory.getBean("routerManager");
        }
        return manager;
    }


}
