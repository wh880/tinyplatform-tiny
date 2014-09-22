package org.tinygroup.cepcorenetty.test.service.server;

import org.tinygroup.beancontainer.BeanContainerFactory;
import org.tinygroup.cepcore.CEPCore;
import org.tinygroup.cepcorenetty.NettyCepCoreImpl;
import org.tinygroup.event.Event;
import org.tinygroup.event.ServiceRequest;
import org.tinygroup.tinytestutil.AbstractTestUtil;

public class TestServiceAr {
	public static void main(String[] args) {
		AbstractTestUtil.init("application.xml", true);
		NettyCepCoreImpl p = BeanContainerFactory.getBeanContainer(
				TestServiceAr.class.getClassLoader()).getBean(
				CEPCore.CEP_CORE_BEAN);
		p.process(getEvent());
	}

	public static Event getEvent() {
		Event e = new Event();
		ServiceRequest s = new ServiceRequest();
		s.setServiceId("a0");
		e.setServiceRequest(s);
		return e;
	}
}
