package org.tinygroup.cepcorenetty.test;


public class CoreTestCase {
	public static int ProcessorNum = 6;
	public static int ServiceNum = 100;

	public static void main(String[] args) {
		core(new CoreInCore());
		core(new CoreInProcessor());
	}
	
	
	public static void core(Core core) {
		int j = 0;
		for (int i = 0; i < ProcessorNum; i++, j = j - 10 + ServiceNum) {
			Processor p = new Processor(ServiceNum, j, "v");
			core.addProcessor(p);
		}
		long begin = System.currentTimeMillis();
		System.out.println(begin);
		for (int i = 0; i < j; i++) {
			core.get("v" + i);
		}
		long end = System.currentTimeMillis();
		System.out.println(end-begin);
		System.out.println(j);
	}
}
