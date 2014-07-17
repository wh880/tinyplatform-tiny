package org.tinygroup.service.test.serviceinterfacetest.service;

public class VoidInterfaceImpl extends AbstractTestVoidInterface {
	
	public String getServiceId() {
		return "VoidInterfaceImpl";
	}

	public String execute(String firstName, String secondName) {
		return firstName+"_"+secondName;
	}

}
