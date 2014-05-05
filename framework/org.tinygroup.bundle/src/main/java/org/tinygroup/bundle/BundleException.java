package org.tinygroup.bundle;

/**
 * BundleException
 * Created by luoguo on 2014/5/4.
 */
public class BundleException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = -6293780078695113755L;

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
