package org.tinygroup.httpvisit;

/**
 * Created by luoguo on 2014/5/5.
 */
public class HttpVisitRuntimeException extends RuntimeException {
    public HttpVisitRuntimeException(String message) {
        super(message);
    }

    public HttpVisitRuntimeException(Exception exception) {
        super(exception);
    }

    public HttpVisitRuntimeException(String message, Exception e) {
        super(message, e);
    }
}
