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
package org.tinygroup.bundle.fileresolve;

import com.thoughtworks.xstream.XStream;
import org.tinygroup.bundle.BundleLoader;
import org.tinygroup.bundle.BundleManager;
import org.tinygroup.bundle.config.BundleDefine;
import org.tinygroup.fileresolver.impl.AbstractFileProcessor;
import org.tinygroup.vfs.FileObject;
import org.tinygroup.xstream.XStreamFactory;

/**
 * 放在工程目录中的插件加载器
 *
 * @author luoguo
 */
public class BundleFileProcessor extends AbstractFileProcessor {
    private BundleManager bundleManager;

    public BundleManager getBundleManager() {
        return bundleManager;
    }

    public void setBundleManager(BundleManager bundleManager) {
        this.bundleManager = bundleManager;
    }

    public boolean isMatch(FileObject fileObject) {
        if (fileObject.getFileName().endsWith(BundleLoader.BUNDLE_FILENAME)) {
            return true;
        }
        return false;
    }


    public void process() {
        process(this.getClass().getClassLoader());

    }

    public void process(ClassLoader loader) {
        XStream stream = XStreamFactory.getXStream(BundleManager.BUNDLE_XSTREAM);
        for (FileObject fileObject : fileObjects) {
            BundleDefine bundleDefine = (BundleDefine) stream.fromXML(fileObject.getInputStream());
            bundleManager.add(bundleDefine);
        }
    }
}
