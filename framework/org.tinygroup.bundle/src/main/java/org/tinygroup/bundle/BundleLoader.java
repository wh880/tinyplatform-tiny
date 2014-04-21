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
import org.tinygroup.fileresolver.FileProcessor;

import java.net.URL;
import java.util.List;

public interface BundleLoader {
    static final String BUNDLE_FOLDER = "bundles";
    static final String BUNDLE_FILENAME = "bundles.xml";

    List<BundleDefine> load(String path);

    /**
     * 获取指定插件的ClassLoader
     *
     * @param bundleDefine
     * @return
     */
    ClassLoader getLoader(BundleDefine bundleDefine);

    /**
     * 获取插件外部包所在的ClassLoader，该Loader也是所有插件的ClassLoader的父Loader
     *
     * @return
     */
    ClassLoader getPublicLoader();

    /**
     * 获取指定插件的资源扫描FileProcessor
     *
     * @param bundleDefine
     * @return
     */
    List<FileProcessor> getResourceProcessors(BundleDefine bundleDefine);

    /**
     * 获取指定插件的资源移除FileProcessor
     *
     * @param bundleDefine
     * @return
     */
    List<FileProcessor> getRemoveResourceProcessors(BundleDefine bundleDefine);

    /**
     * 删除指定插件的ClassLoader及相关Jar信息
     *
     * @param bundleDefine
     */
    void remove(BundleDefine bundleDefine);

    void add(BundleDefine bundleDefine);

    List<BundleDefine> listJar(String path);

    void resolveResource(ClassLoader loader, URL urls1[]);

    void deResolveResource(ClassLoader loader, URL urls[]);
}
