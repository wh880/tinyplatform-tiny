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
package org.tinygroup.cepcorepc.test.regex;

import org.tinygroup.beancontainer.BeanContainerFactory;
import org.tinygroup.cepcore.CEPCore;
import org.tinygroup.cepcorepc.test.synchronous.Service;
import org.tinygroup.context.util.ContextFactory;
import org.tinygroup.event.Event;
import org.tinygroup.event.ServiceRequest;
import org.tinygroup.tinytestutil.AbstractTestUtil;

public class TestCase {
	public static void main(String[] args) {

		AbstractTestUtil.init(null, true);
		CEPCore cep = BeanContainerFactory.getBeanContainer(
				TestCase.class.getClassLoader()).getBean(CEPCore.CEP_CORE_BEAN);
		RegexProcessor p = new RegexProcessor();
		Service s1 = new Service("a1");
		Service s2 = new Service("a2");
		p.addService(s1);
		p.addService(s2);
		cep.registerEventProcessor(p);
		
		RegexProcessor2 p2 = new RegexProcessor2();
		cep.registerEventProcessor(p2);
		
		Event e = new Event();
		ServiceRequest s = new ServiceRequest();
		e.setServiceRequest(s);
		s.setContext(ContextFactory.getContext());
		s.setServiceId("a3");
		e.setMode(Event.EVENT_MODE_SYNCHRONOUS);
		cep.process(e);
		
		
		Event e1 = new Event();
		ServiceRequest ss1 = new ServiceRequest();
		e1.setServiceRequest(ss1);
		ss1.setContext(ContextFactory.getContext());
		ss1.setServiceId("v3");
		e1.setMode(Event.EVENT_MODE_SYNCHRONOUS);
		cep.process(e1);
	}
}
