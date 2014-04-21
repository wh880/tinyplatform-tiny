package org.tinygroup.message.email;

import org.tinygroup.message.MessageSender;

/**
 * Created by luoguo on 2014/4/17.
 */
public class EmailMessageSender implements MessageSender {
    String email;
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


}
