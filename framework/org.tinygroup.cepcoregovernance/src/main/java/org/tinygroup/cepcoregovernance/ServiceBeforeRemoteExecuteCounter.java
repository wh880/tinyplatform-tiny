package org.tinygroup.cepcoregovernance;

import org.tinygroup.cepcore.aop.CEPCoreAopAdapter;
import org.tinygroup.event.Event;

public class ServiceBeforeRemoteExecuteCounter implements CEPCoreAopAdapter{

	public void handle(Event event) {
		CommonServiceExecuteContainer.addRemoteExecuteBefore(event);
	}

}
