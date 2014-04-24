package org.tinygroup.message;


/**
 * 信息处理器
 * Created by luoguo on 2014/4/23.
 */
public interface MessageReceiveProcessor< Msg extends Message> {
    /**
     * 返回是否是匹配的消息
     *
     * @param message
     * @return
     */
    boolean isMatch(Msg message);

    /**
     * 进行处理
     *
     * @param message
     */
    void processMessage(Msg message);
}
