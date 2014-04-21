package org.tinygroup.message;

/**
 * Created by luoguo on 2014/4/18.
 */
public class MessageException extends Exception {
    public MessageException() {

    }

    public MessageException(String message) {
        super(message);
    }

    public MessageException(Exception e) {
        super(e);
    }

    public MessageException(String message, Exception e) {
        super(message, e);
    }
}
