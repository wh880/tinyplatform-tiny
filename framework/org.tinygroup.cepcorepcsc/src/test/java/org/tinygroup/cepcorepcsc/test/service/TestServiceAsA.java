package org.tinygroup.cepcorepcsc.test.service;

import org.tinygroup.cepcore.EventProcessor;
import org.tinygroup.tinytestutil.AbstractTestUtil;

public class TestServiceAsA {
	public static void main(String[] args) {
		AbstractTestUtil.init("applicationAsA.xml", true);
//		PcCepCoreImpl p  = SpringUtil.getBean(CEPCore.CEP_CORE_BEAN);
//		p.setNodeName("asa");
//		ArOperator aro = new ArOperator("192.168.84.23","5555","192.168.84.23","8888",10);
//		
//		p.setOperator(aro);
//		p.registerEventProcessor(getEventProcessor());
//		p.startCEPCore(p);
	}
	
	public static EventProcessor getEventProcessor(){
		EventProcessorB b = new EventProcessorB();
		b.addServiceInfo(new ServiceB("b0"));
		b.addServiceInfo(new ServiceB("b1"));
		b.addServiceInfo(new ServiceB("b2"));
		
		return b;
	}
}
