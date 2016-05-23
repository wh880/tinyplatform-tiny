package org.tinygroup.mbean;




public class HelloMX implements HelloMXBean{

	public void print(String name) {
		System.out.println("天空一声巨响，"+name+"闪亮登场！");
	}

}
