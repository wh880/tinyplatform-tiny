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
package org.tinygroup.bundle;

import org.tinygroup.bundle.config.BundleDefine;

import java.util.Collection;
import java.util.List;

public interface BundleManager extends SingleBundleManager, BundleBatchManager, BundleLevelManager {
    String BUNDLE_XSTREAM = "bundle";
    int STATUS_UNINITIALIZED = 0;// 未初始化的
    int STATUS_INITING = 1;// 正在初始化
    int STATUS_INITED = 2;// 已初始化
    int STATUS_ASSEMBLEING = 3;// 正在装配
    int STATUS_ASSEMBLEED = 4;// 已装配
    int STATUS_STARTING = 5;// 正在开始中
    int STATUS_READY = 6;// 就绪
    int STATUS_PAUSING = 7;// 暂停中
    int STATUS_PAUSED = 8;// 已暂停
    int STATUS_STOPING = 9;// 正在停止中
    int STATUS_DISASSEMBLEING = 10;// 正在拆装
    int STATUS_DESTORYING = 11;// 正在执行销毁

    void setBundleLoader(BundleLoader bundleLoader);

    void add(BundleDefine bundleDefine);

    void add(BundleDefine[] bundleDefines);

    void add(Collection<BundleDefine> bundleDefineCollection);

    int status(BundleDefine bundleDefine);

    BundleDefine getBundleDefine(String id, String version);

    BundleDefine getBundleDefine(String id);

    List<BundleDefine> getBundleDefineList();

    void remove(BundleDefine bundleDefine);

    void remove(Collection<BundleDefine> bundleDefineCollection);

    void removeAll();

    <T> T getService(BundleDefine bundleDefine, Class<T> clazz);

    <T> T getService(BundleDefine bundleDefine, Class<T> clazz, String version);

    <T> T getService(BundleDefine bundleDefine, String id);

    <T> T getService(BundleDefine bundleDefine, String id, String version);

    int size();

    /**
     * 从指定路径加载插件
     *
     * @param path 插件所在路径，可为目录或者单个jar文件
     */
    void load(String path);

    /**
     * 插件上下文传递接口
     *
     * @return
     */
    BundleContext getBundleContext();

}
