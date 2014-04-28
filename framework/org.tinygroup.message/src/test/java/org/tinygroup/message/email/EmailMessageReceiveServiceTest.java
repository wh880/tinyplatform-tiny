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

import org.tinygroup.message.*;

import java.util.Collection;

/**
 * Created by luoguo on 2014/4/18.
 */
public class EmailMessageReceiveServiceTest {
    public static void main(String[] args) throws MessageException {
        MessageManager<EmailMessageAccount, EmailMessageSender, EmailMessageReceiver, EmailReceiveMessage> messageManager = new MessageManagerDefault();
        EmailMessageAccount account = new EmailMessageAccount();
        account.setHost("127.0.0.1");
        account.setUsername("luoguo@tinygroup.org");
        account.setPassword("123456");
        messageManager.setMessageAccount(account);
        EmailMessageReceiveService messageReceiveService = new EmailMessageReceiveService();
        MessageReceiveService receiveService=messageReceiveService;
        MessageSendService messageSendService = new EmailMessageSendService();
        messageManager.setMessageReceiveService(receiveService);
        messageManager.setMessageSendService(messageSendService);
        messageReceiveService.setEmailMessageFlagMarker(new RemoveAllEmailMessageFlagMarker());
        Collection<EmailReceiveMessage> messages = messageManager.getMessages();
        for (EmailReceiveMessage message : messages) {
            System.out.println("subject:" + message.getMessage().getSubject());
            System.out.println("content:" + message.getMessage().getContent());
            System.out.println("附件:" + message.getMessage().getAccessories().size());
            System.out.println("=============================================");
        }
    }
}