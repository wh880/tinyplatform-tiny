package org.tinygroup.service.test.serviceinterfacetest.service;

public class VoidInterfaceImpl2 extends AbstractTestVoidInterface {
	
	public String getServiceId() {
		return "VoidInterfaceImpl2";
	}

	public void execute(String name) {
		System.out.println("hello:"+name);
	}
	
}
