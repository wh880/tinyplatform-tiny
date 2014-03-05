package org.tinygroup.tinypc.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by luoguo on 14-1-24.
 */
public interface Hello extends Remote {
    String sayHello(String name) throws RemoteException;
}
