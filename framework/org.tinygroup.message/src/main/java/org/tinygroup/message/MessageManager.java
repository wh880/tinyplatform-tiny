package org.tinygroup.message;

import java.util.Collection;
import java.util.List;

/**
 * Created by luoguo on 2014/4/24.
 */
public interface MessageManager<ACCOUNT extends MessageAccount, SENDER extends MessageSender, RECEIVER extends MessageReceiver, MSG extends Message> {
    void setMessageAccount(ACCOUNT messageAccount);

    void setMessageReceiveService(MessageReceiveService<ACCOUNT, MSG> messageReceiveService);

    void setMessageSendService(MessageSendService<ACCOUNT, SENDER, RECEIVER, MSG> messageSendService);

    /**
     * 设置信息接收处理器列表
     *
     * @param messageReceiveProcessors
     */
    void setMessageReceiveProcessors(List<MessageReceiveProcessor<MSG>> messageReceiveProcessors);

    /**
     * 添加信息接收处理器
     *
     * @param messageReceiveProcessor
     */
    void addMessageReceiveProcessor(MessageReceiveProcessor<MSG> messageReceiveProcessor);

    /**
     * 设置信息发送处理器列表
     *
     * @param messageSendProcessors
     */
    void setMessageSendProcessors(List<MessageSendProcessor<SENDER, RECEIVER, MSG>> messageSendProcessors);

    /**
     * 添加信息发送处理器
     *
     * @param messageSendProcessor
     */
    void addMessageSendProcessor(MessageSendProcessor<SENDER, RECEIVER, MSG> messageSendProcessor);

    Collection<MSG> getMessages() throws MessageException;

    void sendMessage(SENDER messageSender, Collection<RECEIVER> messageReceivers, MSG message) throws MessageException;

}
