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

import javax.activation.DataHandler;
import javax.activation.MimetypesFileTypeMap;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.*;
import javax.mail.util.ByteArrayDataSource;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 邮件发送服务
 * Created by luoguo on 2014/4/17.
 */
public class EmailMessageSendService implements MessageSendService<EmailMessageAccount, EmailMessageSender, EmailMessageReceiver, EmailMessage> {
    private EmailMessageAccount messageAccount;
    private Session session;
    private List<MessageProcessor> messageProcessors;

    public void setMessageProcessors(List<MessageProcessor> messageProcessors) {
        this.messageProcessors = messageProcessors;
    }

    public void addMessageProcessor(MessageProcessor messageProcessor) {
        if (messageProcessors == null) {
            messageProcessors = new ArrayList<MessageProcessor>();
        }
        messageProcessors.add(messageProcessor);
    }


    public EmailMessageSendService() {

    }

    public EmailMessageSendService(EmailMessageAccount messageAccount) {
        setMessageAccount(messageAccount);
    }

    public void setMessageAccount(EmailMessageAccount messageAccount) {
        this.messageAccount = messageAccount;
    }

    public void sendMessage(EmailMessageSender messageSender, Collection<EmailMessageReceiver> messageReceivers, EmailMessage emailMessage) throws MessageException {
        if (session == null) {
            session = EmailMessageUtil.getSession(messageAccount);
        }
        javax.mail.Message message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(encode(messageSender.getAddress())));
            for (EmailMessageReceiver receiver : messageReceivers) {
                message.setRecipients(receiver.getType(),
                        InternetAddress.parse(encode(receiver.getAddress())));
            }
            message.setSubject(emailMessage.getSubject());
            Multipart multipart = new MimeMultipart();
            MimeBodyPart part = new MimeBodyPart();
            multipart.addBodyPart(part);
            part.setContent(emailMessage.getContent(), "text/html;\n\tcharset=\"UTF-8\"");
            processAccessory(emailMessage, multipart);
            message.setContent(multipart);
            Transport.send(message);
            for (EmailMessageReceiver receiver : messageReceivers) {
                executeMessageProcessor(messageSender, receiver, emailMessage);
            }
        } catch (javax.mail.MessagingException e) {
            throw new MessageException(e);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void processAccessory(EmailMessage emailMessage, Multipart multipart) throws MessagingException {
        if (emailMessage.getAccessories() != null) {
            for (EmailAccessory accessory : emailMessage.getAccessories()) {
                MimeBodyPart mimeBodyPart = new MimeBodyPart();
                ByteArrayDataSource dataSource = new ByteArrayDataSource(accessory.getContent(), MimetypesFileTypeMap.getDefaultFileTypeMap().getContentType(accessory.getFileName()));
                mimeBodyPart.setFileName(accessory.getFileName());
                mimeBodyPart.setDataHandler(new DataHandler(dataSource));
                multipart.addBodyPart(mimeBodyPart);
            }
        }
    }

    private void executeMessageProcessor(MessageSender messageSender, MessageReceiver messageReceiver, Message message) {
        if (messageProcessors != null) {
            for (MessageProcessor processor : messageProcessors) {
                if (processor.isMatch(messageSender, messageReceiver, message)) {
                    processor.processMessage(messageSender, messageReceiver, message);
                }
            }
        }
    }

    private String encode(String address) throws UnsupportedEncodingException {
        return MimeUtility.encodeText(address);
    }
}


