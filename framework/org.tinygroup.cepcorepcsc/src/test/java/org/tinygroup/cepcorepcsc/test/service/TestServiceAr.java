package org.tinygroup.cepcorepcsc.test.service;

import org.tinygroup.cepcore.CEPCore;
import org.tinygroup.cepcorepc.impl.PcCepCoreImpl;
import org.tinygroup.cepcorepcsc.impl.ArOperator;
import org.tinygroup.event.Event;
import org.tinygroup.event.ServiceRequest;
import org.tinygroup.springutil.SpringUtil;
import org.tinygroup.tinytestutil.AbstractTestUtil;

public class TestServiceAr {
	public static void main(String[] args) {
		AbstractTestUtil.init("application.xml", true);
		PcCepCoreImpl p  = SpringUtil.getBean(CEPCore.CEP_CORE_BEAN);
		p.setNodeName("asa");
		ArOperator aro = new ArOperator("192.168.84.23","3333","192.168.84.23","8888",10);
		p.setOperator(aro);
		p.startCEPCore(p);
		p.process(getEvent());
	}
	
	public static Event getEvent(){
		Event e = new Event();
		ServiceRequest s = new ServiceRequest();
		s.setServiceId("a0");
		e.setServiceRequest(s);
		return e;
	}
}
