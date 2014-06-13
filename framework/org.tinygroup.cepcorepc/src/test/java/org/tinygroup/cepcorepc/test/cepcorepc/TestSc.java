package org.tinygroup.cepcorepc.test.cepcorepc;

import org.tinygroup.cepcorepc.impl.PcCepCoreImpl2;
import org.tinygroup.cepcorepc.impl.ScOperator;

public class TestSc {
	public static void main(String[] args) {
		PcCepCoreImpl2 p = new PcCepCoreImpl2();
		p.setNodeName("sc");
		ScOperator o = new  ScOperator("192.168.84.23",8888);
		p.setOperator(o);
		p.startCEPCore(p);
	}
}
