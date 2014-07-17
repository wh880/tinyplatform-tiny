package org.tinygroup.service.test.serviceinterfacetest.service;

import org.tinygroup.service.ServiceInterface;

public interface TestVoidInterface extends ServiceInterface {

     public void execute(String name);
     
     public String execute(String firstName,String secondName );
	
}
