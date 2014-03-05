package org.tinygroup.tinypc.rmi.registry;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class LocateRegistryTest {
	public static void main(String[] args) {
		try {
			Registry registry = LocateRegistry.getRegistry("192.168.84.52",
					8888);
			System.out.println(registry);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Registry registry = LocateRegistry.getRegistry("192.168.84.52",
					8888);
			registry = LocateRegistry.createRegistry(8888);
			System.out.println(registry);
			 registry.list();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
