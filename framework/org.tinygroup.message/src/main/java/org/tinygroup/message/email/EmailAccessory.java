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
package org.tinygroup.message.email;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * 邮件的附件
 * Created by luoguo on 2014/4/18.
 */
public class EmailAccessory {
    public EmailAccessory() {

    }

    public EmailAccessory(File file) throws IOException {
        this.fileName = file.getName();
        FileInputStream inputStream = new FileInputStream(file);
        content = new byte[(int) file.length()];
        inputStream.read(content);
        inputStream.close();
    }

    public EmailAccessory(String fileName, byte[] content) {
        setFileName(fileName);
        setContent(content);
    }

    String fileName;
    byte[] content;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
