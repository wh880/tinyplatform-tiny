package org.tinygroup.cepcoregovernance;

import org.tinygroup.event.Event;
import org.tinygroup.exceptionhandler.ExceptionHandler;

public class ServiceExceptionHanlder implements ExceptionHandler<Exception>{

	public void handle(Exception t, Event event) {
		CommonServiceExecuteContainer.addExecuteException(event,t);
	}

}
