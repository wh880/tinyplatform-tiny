package org.tinygroup.message;

import java.util.Collection;

/**
 * 信息服务，用于定义信息的发送与接收接口
 * Created by luoguo on 2014/4/17.
 */
public interface MessageReceiveService<Account extends MessageAccount, Msg extends Message> {
    /**
     * 设置消息发送者
     *
     * @param messageAccount
     */
    void setMessageAccount(Account messageAccount);

    Collection<Msg> getMessages() throws MessageException;
}
