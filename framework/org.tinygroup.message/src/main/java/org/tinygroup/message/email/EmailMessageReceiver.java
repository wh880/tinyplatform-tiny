package org.tinygroup.message.email;

import org.tinygroup.message.MessageReceiver;

import javax.mail.Message;

/**
 * Created by luoguo on 2014/4/17.
 */
public class EmailMessageReceiver implements MessageReceiver {
    String email;
    Message.RecipientType type=Message.RecipientType.TO;
    String displayName;

    public String getDisplayName() {
        return displayName;
    }

    public String getAddress() {
        if (displayName == null) {
            return email;
        }
        return displayName + "<" + email + ">";
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Message.RecipientType getType() {
        return type;
    }

    public void setType(Message.RecipientType type) {
        this.type = type;
    }
}
