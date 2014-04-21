package org.tinygroup.message.email;

import org.tinygroup.message.MessageException;
import org.tinygroup.message.MessageReceiveService;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by luoguo on 2014/4/18.
 */
public class EmailMessageReceiveService implements MessageReceiveService<EmailMessageAccount, EmailReceiveMessage> {
    private EmailMessageAccount messageAccount;
    private Session session;

    public EmailMessageReceiveService() {

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
            Store store = null;
            store = session.getStore("pop3");
            store.connect(messageAccount.getHost(), messageAccount.getUsername(), messageAccount.getPassword());
            Folder folder = store.getFolder("inbox");
            folder.open(Folder.READ_ONLY);
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
                System.out.println();
            }

            folder.close(true);
            store.close();
            return receiveMessages;
        } catch (Exception e) {
            throw new MessageException(e);
        }

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


    static String getCharset(String contentType) {
        if (contentType != null) {
            String[] valuePairs = contentType.split(";");
            for (String str : valuePairs) {
                str = str.trim();
                if (str.startsWith("charset=")) {
                    return str.substring(str.indexOf('=') + 1).replaceAll("[\"]", "");
                }
            }
        }
        return null;
    }

}
