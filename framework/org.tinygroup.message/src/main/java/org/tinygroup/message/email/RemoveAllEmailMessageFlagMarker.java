package org.tinygroup.message.email;

import javax.mail.Flags;
import javax.mail.Message;

/**
 * 删除所有接收到的邮件
 * Created by luoguo on 2014/4/23.
 */
public class RemoveAllEmailMessageFlagMarker implements EmailMessageFlagMarker {
    public Flags.Flag getFlag(EmailReceiveMessage receiveMessage, Message message) {
        return Flags.Flag.DELETED;
    }
}
