package org.tinygroup.message;

import java.util.Collection;

/**
 * 信息服务，用于定义信息的发送与接收接口
 * Created by luoguo on 2014/4/17.
 */
public interface MessageSendService<Account extends MessageAccount, Sender extends MessageSender, Receiver extends MessageReceiver, Msg extends Message> {

    /**
     * 设置消息发送者
     *
     * @param messageAccount
     */
    void setMessageAccount(Account messageAccount);

    /**
     * 发送单条消息
     *
     * @param messageReceivers
     * @param message
     */
    void sendMessage(Sender messageSender, Collection<Receiver> messageReceivers, Msg message) throws MessageException;



}
