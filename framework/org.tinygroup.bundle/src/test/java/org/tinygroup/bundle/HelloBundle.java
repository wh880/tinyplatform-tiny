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

import org.tinygroup.bundle.Bundle;
import org.tinygroup.bundle.BundleContext;
import org.tinygroup.bundle.BundleManager;
import org.tinygroup.bundle.config.BundleDefine;
import org.tinygroup.bundle.exception.BundleException;

public class HelloBundle implements Bundle {

    public void init(BundleContext bundleContext) throws BundleException {

    }

    public void start(BundleContext bundleContext) throws BundleException {

    }

    public void pause(BundleContext bundleContext) throws BundleException {

    }

    public void stop(BundleContext bundleContext) throws BundleException {

    }

    public void destroy(BundleContext bundleContext) throws BundleException {

    }

    public <T> T getService(String id, String version) {
        if (id.equals("hello")) {
            return (T) new Hello(version);
        }
        throw new BundleException("请求的服务ID不存在");
    }

    public <T> T getService(String id) {
        return (T) getService(id, "1.0");
    }

    public <T> T getService(Class<T> clazz, String version) {
        if (Hello.class.isAssignableFrom(clazz)) {
            return (T) new Hello(version);
        }
        throw new BundleException("请求的服务ID不存在");
    }

    public <T> T getService(Class<T> clazz) {
        return (T) getService(clazz, "1.0");
    }

    public <T> void setService(T service, Class<T> clazz) {


    }


    public void setBundleManager(BundleManager bundleManager) {


    }

    public void setBundleDefine(BundleDefine bundleDefine) {

    }


}
