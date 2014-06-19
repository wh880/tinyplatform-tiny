package org.tinygroup.cepcorepc.test.cepcorepc;

import org.tinygroup.cepcorepc.impl.ArOperator;
import org.tinygroup.cepcorepc.impl.PcCepCoreImpl2;

public class TestAr {
	public static void main(String[] args) {
		
		PcCepCoreImpl2 p = new PcCepCoreImpl2();
		p.setNodeName("ar");
		ArOperator aro = new ArOperator("192.168.84.23","6666","192.168.84.23","8888");
		aro.setCep(p);
		p.setOperator(aro);
		p.startCEPCore(p);
		
	
	}
}
