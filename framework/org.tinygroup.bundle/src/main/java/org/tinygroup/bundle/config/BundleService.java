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
package org.tinygroup.bundle.config;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * Bundle对外提供的服务
 */
@XStreamAlias("bundle-service")
public class BundleService {
    @XStreamAsAttribute
    @XStreamAlias("instance-creator")
    private String instanceCreator;//实例创建器类名，用于创建实例

    @XStreamAsAttribute
    private String id;//标识
    @XStreamAsAttribute
    private String type; // 插件实现类接口
    @XStreamAsAttribute
    @XStreamAlias("class-name")
    private String className;// 插件的实现类
    @XStreamAsAttribute
    private String version;//版本
    private String description;//描述

    public String getInstanceCreator() {
        return instanceCreator;
    }

    public void setInstanceCreator(String instanceCreator) {
        this.instanceCreator = instanceCreator;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getClassName() {
        if (className == null || "".equals(className)) {
            className = type;
        }
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

}
