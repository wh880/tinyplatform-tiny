package org.tinygroup.message.email;

import org.tinygroup.message.MessageException;
import org.tinygroup.message.MessageSendService;

import javax.activation.DataHandler;
import javax.activation.MimetypesFileTypeMap;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.*;
import javax.mail.util.ByteArrayDataSource;
import java.io.UnsupportedEncodingException;
import java.util.Collection;

/**
 * Created by luoguo on 2014/4/17.
 */
public class EmailMessageSendService implements MessageSendService<EmailMessageAccount, EmailMessageSender, EmailMessageReceiver, EmailMessage> {
    private EmailMessageAccount messageAccount;
    private Session session;


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
            if (emailMessage.getAccessories() != null) {
                for (EmailAccessory accessory : emailMessage.getAccessories()) {
                    MimeBodyPart mimeBodyPart = new MimeBodyPart();
                    ByteArrayDataSource dataSource = new ByteArrayDataSource(accessory.getContent(), MimetypesFileTypeMap.getDefaultFileTypeMap().getContentType(accessory.getFileName()));
                    mimeBodyPart.setFileName(accessory.getFileName());
                    mimeBodyPart.setDataHandler(new DataHandler(dataSource));
                    multipart.addBodyPart(mimeBodyPart);
                }
            }
            message.setContent(multipart);
            Transport.send(message);
        } catch (javax.mail.MessagingException e) {
            throw new MessageException(e);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private String encode(String address) throws UnsupportedEncodingException {
        return MimeUtility.encodeText(address);
    }
}


