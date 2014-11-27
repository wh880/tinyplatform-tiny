/**
 *  Copyright (c) 1997-2013, www.tinygroup.org (luo_guo@icloud.com).
 *
 *  Licensed under the GPL, Version 3.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.gnu.org/licenses/gpl.html
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
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
		e.setMode(Event.EVENT_MODE_ASYNCHRONOUS);

		AopTestUtil.execute(e);
	}
}
