package org.tinygroup.tinyrmi.test;

import java.rmi.RemoteException;

/**
 * Created by luoguo on 14-1-24.
 */
public class HelloImpl implements Hello {
    public HelloImpl() throws RemoteException {
        System.out.println("创建：" + HelloImpl.class + "实例");
    }

    public String sayHello(String name) throws RemoteException {
        return "Hello," + name;
    }

	public void verify() throws RemoteException {
		// TODO Auto-generated method stub
		
	}
}
