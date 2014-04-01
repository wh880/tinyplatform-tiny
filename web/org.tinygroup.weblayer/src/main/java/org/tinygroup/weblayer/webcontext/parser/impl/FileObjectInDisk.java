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
package org.tinygroup.weblayer.webcontext.parser.impl;

import org.apache.commons.fileupload.FileItem;
import org.tinygroup.commons.tools.FileUtil;
import org.tinygroup.vfs.FileObject;
import org.tinygroup.vfs.impl.AbstractFileObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class FileObjectInDisk extends AbstractFileObject implements ItemFileObject {

    private DiskFileItem fileItem;

    private static final String FILE_PROTOCAL = "file:";

    public FileObjectInDisk(DiskFileItem fileItem) {
        super(null);
        this.fileItem = fileItem;
    }

    public String getFileName() {
        File file = fileItem.getStoreLocation();
        if (file != null) {
            return file.getName();
        }
        return null;
    }

    public String getPath() {
        return "/";
    }

    public String getAbsolutePath() {
        File file = fileItem.getStoreLocation();
        if (file != null) {
            return file.getAbsolutePath();
        }
        return null;
    }

    public String getExtName() {
        File file = fileItem.getStoreLocation();
        if (file != null) {
            return FileUtil.getExtension(file.getName());
        }
        return null;
    }

    public boolean isExist() {
        File file = fileItem.getStoreLocation();
        if (file != null) {
            return file.exists();
        }
        return false;
    }

    public long getSize() {
        return fileItem.getSize();
    }

    public InputStream getInputStream() throws IOException {
        return fileItem.getInputStream();
    }

    public boolean isFolder() {
        File file = fileItem.getStoreLocation();
        if (file != null) {
            return file.isDirectory();
        }
        return false;
    }


    public List<FileObject> getChildren() {
        return null;
    }

    public FileObject getChild(String fileName) {
        return null;
    }

    public long getLastModifiedTime() {
        return 0;
    }


    public boolean isInPackage() {
        return false;
    }

    public URL getURL() {
        try {
            return new URL(FILE_PROTOCAL + getAbsolutePath());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public OutputStream getOutputStream() throws IOException {
            return fileItem.getOutputStream();
    }

    public FileItem getFileItem() {
        return fileItem;
    }

}
