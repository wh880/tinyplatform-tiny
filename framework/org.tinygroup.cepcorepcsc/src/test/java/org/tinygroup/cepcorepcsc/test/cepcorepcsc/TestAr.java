package org.tinygroup.cepcorepcsc.test.cepcorepcsc;

import org.tinygroup.cepcorepc.impl.PcCepCoreImpl;
import org.tinygroup.cepcorepcsc.impl.ArOperator;

public class TestAr {
	public static void main(String[] args) {
		
		PcCepCoreImpl p = new PcCepCoreImpl();
		p.setNodeName("ar");
		ArOperator aro = new ArOperator("192.168.84.23","6666","192.168.84.23","8888",10);
		p.setOperator(aro);
		p.start();
		
	
	}
}
