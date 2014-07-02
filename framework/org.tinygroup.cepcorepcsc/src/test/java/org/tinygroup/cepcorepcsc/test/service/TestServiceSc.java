package org.tinygroup.cepcorepcsc.test.service;

import org.tinygroup.cepcore.CEPCore;
import org.tinygroup.cepcorepc.impl.PcCepCoreImpl;
import org.tinygroup.cepcorepcsc.impl.ScOperator;
import org.tinygroup.springutil.SpringUtil;
import org.tinygroup.tinytestutil.AbstractTestUtil;

public class TestServiceSc {
	public static void main(String[] args) {
		AbstractTestUtil.init("applicationsc.xml", true);
		PcCepCoreImpl p  = SpringUtil.getBean(CEPCore.CEP_CORE_BEAN);
		p.setNodeName("sc");
		ScOperator o = new  ScOperator("192.168.84.23",8888);
		p.setOperator(o);
		p.startCEPCore(p);
	}
}
