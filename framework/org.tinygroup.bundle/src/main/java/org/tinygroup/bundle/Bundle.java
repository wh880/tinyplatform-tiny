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

/**
 * Bundle接口，所有的Bundle都必须实现此接口
 *
 * @author luoguo
 */
public interface Bundle {

    /**
     * 一开始都是未初始化的，执行初始化的时候，进入STATUS_INITING状态，在初始化结束后，
     * 开始进行依赖注入，在依赖注入完成后，变成STATUS_INITED状态
     * 此时，可以调用start方法启动服务，start仅用来修改状态，启动过程是STATUS_STARTING状态，
     * 启动结束后，变成STATUS_READY状态，此时可以对外提供服务
     * 如果在STATUS_READY状态，可以执行pause方法暂停服务，在暂停过程中，状态为STATUS_PAUSING
     * 暂停完成后变成STATUS_PAUSED，处于暂停状态时，可以再调用start启动，状态随之变成STATUS_STARTING，
     * 启动结束后变成STATUS_READY
     * 在任何时候调用stop方法，都会停止服务，停止某一服务提供者时，会同时停止依赖它的服务提供者。
     * 在执行停止后，会同时执行clear方法。
     */
    /**
     * 初始化
     */
    void init(BundleContext context);

    /**
     * 开始
     *
     * @param context
     */
    void start(BundleContext context);

    /**
     * 暂停
     *
     * @param context
     */
    void pause(BundleContext context);

    /**
     * 停止
     *
     * @param context
     */
    void stop(BundleContext context);

    /**
     * 销毁
     *
     * @param context
     */
    void destroy(BundleContext context);

    /**
     * 返回指定版本的指定ID的服务实类
     *
     * @param id
     * @param version
     * @return
     */
    <T> T getService(String id, String version);

    /**
     * 取回最高版本的服务实现
     *
     * @param id
     * @return
     */
    <T> T getService(String id);

    /**
     * 返回指类型指定版本的服务实例
     *
     * @param clazz
     * @param version
     * @return
     */
    <T> T getService(Class<T> clazz, String version);

    /**
     * 返回指类型指定版本的服务实例
     *
     * @param clazz
     * @return
     */
    <T> T getService(Class<T> clazz);

    /**
     * 注入插件管理器
     *
     * @param bundleManager
     */
    void setBundleManager(BundleManager bundleManager);

    /**
     * 设置Bundle定义
     *
     * @param bundleDefine
     */
    void setBundleDefine(BundleDefine bundleDefine);

    /**
     * 设置服务实例
     */
    <T> void setService(T service, Class<T> clazz);


}
