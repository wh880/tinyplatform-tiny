package org.tinygroup.cepcoregovernance.test.testcase;

import java.util.ArrayList;

import org.tinygroup.beancontainer.BeanContainerFactory;
import org.tinygroup.cepcore.CEPCore;
import org.tinygroup.cepcoregovernance.CommonServiceExecuteContainer;
import org.tinygroup.context.util.ContextFactory;
import org.tinygroup.event.Event;
import org.tinygroup.tinyrunner.Runner;

import junit.framework.TestCase;

public class LocalAsynchronousServiceTestCase extends TestCase {
	private CEPCore core;

	public void setUp() {
		Runner.init("application.xml", new ArrayList<String>());
		core = BeanContainerFactory.getBeanContainer(
				LocalAsynchronousServiceTestCase.class.getClassLoader())
				.getBean(CEPCore.CEP_CORE_BEAN);
	}

	public void testServiceTime() {
		Thread t = new Thread(new Task("localService"));
		Thread t2 = new Thread(new Task("exceptionService"));
		t.start();
		t2.start();
		assertTrue(CommonServiceExecuteContainer.getLocalTotalTimes()
				.longValue()<=2000);
		assertTrue(CommonServiceExecuteContainer.getLocalExceptionTimes()
				.longValue()<=1000);
		assertTrue(CommonServiceExecuteContainer.getLocalSucessTimes()
				.longValue()<=1000);
	}

	private void executeService(String serviceId) {
		try {
			core.process(getEvent(serviceId));
		} catch (Exception e) {
		}

	}

	private Event getEvent(String serviceId) {
		return Event.createEvent(serviceId, ContextFactory.getContext());
	}

	class Task implements Runnable {
		private String serviceId;

		public Task(String serviceId) {
			this.serviceId = serviceId;
		}

		public void run() {
			for (int i = 0; i < 1000; i++) {
				executeService(serviceId);
			}
		}
	}
}
