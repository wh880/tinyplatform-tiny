package org.tinygroup.cepcoreimpl.test.testcase;

import java.util.List;

import junit.framework.Assert;

import org.tinygroup.cepcore.EventProcessor;
import org.tinygroup.cepcoreimpl.test.EventProcessorForTestEnable;

public class EventProcessorEnableTest extends CEPCoreBaseTestCase {
	public void setUp() {
		super.setUp();
		EventProcessorForTestEnable e1 = new EventProcessorForTestEnable();
		e1.getServiceInfos().add(initServiceInfo("helloEnableTrue"));
		e1.getServiceInfos().add(initServiceInfo("helloEnablefalse"));
		getCore().registerEventProcessor(e1);
	}

	public void testCase() {
		getCore().process(getEvent("helloEnableTrue"));
		List<EventProcessor> ps = getCore().getEventProcessors();
		for(EventProcessor p:ps){
			if(EventProcessorForTestEnable.class.getSimpleName().equals(p.getId())){
				p.setEnable(false);
			}
		}
		try {
			getCore().process(getEvent("helloEnablefalse"));
			Assert.fail();
		} catch (Exception e) {

		}
		for(EventProcessor p:ps){
			if(EventProcessorForTestEnable.class.getSimpleName().equals(p.getId())){
				p.setEnable(true);
			}
		}
		getCore().process(getEvent("helloEnableTrue"));
	}
}
