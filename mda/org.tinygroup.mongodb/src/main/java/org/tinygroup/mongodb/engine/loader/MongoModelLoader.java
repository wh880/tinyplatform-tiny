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
package org.tinygroup.mongodb.engine.loader;

import com.thoughtworks.xstream.XStream;
import org.tinygroup.imda.ModelLoader;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.mongodb.model.MongoDBModel;
import org.tinygroup.vfs.FileObject;
import org.tinygroup.xstream.XStreamFactory;

import java.io.IOException;

/**
 * 功能说明:mongo模型加载对象
 * <p/>
 * 开发人员: renhui <br>
 * 开发时间: 2013-11-25 <br>
 * <br>
 */
public class MongoModelLoader implements ModelLoader {

    private static Logger logger = LoggerFactory.getLogger(MongoModelLoader.class);

    public String getExtFileName() {
        return ".mongomodel.xml";
    }

    public Object loadModel(FileObject fileObject) {
        logger.logMessage(LogLevel.INFO, "正在加载mongodb模型文件<{}>", fileObject.getAbsolutePath());
        XStream xstream = XStreamFactory.getXStream("mongodb");
        MongoDBModel model = null;
        try {
            model = (MongoDBModel) xstream.fromXML(fileObject.getInputStream());
            logger.logMessage(LogLevel.INFO, "mongodb模型文件<{}>加载完毕。", fileObject.getAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return model;
    }

}
