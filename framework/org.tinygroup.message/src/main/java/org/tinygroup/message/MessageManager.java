package org.tinygroup.message;

import java.util.Collection;
import java.util.List;

/**
 * Created by luoguo on 2014/4/24.
 */
public interface MessageManager<Account extends MessageAccount, Sender extends MessageSender, Receiver extends MessageReceiver, Msg extends Message> {
    void setMessageAccount(Account messageAccount);

    void setMessageReceiveService(MessageReceiveService<Account, Msg> messageReceiveService);

    void setMessageSendService(MessageSendService<Account, Sender, Receiver, Msg> messageSendService);

    /**
     * 设置信息接收处理器列表
     *
     * @param messageReceiveProcessors
     */
    void setMessageReceiveProcessors(List<MessageReceiveProcessor<Msg>> messageReceiveProcessors);

    /**
     * 添加信息接收处理器
     *
     * @param messageReceiveProcessor
     */
    void addMessageReceiveProcessor(MessageReceiveProcessor<Msg> messageReceiveProcessor);

    /**
     * 设置信息发送处理器列表
     *
     * @param messageSendProcessors
     */
    void setMessageSendProcessors(List<MessageSendProcessor<Sender, Receiver, Msg>> messageSendProcessors);

    /**
     * 添加信息发送处理器
     *
     * @param messageSendProcessor
     */
    void addMessageSendProcessor(MessageSendProcessor<Sender, Receiver, Msg> messageSendProcessor);

    Collection<Msg> getMessages() throws MessageException;

    void sendMessage(Sender messageSender, Collection<Receiver> messageReceivers, Msg message) throws MessageException;

}
