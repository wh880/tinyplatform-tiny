package org.tinygroup.cepcoregovernance;

import org.tinygroup.cepcore.aop.CEPCoreAopAdapter;
import org.tinygroup.event.Event;

public class ServiceBeforeLocalExecuteCounter implements CEPCoreAopAdapter{

	public void handle(Event event) {
		CommonServiceExecuteContainer.addLocalExecuteBefore(event);
	}

}
