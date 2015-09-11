package org.tinygroup.cepcoregovernance;

import org.tinygroup.cepcore.aop.CEPCoreAopAdapter;
import org.tinygroup.event.Event;

public class ServiceAfterRemoteExecuteCounter implements CEPCoreAopAdapter{

	public void handle(Event event) {
		CommonServiceExecuteContainer.addRemoteExecuteAfter(event);
	}

}
