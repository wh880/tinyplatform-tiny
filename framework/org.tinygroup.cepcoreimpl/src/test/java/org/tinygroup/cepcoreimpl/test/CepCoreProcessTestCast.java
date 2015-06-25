/**
 *  Copyright (c) 1997-2013, www.tinygroup.org (tinygroup@126.com).
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
