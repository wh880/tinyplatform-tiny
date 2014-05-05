package org.tinygroup.vfs;

/**
 * Created by luoguo on 2014/5/5.
 */
public class VFSRuntimeException extends RuntimeException {
    public VFSRuntimeException(String message) {
        super(message);
    }

    public VFSRuntimeException(Exception exception) {
        super(exception);
    }

    public VFSRuntimeException(String message, Exception e) {
        super(message, e);
    }
}
