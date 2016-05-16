package org.tinygroup.cepcoreimpl.test.testcase;

import junit.framework.TestCase;

import org.tinygroup.cepcore.CEPCore;
import org.tinygroup.cepcoreimpl.CEPCoreImpl;
import org.tinygroup.cepcoreimpl.test.EventProcessorForTestSameRemoteService;
import org.tinygroup.cepcoreimpl.test.ServiceInfoForTest;
import org.tinygroup.event.Parameter;

public class MainTestSameRemoteService extends TestCase {

	public void testCase1() {
		dealSame();
	}

	public static void main(String[] args) {
		dealSame();
	}

	private static void dealSame() {
		CEPCore core = new CEPCoreImpl();
		EventProcessorForTestSameRemoteService e1 = new EventProcessorForTestSameRemoteService();
		e1.setId("e1");
		e1.getServiceInfos().add(getServiceInfo("a", false));

		EventProcessorForTestSameRemoteService e2 = new EventProcessorForTestSameRemoteService();
		e2.setId("e2");
		e2.getServiceInfos().add(getServiceInfo("a", true));

		core.registerEventProcessor(e1);
		core.registerEventProcessor(e2);
	}

	private static ServiceInfoForTest getServiceInfo(String id, boolean param) {
		ServiceInfoForTest info = new ServiceInfoForTest();
		info.setServiceId(id);
		if (param) {
			info.getParameters().add(new Parameter());
		}
		return info;

	}

}
