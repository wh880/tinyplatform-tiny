package org.tinygroup.cepcoreimpl.test.testcase;

import junit.framework.TestCase;

import org.tinygroup.cepcore.CEPCore;
import org.tinygroup.cepcoreimpl.CEPCoreImpl;
import org.tinygroup.cepcoreimpl.test.EventProcessorForTestSameLocalService;
import org.tinygroup.cepcoreimpl.test.ServiceInfoForTest;
import org.tinygroup.event.Parameter;
	
public class MainTestSameLocalService extends TestCase{
	
	public void testCase1(){
		dealSame();
	}
	private static void dealSame() {
		CEPCore core = new CEPCoreImpl();
		EventProcessorForTestSameLocalService e1 = new EventProcessorForTestSameLocalService();
		e1.setId("e1");
		e1.getServiceInfos().add(getServiceInfo("a",false));
		
		EventProcessorForTestSameLocalService e2 = new EventProcessorForTestSameLocalService();
		e2.setId("e2");
		e2.getServiceInfos().add(getServiceInfo("a",true));
		
		core.registerEventProcessor(e1);
		core.registerEventProcessor(e2);
	}
	public static void main(String[] args) {
		dealSame();
	}

	private static ServiceInfoForTest getServiceInfo(String id,boolean param) {
		ServiceInfoForTest info = new ServiceInfoForTest();
		info.setServiceId(id);
		if(param){
			info.getParameters().add(new Parameter());
		}
		return info;
		
	}
	
}
