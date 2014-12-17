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
package org.tinygroup.cepcorenettysc.test.service.server;

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
