package org.tinygroup.message.email;

import org.tinygroup.message.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luoguo on 2014/4/17.
 */
public class EmailMessage implements Message {
    String subject;
    String content;
    List<EmailAccessory> accessories;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<EmailAccessory> getAccessories() {
        if (accessories == null) {
            accessories = new ArrayList<EmailAccessory>();
        }

        return accessories;
    }

    public void setAccessories(List<EmailAccessory> accessories) {
        this.accessories = accessories;
    }
}
