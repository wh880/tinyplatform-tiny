package org.tinygroup.cepcorepc.test.synchronous;

import org.tinygroup.cepcorepc.test.aop.util.AopTestUtil;
import org.tinygroup.context.util.ContextFactory;
import org.tinygroup.event.Event;
import org.tinygroup.event.ServiceRequest;

public class TestCase extends junit.framework.TestCase {
	
	public void test() {
		SynchronousProcessor p = new SynchronousProcessor();
		Service s1 = new Service("s1");
		Service s2 = new Service("s2");
		p.addService(s1);
		p.addService(s2);
		AopTestUtil.registerEventProcessor(p);

		Event e = new Event();
		ServiceRequest s = new ServiceRequest();
		e.setServiceRequest(s);
		s.setContext(ContextFactory.getContext());
		s.setServiceId("s1");
		e.setMode(Event.EVENT_MODE_SYNCHRONOUS);

		AopTestUtil.execute(e);
	}
}
