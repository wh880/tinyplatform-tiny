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
package org.tinygroup.config;

import org.tinygroup.xmlparser.node.XmlNode;

import java.util.Collection;
import java.util.Map;

/**
 * 应用配置管理器
 *
 * @author luoguo
 */
public interface ConfigurationManager {

    /**
     * 设置配置加载器
     *
     * @param configurationLoader
     */
    void setConfigurationLoader(ConfigurationLoader configurationLoader);

    void setApplicationConfiguration(XmlNode applicationConfiguration);

    void setComponentConfigurationMap(Map<String, XmlNode> componentConfiguration);

    XmlNode getApplicationConfiguration();

    Map<String, XmlNode> getComponentConfigurationMap();

    /**
     * 分发应用配置<br>
     * 应用配置会促使配置管理器把配置信息推送到配置订阅者
     */
    void distributeConfiguration();


    void setConfigurationList(Collection<Configuration> configurationList);

    void loadConfiguration();
}
