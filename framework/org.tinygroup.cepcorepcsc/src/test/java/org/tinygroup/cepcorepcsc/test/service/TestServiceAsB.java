package org.tinygroup.cepcorepcsc.test.service;

import org.tinygroup.cepcore.CEPCore;
import org.tinygroup.cepcore.EventProcessor;
import org.tinygroup.cepcorepc.impl.PcCepCoreImpl;
import org.tinygroup.cepcorepcsc.impl.ArOperator;
import org.tinygroup.springutil.SpringUtil;
import org.tinygroup.tinytestutil.AbstractTestUtil;

public class TestServiceAsB {
	public static void main(String[] args) {
		AbstractTestUtil.init("application2.xml", true);
		PcCepCoreImpl p  = SpringUtil.getBean(CEPCore.CEP_CORE_BEAN);
		p.setNodeName("asb");
		ArOperator aro = new ArOperator("192.168.84.23","6666","192.168.84.23","8888",10);
		p.setOperator(aro);
		p.registerEventProcessor(getEventProcessor());
		p.startCEPCore(p);
	}
	
	public static EventProcessor getEventProcessor(){
		EventProcessorA a = new EventProcessorA();
		a.addServiceInfo(new ServiceA("a0"));
		a.addServiceInfo(new ServiceA("a1"));
		a.addServiceInfo(new ServiceA("a2"));
		
		return a;
	}
}
