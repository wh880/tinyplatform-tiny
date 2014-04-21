package org.tinygroup.message.email;

import junit.framework.TestCase;

import java.util.Collection;

/**
 * Created by luoguo on 2014/4/18.
 */
public class EmailMessageReceiveServiceTest extends TestCase {
    public void testGetMessages() throws Exception {
        EmailMessageAccount account = new EmailMessageAccount();
        account.setHost("mail.tinygroup.org");
        account.setUsername("luoguo");
        account.setPassword("123456");
        EmailMessageReceiveService service = new EmailMessageReceiveService(account);
        Collection<EmailReceiveMessage> messages = service.getMessages();
        for (EmailReceiveMessage message : messages) {
            System.out.println("subject:" + message.getMessage().getSubject());
            System.out.println("content:" + message.getMessage().getContent());
            System.out.println("附件:" + message.getMessage().getAccessories().size());
            System.out.println("=============================================");
        }
    }
}
