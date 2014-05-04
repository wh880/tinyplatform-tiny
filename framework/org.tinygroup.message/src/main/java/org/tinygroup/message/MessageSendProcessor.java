package org.tinygroup.message;


/**
 * 信息处理器
 * Created by luoguo on 2014/4/23.
 */
public interface MessageSendProcessor<SENDER extends MessageSender, RECEIVER extends MessageReceiver, MSG extends Message> {
    /**
     * 返回是否是匹配的消息
     *
     * @param message
     * @return
     */
    boolean isMatch(SENDER messageSender, RECEIVER messageReceiver, MSG message);

    /**
     * 进行处理
     *
     * @param message
     */
    void processMessage(SENDER messageSender, RECEIVER messageReceiver, MSG message);
}
