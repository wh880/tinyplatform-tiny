package org.tinygroup.cepcoreimpl.test;

import java.util.ArrayList;

import junit.framework.TestCase;

import org.tinygroup.beancontainer.BeanContainerFactory;
import org.tinygroup.cepcore.CEPCore;
import org.tinygroup.context.Context;
import org.tinygroup.context.impl.ContextImpl;
import org.tinygroup.event.Event;
import org.tinygroup.tinyrunner.Runner;

public abstract class CEPCoreBaseTestCase extends TestCase {
	private CEPCore core;
	

	public CEPCore getCore() {
		return core;
	}

	protected Event getEvent(String serviceId) {
		Context context = new ContextImpl();
		Event e = Event.createEvent(serviceId, context);
		return e;
	}

	protected ServiceInfoForTest initServiceInfo(String serviceId) {
		ServiceInfoForTest sifft = new ServiceInfoForTest();
		sifft.setServiceId(serviceId);
		return sifft;
	}

	public void setUp() {
		Runner.init("application.xml", new ArrayList<String>());
		core = BeanContainerFactory.getBeanContainer(
				this.getClass().getClassLoader())
				.getBean(CEPCore.CEP_CORE_BEAN);
	}
	
	
}
