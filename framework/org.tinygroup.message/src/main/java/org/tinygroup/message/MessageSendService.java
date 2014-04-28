/**
 *  Copyright (c) 1997-2013, tinygroup.org (luo_guo@live.cn).
 *
 *  Licensed under the GPL, Version 3.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.gnu.org/licenses/gpl.html
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 * --------------------------------------------------------------------------
 *  版权 (c) 1997-2013, tinygroup.org (luo_guo@live.cn).
 *
 *  本开源软件遵循 GPL 3.0 协议;
 *  如果您不遵循此协议，则不被允许使用此文件。
 *  你可以从下面的地址获取完整的协议文本
 *
 *       http://www.gnu.org/licenses/gpl.html
 */
package org.tinygroup.message;

import java.util.Collection;

/**
 * 信息服务，用于定义信息的发送与接收接口
 * Created by luoguo on 2014/4/17.
 */
public interface MessageSendService<Account extends MessageAccount, Sender extends MessageSender, Receiver extends MessageReceiver, Msg extends Message> {


    /**
     * 发送单条消息
     *
     * @param messageSender    消息发送者
     * @param messageReceivers 消息接收者
     * @param message          消息
     */
    void sendMessage(Account messageAccount, Sender messageSender, Collection<Receiver> messageReceivers, Msg message) throws MessageException;


}