package org.tinygroup.tinydb.exception;

/**
 * 
 * @author renhui
 *
 */
public class TinyDbRuntimeException extends RuntimeException {

	public TinyDbRuntimeException() {
		super();
	}

	public TinyDbRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public TinyDbRuntimeException(String message) {
		super(message);
	}

	public TinyDbRuntimeException(Throwable cause) {
		super(cause);
	}
	
}
