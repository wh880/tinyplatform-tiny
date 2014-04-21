package org.tinygroup.message.email;

import org.tinygroup.message.Message;

import java.util.Collection;
import java.util.Date;

/**
 * Created by luoguo on 2014/4/18.
 */
public class EmailReceiveMessage implements Message {
    EmailMessage message;
    Collection<EmailMessageSender> messageSenders;
    Collection<EmailMessageReceiver> messageReceivers;
    Date sendDate;
    Date receiveDate;

    public Collection<EmailMessageSender> getMessageSenders() {
        return messageSenders;
    }

    public void setMessageSenders(Collection<EmailMessageSender> messageSenders) {
        this.messageSenders = messageSenders;
    }

    public EmailMessage getMessage() {
        return message;
    }

    public void setMessage(EmailMessage message) {
        this.message = message;
    }

    public Collection<EmailMessageReceiver> getMessageReceivers() {
        return messageReceivers;
    }

    public void setMessageReceivers(Collection<EmailMessageReceiver> messageReceivers) {
        this.messageReceivers = messageReceivers;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public Date getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }
}
