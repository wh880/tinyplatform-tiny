package org.tinygroup.message.email;

import javax.mail.Flags;
import javax.mail.Message;

/**
 * Created by luoguo on 2014/4/23.
 */
public interface EmailMessageFlagMarker {
    Flags.Flag getFlag(EmailReceiveMessage receiveMessage,Message message);
}
