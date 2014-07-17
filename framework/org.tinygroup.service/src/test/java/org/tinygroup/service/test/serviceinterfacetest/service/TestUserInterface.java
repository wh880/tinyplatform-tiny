package org.tinygroup.service.test.serviceinterfacetest.service;

import org.tinygroup.service.ServiceInterface;

public interface TestUserInterface extends ServiceInterface {

     public User execute(String name,int age,boolean sex);
	
}
