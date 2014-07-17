package org.tinygroup.service.test.serviceinterfacetest.service;

public abstract class AbstractTestVoidInterface implements TestVoidInterface {

	public String getServiceId() {
		 return "AbstractTestInterface";
	}

	public String getCategory() {
		 return "AbstractTestInterface";
	}

	public String getResultKey() {
		return "result";
	}

	public void execute(String name) {
		System.out.println(name);
	}

	public String execute(String firstName, String secondName) {
		return firstName+secondName;
	}
	

}
