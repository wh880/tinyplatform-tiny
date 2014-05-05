package org.tinygroup.ini;

/**
 * Created by luoguo on 2014/5/5.
 */
public class IniException extends Exception {
    public IniException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public IniException(String message) {
        super(message);
    }

    public IniException(Throwable throwable) {
        super(throwable);
    }
}
