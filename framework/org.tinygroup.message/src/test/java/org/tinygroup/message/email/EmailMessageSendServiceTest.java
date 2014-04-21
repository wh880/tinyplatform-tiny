package org.tinygroup.message.email;

import junit.framework.TestCase;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by luoguo on 2014/4/21.
 */
public class EmailMessageSendServiceTest extends TestCase {
    public void testSendMessage() throws Exception {
        EmailMessageAccount account = new EmailMessageAccount();
        account.setHost("mail.tinygroup.org");
        account.setUsername("luoguo");
        account.setPassword("123456");
        EmailMessageSendService sendService=new EmailMessageSendService();
        EmailMessageSender messageSender=new EmailMessageSender();
        messageSender.setDisplayName("罗果");
        messageSender.setEmail("luoguo@tinygroup.org");
        EmailMessageReceiver messageReceiver=new EmailMessageReceiver();
        messageReceiver.setDisplayName("罗果");
        messageReceiver.setEmail("luog@tinygroup.org");
        EmailMessage emailMessage=new EmailMessage();
        emailMessage.setSubject("test1111111");
        emailMessage.setContent("中华人民共和国");
        EmailAccessory accessory=new EmailAccessory(new File("D:/RUNNING.txt"));
        emailMessage.getAccessories().add(accessory);
        sendService.setMessageAccount(account);
        Collection<EmailMessageReceiver>receivers=new ArrayList<EmailMessageReceiver>();
        receivers.add(messageReceiver);
        sendService.sendMessage(messageSender,receivers,emailMessage);
    }


}
