package org.tinygroup.tinypc;

/**
 * Created by luoguo on 2014/5/5.
 */
public class PCRuntimeException extends RuntimeException {
    public PCRuntimeException(String message) {
        super(message);
    }

    public PCRuntimeException(Exception exception) {
        super(exception);
    }

    public PCRuntimeException(String message, Exception e) {
        super(message, e);
    }
}
