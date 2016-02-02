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

import org.tinygroup.cepcore.EventProcessor;
import org.tinygroup.cepcoreimpl.test.EventProcessorForTest;
import org.tinygroup.event.Event;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;

public class CepCoreProcessTestCase extends CEPCoreBaseTestCase {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(CepCoreProcessTestCase.class);
	public void setUp() {
		super.setUp();
		init();
	}

	private void init() {
		EventProcessor eventProcessor = new EventProcessorForTest();
		eventProcessor.getServiceInfos().add(initServiceInfo("a"));
		eventProcessor.getServiceInfos().add(initServiceInfo("b"));
		eventProcessor.getServiceInfos().add(initServiceInfo("exception"));
		getCore().registerEventProcessor(eventProcessor);
	}

	public void testAy() {
		Event e = getEvent("a");
		e.setMode(Event.EVENT_MODE_ASYNCHRONOUS);
		try {
			getCore().process(e);
		} catch (Exception e2) {
			fail();
		}
	}

	public void testy() {
		Event e = getEvent("a");
		e.setMode(Event.EVENT_MODE_SYNCHRONOUS);
		try {
			getCore().process(e);
		} catch (Exception e2) {
			fail();
		}
	}

	public void testException() {
		Event e = getEvent("exception");
		e.setMode(Event.EVENT_MODE_SYNCHRONOUS);
		try {
			getCore().process(e);
		} catch (Exception e2) {
			LOGGER.errorMessage("异常",e2);
			assertEquals(e2.getMessage(), "testExceptionhandler");
		}

	}

}
