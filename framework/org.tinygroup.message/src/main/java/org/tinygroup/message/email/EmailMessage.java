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

import org.tinygroup.message.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * 邮件消息，这里的邮件消息仅包含主题，内容及附件
 * Created by luoguo on 2014/4/17.
 */
public class EmailMessage implements Message {
    String subject;
    String content;
    List<EmailAccessory> accessories;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<EmailAccessory> getAccessories() {
        if (accessories == null) {
            accessories = new ArrayList<EmailAccessory>();
        }

        return accessories;
    }

    public void setAccessories(List<EmailAccessory> accessories) {
        this.accessories = accessories;
    }
}
