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
package org.tinygroup.metadata.fileresolver;

import com.thoughtworks.xstream.XStream;
import org.tinygroup.fileresolver.impl.AbstractFileProcessor;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.metadata.config.constants.Constants;
import org.tinygroup.metadata.constants.ConstantProcessor;
import org.tinygroup.metadata.util.MetadataUtil;
import org.tinygroup.springutil.SpringUtil;
import org.tinygroup.vfs.FileObject;
import org.tinygroup.xstream.XStreamFactory;

import java.io.IOException;

public class ConstantFileResolver extends AbstractFileProcessor {

    private static final String CONSTANT_EXTFILENAME = ".const.xml";

    public boolean isMatch(FileObject fileObject) {
        return fileObject.getFileName().endsWith(CONSTANT_EXTFILENAME);
    }

    public void process() {
        ConstantProcessor constantProcessor = SpringUtil.getBean(MetadataUtil.CONSTANTPROCESSOR_BEAN);
        XStream stream = XStreamFactory.getXStream(MetadataUtil.METADATA_XSTREAM);
        for (FileObject fileObject : deleteList) {
            logger.logMessage(LogLevel.INFO, "正在移除const文件[{0}]", fileObject.getAbsolutePath());
            Constants constants = (Constants) caches.get(fileObject.getAbsolutePath());
            if (constants != null) {
                constantProcessor.removeConstants(constants);
                caches.remove(fileObject.getAbsolutePath());
            }
            logger.logMessage(LogLevel.INFO, "移除const文件[{0}]结束", fileObject.getAbsolutePath());
        }
        for (FileObject fileObject : changeList) {
            logger.logMessage(LogLevel.INFO, "正在加载const文件[{0}]", fileObject.getAbsolutePath());
            Constants oldConstants = (Constants) caches.get(fileObject.getAbsolutePath());
            if (oldConstants != null) {
                constantProcessor.removeConstants(oldConstants);
            }
            Constants constants = null;
            try {
                constants = (Constants) stream.fromXML(fileObject.getInputStream());
                constantProcessor.addConstants(constants);
                caches.put(fileObject.getAbsolutePath(), constants);
                logger.logMessage(LogLevel.INFO, "加载const文件[{0}]结束", fileObject.getAbsolutePath());
            } catch (IOException e) {
                logger.errorMessage("加载const文件[{0}]错误", e, fileObject.getAbsolutePath());
            }
        }
    }

}
