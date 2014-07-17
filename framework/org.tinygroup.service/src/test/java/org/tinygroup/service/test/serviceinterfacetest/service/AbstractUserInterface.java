package org.tinygroup.service.test.serviceinterfacetest.service;


public abstract class AbstractUserInterface implements TestUserInterface {

	public String getServiceId() {
		return "AbstractUserInterface";
	}

	public String getCategory() {
		return "AbstractUserInterface";
	}

	public String getResultKey() {
		return "result";
	}

	public User execute(String name, int age, boolean sex) {
		return new User(name, age, sex);
	}

}
