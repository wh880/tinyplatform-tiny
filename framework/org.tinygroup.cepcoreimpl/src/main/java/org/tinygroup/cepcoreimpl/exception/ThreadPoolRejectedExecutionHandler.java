package org.tinygroup.cepcoreimpl.exception;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

public class ThreadPoolRejectedExecutionHandler implements
		RejectedExecutionHandler {
	private String message;

	public ThreadPoolRejectedExecutionHandler(String message) {
		super();
		this.message = message;
	}

	public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
		throw new RuntimeException(message);
	}

}
