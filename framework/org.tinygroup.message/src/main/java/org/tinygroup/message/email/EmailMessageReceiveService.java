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

import javax.mail.*;
import javax.mail.Message;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 邮件接收服务
 * Created by luoguo on 2014/4/18.
 */
public class EmailMessageReceiveService implements MessageReceiveService<EmailMessageAccount, EmailReceiveMessage> {
    private EmailMessageAccount messageAccount;
    private Session session;
    private EmailMessageFlagMarker emailMessageFlagMarker;
    private List<MessageProcessor> messageProcessors;

    public EmailMessageFlagMarker getEmailMessageFlagMarker() {
        return emailMessageFlagMarker;
    }

    public void setEmailMessageFlagMarker(EmailMessageFlagMarker emailMessageFlagMarker) {
        this.emailMessageFlagMarker = emailMessageFlagMarker;
    }

    public EmailMessageReceiveService(EmailMessageAccount account) {
        this.messageAccount = account;
    }

    public void setMessageAccount(EmailMessageAccount messageAccount) {
        this.messageAccount = messageAccount;
    }

    public Collection<EmailReceiveMessage> getMessages() throws MessageException {

        try {
            if (session == null) {
                session = EmailMessageUtil.getSession(messageAccount);
            }
            Store store = session.getStore("pop3");
            store.connect(messageAccount.getHost(), messageAccount.getUsername(), messageAccount.getPassword());
            Folder folder = store.getFolder("inbox");
            folder.open(Folder.READ_WRITE);
            Message[] messages = folder.getMessages();
            List<EmailReceiveMessage> receiveMessages = new ArrayList<EmailReceiveMessage>();
            for (int i = 0; i < messages.length; i++) {
                EmailReceiveMessage message = new EmailReceiveMessage();
                receiveMessages.add(message);
                message.setMessage(getEmailMessage(messages[i]));
                message.setMessageSenders(getMessageSender(messages[i]));
                message.setReceiveDate(messages[i].getReceivedDate());
                message.setSendDate(messages[i].getSentDate());
                message.setMessageReceivers(getMessageReceivers(messages[i]));
                if (emailMessageFlagMarker != null) {
                    messages[i].setFlag(emailMessageFlagMarker.getFlag(message, messages[i]), true);
                }
            }
            folder.close(true);
            store.close();
            executeMessageProcess(receiveMessages);
            return receiveMessages;
        } catch (Exception e) {
            throw new MessageException(e);
        }

    }

    private void executeMessageProcess(List<EmailReceiveMessage> receiveMessages) {
        if (messageProcessors != null) {
            for (EmailReceiveMessage receiveMessage : receiveMessages) {
                for (MessageSender messageSender : receiveMessage.getMessageSenders()) {
                    for (MessageReceiver messageReceiver : receiveMessage.getMessageReceivers()) {
                        for (MessageProcessor processor : messageProcessors) {
                            if (processor.isMatch(messageSender,messageReceiver,receiveMessage.getMessage())) {
                                processor.processMessage(messageSender,messageReceiver,receiveMessage.getMessage());
                            }
                        }
                    }
                }
            }
        }
    }

    public void setMessageProcessors(List<MessageProcessor> messageProcessors) {
        this.messageProcessors = messageProcessors;
    }

    public void addMessageProcessor(MessageProcessor messageProcessor) {
        if (messageProcessors == null) {
            messageProcessors = new ArrayList<MessageProcessor>();
        }
        messageProcessors.add(messageProcessor);
    }

    private Collection<EmailMessageReceiver> getMessageReceivers(Message message) throws MessagingException {
        List<EmailMessageReceiver> receivers = new ArrayList<EmailMessageReceiver>();

        addReceivers(receivers, Message.RecipientType.TO, message.getReplyTo());
        addReceivers(receivers, Message.RecipientType.TO, message.getRecipients(Message.RecipientType.TO));
        addReceivers(receivers, Message.RecipientType.CC, message.getRecipients(Message.RecipientType.CC));
        addReceivers(receivers, Message.RecipientType.BCC, message.getRecipients(Message.RecipientType.BCC));
        return receivers;
    }

    private void addReceivers(List<EmailMessageReceiver> receivers, Message.RecipientType type, Address[] replyTo) throws AddressException {
        if (replyTo != null) {
            for (Address address : replyTo) {
                InternetAddress internetAddress = new InternetAddress(address.toString());
                EmailMessageReceiver messageReceiver = new EmailMessageReceiver();
                receivers.add(messageReceiver);
                messageReceiver.setEmail(internetAddress.getAddress());
                messageReceiver.setDisplayName(internetAddress.getPersonal());
                messageReceiver.setType(type);
            }
        }
    }

    private Collection<EmailMessageSender> getMessageSender(Message message) throws MessagingException, IOException {
        Address[] addresses = message.getFrom();
        List<EmailMessageSender> messageSenders = new ArrayList<EmailMessageSender>();
        for (Address address : addresses) {
            InternetAddress internetAddress = new InternetAddress(address.toString());
            EmailMessageSender messageSender = new EmailMessageSender();
            messageSenders.add(messageSender);
            messageSender.setEmail(internetAddress.getAddress());
            messageSender.setDisplayName(internetAddress.getPersonal());
        }
        return messageSenders;
    }

    private EmailMessage getEmailMessage(Message message) throws MessagingException, IOException {
        EmailMessage emailMessage = new EmailMessage();
        emailMessage.setSubject(message.getSubject());
        getContent(emailMessage, (Part) message);
        return emailMessage;
    }

    private void getContent(EmailMessage emailMessage, Part part) throws MessagingException, IOException {
        String contentType = part.getContentType();
        int nameIndex = contentType.indexOf("name");
        boolean hasContentName = false;
        if (nameIndex != -1) {
            hasContentName = true;
        }
        if (hasContentName) {
            BodyPart bodyPart = (BodyPart) part;
            String disposition = bodyPart.getDisposition();
            if ((disposition != null)
                    && ((disposition.equals(Part.ATTACHMENT)) || (disposition
                    .equals(Part.INLINE)))) {
                EmailAccessory accessory = new EmailAccessory();
                accessory.setFileName(part.getFileName());
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                part.writeTo(outputStream);
                accessory.setContent(outputStream.toByteArray());
                emailMessage.getAccessories().add(accessory);
            } else {
                System.out.println("====");
            }
        } else if ((part.isMimeType("text/plain") || part.isMimeType("text/html"))) {
            emailMessage.setContent(part.getContent().toString());
        } else if (part.isMimeType("multipart/*")) {
            Multipart multipart = (Multipart) part.getContent();
            int counts = multipart.getCount();
            for (int i = 0; i < counts; i++) {
                getContent(emailMessage, multipart.getBodyPart(i));
            }
        } else if (part.isMimeType("message/rfc822")) {
            getContent(emailMessage, (Part) part.getContent());
        } else {
            System.out.println(contentType);
        }
    }
}
