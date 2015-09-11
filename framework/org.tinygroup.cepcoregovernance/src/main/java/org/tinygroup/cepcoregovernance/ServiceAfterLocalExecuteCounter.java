package org.tinygroup.cepcoregovernance;

import org.tinygroup.cepcore.aop.CEPCoreAopAdapter;
import org.tinygroup.event.Event;

public class ServiceAfterLocalExecuteCounter implements CEPCoreAopAdapter{

	public void handle(Event event) {
		CommonServiceExecuteContainer.addLocalExecuteAfter(event);
	}

}
