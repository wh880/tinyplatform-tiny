package org.tinygroup.dbrouter.exception;

/**
 * dbrouter封装的运行期异常
 * @author renhui
 *
 */
public class DbrouterRuntimeException extends RuntimeException {

	public DbrouterRuntimeException() {
		super();
	}

	public DbrouterRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public DbrouterRuntimeException(String message) {
		super(message);
	}

	public DbrouterRuntimeException(Throwable cause) {
		super(cause);
	}
}
