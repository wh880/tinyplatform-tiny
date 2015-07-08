package org.tinygroup.cepcoreimpl.test;

import org.tinygroup.event.Event;
import org.tinygroup.exceptionhandler.ExceptionHandler;

public class NullPointHandler implements ExceptionHandler<NullPointerException>{

	public void handle(NullPointerException t, Event event) {
		System.out.println("NullPointerException");
		throw new NullPointerException("testExceptionhandler");
	}

}
