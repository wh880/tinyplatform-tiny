package org.tinygroup.convert;

/**
 * Created by luoguo on 2014/5/7.
 */
public class ConvertException extends Exception {
    public ConvertException(String message, Throwable e) {
        super(message, e);
    }

    public ConvertException(Throwable e) {
        super(e);
    }

    public ConvertException(String message) {
        super(message);
    }
}
