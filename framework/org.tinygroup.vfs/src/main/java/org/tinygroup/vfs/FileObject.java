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
package org.tinygroup.vfs;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.List;

public interface FileObject {

    SchemaProvider getSchemaProvider();// 返回模式提供者

    URL getURL();// 返回url

    String getAbsolutePath();// 返回绝对路径

    String getPath();// 返回路径

    String getFileName();// 返回文件名

    String getExtName();// 返回扩展名

    boolean isFolder();// 返回是否是目录，如果是目录，则getInputStream无效。

    boolean isInPackage();// 是否是包文件

    boolean isExist();// 是否存在

    long getLastModifiedTime();// 返回修改时间

    long getSize();// 返回文件大小

    InputStream getInputStream();// 返回输入流

    OutputStream getOutputStream();// 返回输出流

    FileObject getParent();// 返回上级文件

    void setParent(FileObject fileObject);// 设置上级文件

    List<FileObject> getChildren();// 返回下级文件列表

    FileObject getChild(String fileName);// 获取参数名称指定的fileobject

    void foreach(FileObjectFilter fileObjectFilter, FileObjectProcessor fileObjectProcessor, boolean parentFirst);

    void foreach(FileObjectFilter fileObjectFilter, FileObjectProcessor fileObjectProcessor);

}
