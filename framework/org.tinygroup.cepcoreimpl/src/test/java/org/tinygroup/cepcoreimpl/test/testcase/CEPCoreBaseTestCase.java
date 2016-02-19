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
package org.tinygroup.cepcoreimpl.test.testcase;

import java.util.ArrayList;

import junit.framework.TestCase;

import org.tinygroup.beancontainer.BeanContainerFactory;
import org.tinygroup.cepcore.CEPCore;
import org.tinygroup.cepcore.EventProcessor;
import org.tinygroup.cepcoreimpl.test.AsynchronousEventProcessorForTest;
import org.tinygroup.cepcoreimpl.test.EventProcessorForTest;
import org.tinygroup.cepcoreimpl.test.EventProcessorForTestEnable;
import org.tinygroup.cepcoreimpl.test.ServiceInfoForTest;
import org.tinygroup.context.Context;
import org.tinygroup.context.impl.ContextImpl;
import org.tinygroup.event.Event;
import org.tinygroup.tinyrunner.Runner;

public abstract class CEPCoreBaseTestCase extends TestCase {
	protected static final String SERVICE_ID = "asynchronousService";
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
		EventProcessor eventProcessor = new AsynchronousEventProcessorForTest();
		eventProcessor.getServiceInfos().add(initServiceInfo(SERVICE_ID));
		getCore().registerEventProcessor(eventProcessor);
		
		EventProcessor eventProcessor2 = new EventProcessorForTest();
		eventProcessor2.getServiceInfos().add(initServiceInfo("a"));
		eventProcessor2.getServiceInfos().add(initServiceInfo("b"));
		eventProcessor2.getServiceInfos().add(initServiceInfo("exception"));
		getCore().registerEventProcessor(eventProcessor2);
		
	}
	
	
}
