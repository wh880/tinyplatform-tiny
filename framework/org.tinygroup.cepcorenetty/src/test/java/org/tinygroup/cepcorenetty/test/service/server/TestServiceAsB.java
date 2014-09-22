package org.tinygroup.cepcorenetty.test.service.server;

import org.tinygroup.beancontainer.BeanContainerFactory;
import org.tinygroup.cepcore.CEPCore;
import org.tinygroup.cepcore.EventProcessor;
import org.tinygroup.cepcorenetty.NettyCepCoreImpl;
import org.tinygroup.cepcorenetty.operator.ArOperator;
import org.tinygroup.cepcorenetty.test.service.EventProcessorA;
import org.tinygroup.cepcorenetty.test.service.ServiceA;
import org.tinygroup.tinytestutil.AbstractTestUtil;

public class TestServiceAsB {
	public static void main(String[] args) {
		AbstractTestUtil.init("applicationb.xml", true);
		NettyCepCoreImpl p  = BeanContainerFactory.getBeanContainer(
				TestServiceAsB.class.getClassLoader()).getBean(CEPCore.CEP_CORE_BEAN);
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
