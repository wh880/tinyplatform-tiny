
package org.tinygroup.cepcoregovernance.test.testcase;

import java.util.ArrayList;

import org.tinygroup.beancontainer.BeanContainerFactory;
import org.tinygroup.cepcore.CEPCore;
import org.tinygroup.cepcoregovernance.CommonServiceExecuteContainer;
import org.tinygroup.context.util.ContextFactory;
import org.tinygroup.event.Event;
import org.tinygroup.tinyrunner.Runner;

import junit.framework.TestCase;

public class LocalServiceTestCase extends TestCase {
	private CEPCore core;

	public void setUp() {
		Runner.init("application.xml", new ArrayList<String>());
		core = BeanContainerFactory.getBeanContainer(
				LocalServiceTestCase.class.getClassLoader()).getBean(
				CEPCore.CEP_CORE_BEAN);
	}

	public void testServiceTime() {
		for(int i = 0 ; i < 1000 ; i ++ ){
			executeService("exceptionService");
			executeService("localService");
		}
		assertEquals(2000,CommonServiceExecuteContainer.getLocalTotalTimes().longValue());
		assertEquals(1000,CommonServiceExecuteContainer.getLocalSucessTimes().longValue());
		assertEquals(1000,CommonServiceExecuteContainer.getLocalExceptionTimes().longValue());
	}

	private void executeService(String serviceId ) {
		try{
			core.process(getEvent(serviceId));
		}catch (Exception e) {
		}
		
	}

	private Event getEvent(String serviceId) {
		return Event.createEvent(serviceId, ContextFactory.getContext());
	}
}
