package org.tinygroup.cepcorepcsc.test.cepcorepcsc;

import org.tinygroup.cepcorepc.impl.PcCepCoreImpl;
import org.tinygroup.cepcorepcsc.impl.ScOperator;

public class TestSc {
	public static void main(String[] args) {
		PcCepCoreImpl p = new PcCepCoreImpl();
		p.setNodeName("sc");
		ScOperator o = new  ScOperator("192.168.84.23",8888);
		p.setOperator(o);
		p.startCEPCore(p);
	}
}
