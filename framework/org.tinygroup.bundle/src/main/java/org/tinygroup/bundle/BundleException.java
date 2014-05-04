package org.tinygroup.bundle;

/**
 * BundleException
 * Created by luoguo on 2014/5/4.
 */
public class BundleException extends Exception {
    public BundleException(String message) {
        super(message);
    }
    public BundleException(Throwable throwable) {
        super(throwable);
    }

    public BundleException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
