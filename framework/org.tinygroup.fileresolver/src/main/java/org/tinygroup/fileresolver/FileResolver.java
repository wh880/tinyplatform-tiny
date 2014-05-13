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
package org.tinygroup.fileresolver;

import org.tinygroup.config.Configuration;
import org.tinygroup.vfs.FileObject;

import java.util.List;

/**
 * 文件查找器
 *
 * @author luoguo
 */
public interface FileResolver extends Configuration {

    String BEAN_NAME = "fileResolver";

    /**
     * 返回所有的文件处理器
     *
     * @return
     */
    List<FileProcessor> getFileProcessorList();


    /**
     * 手工添加扫描的匹配列表，如果有包含列表，则按包含列表
     *
     * @param pattern
     */
    void addIncludePathPattern(String pattern);

    /**
     * 添加扫描的路径
     *
     * @param fileObject
     */
    void addResolveFileObject(FileObject fileObject);

    void addResolvePath(String path);

    /**
     * 增加文件处理器
     *
     * @param fileProcessor
     */
    void addFileProcessor(FileProcessor fileProcessor);

    void setClassLoader(ClassLoader classLoader);

    /**
     * 开始找文件
     */
    void resolve();


    /**
     * 获取文件处理的线程数目
     *
     * @return
     */
    int getFileProcessorThreadNumber();

    /**
     * 设置文件处理的线程数目
     *
     * @param threadNum
     */
    void setFileProcessorThreadNumber(int threadNum);
}
