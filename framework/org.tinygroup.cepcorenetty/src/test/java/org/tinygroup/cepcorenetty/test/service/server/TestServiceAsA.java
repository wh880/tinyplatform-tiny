package org.tinygroup.cepcorenetty.test.service.server;

import org.tinygroup.beancontainer.BeanContainerFactory;
import org.tinygroup.cepcore.CEPCore;
import org.tinygroup.cepcore.EventProcessor;
import org.tinygroup.cepcorenetty.NettyCepCoreImpl;
import org.tinygroup.cepcorenetty.operator.ArOperator;
import org.tinygroup.cepcorenetty.test.service.EventProcessorB;
import org.tinygroup.cepcorenetty.test.service.ServiceB;
import org.tinygroup.tinytestutil.AbstractTestUtil;

public class TestServiceAsA {
	public static void main(String[] args) {
		AbstractTestUtil.init("applicationa.xml", true);
		NettyCepCoreImpl p  = BeanContainerFactory.getBeanContainer(
				TestServiceAsA.class.getClassLoader()).getBean(CEPCore.CEP_CORE_BEAN);
		p.setNodeName("asa");
		ArOperator aro = new ArOperator("192.168.84.23","5555","192.168.84.23","8888",10);
		
		p.setOperator(aro);
		p.registerEventProcessor(getEventProcessor());
		p.startCEPCore(p);
	}
	
	public static EventProcessor getEventProcessor(){
		EventProcessorB b = new EventProcessorB();
		b.addServiceInfo(new ServiceB("b0"));
		b.addServiceInfo(new ServiceB("b1"));
		b.addServiceInfo(new ServiceB("b2"));
		
		return b;
	}
}
