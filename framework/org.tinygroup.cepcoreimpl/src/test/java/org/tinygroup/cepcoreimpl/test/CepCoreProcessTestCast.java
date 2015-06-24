package org.tinygroup.cepcoreimpl.test;

import junit.framework.TestCase;
import org.tinygroup.beancontainer.BeanContainerFactory;
import org.tinygroup.cepcore.CEPCore;
import org.tinygroup.cepcore.EventProcessor;
import org.tinygroup.context.Context;
import org.tinygroup.context.impl.ContextImpl;
import org.tinygroup.event.Event;
import org.tinygroup.tinyrunner.Runner;

import java.util.ArrayList;

public class CepCoreProcessTestCast extends TestCase {
	private CEPCore core;

	private void init() {
		EventProcessor eventProcessor = new EventProcessorForTest();
		eventProcessor.getServiceInfos().add(initServiceInfo("a"));
		eventProcessor.getServiceInfos().add(initServiceInfo("b"));
		core.registerEventProcessor(eventProcessor);
	}

	private Event getEvent(String serviceId) {
		Context context = new ContextImpl();
		Event e = Event.createEvent(serviceId, context);
		return e;
	}

	private ServiceInfoForTest initServiceInfo(String serviceId) {
		ServiceInfoForTest sifft = new ServiceInfoForTest();
		sifft.setServiceId(serviceId);
		return sifft;
	}

	public void setUp() {
		try {
			super.setUp();
		} catch (Exception e) {

		}
		Runner.init("application.xml", new ArrayList<String>());
		core = BeanContainerFactory.getBeanContainer(
				this.getClass().getClassLoader())
				.getBean(CEPCore.CEP_CORE_BEAN);
		init() ;
	}

	public void testAy() {
		Event e = getEvent("a");
		e.setMode(Event.EVENT_MODE_ASYNCHRONOUS);
		core.process(e);
	}
	
	public void testy() {
		Event e = getEvent("a");
		e.setMode(Event.EVENT_MODE_SYNCHRONOUS);
		core.process(e);
	}

}
