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

import java.util.ArrayList;
import java.util.List;

/**
 * 具体的一个Bundle定义
 */
@XStreamAlias("bundle")
public class BundleDefine {
    private static final int MAX_LEVEL = 99;
    @XStreamAsAttribute
    private String id;//标识
    @XStreamAsAttribute
    private String version;//版本
    @XStreamAsAttribute
    private String type;// 接口类名
    @XStreamAsAttribute
    private int level;// 级别，1~,越小级别越高，级别是1的，属于内核级别，不允许停止操作，且自动启动，如果不指定作为99计算
    @XStreamAlias("bundle-services")
    private List<BundleService> bundleServices;//一个Bundle可以对外提供多个服务
    @XStreamAlias("bundle-dependencies")
    private List<BundleDependency> bundleDependencies;//一个Bundle依赖的Bundle列表
    private String description;//描述信息


    public int getLevel() {
        if (level <= 0 || level > MAX_LEVEL) {
            return MAX_LEVEL;
        }
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<BundleService> getBundleServices() {
        if (bundleServices == null) {
            bundleServices = new ArrayList<BundleService>();
        }
        return bundleServices;
    }

    public void setBundleServices(List<BundleService> bundleServices) {
        this.bundleServices = bundleServices;
    }

    public List<BundleDependency> getBundleDependencies() {
        if (bundleDependencies == null) {
            bundleDependencies = new ArrayList<BundleDependency>();
        }
        return bundleDependencies;
    }

    public void setBundleDependencies(List<BundleDependency> bundleDependencies) {
        this.bundleDependencies = bundleDependencies;
    }

}
