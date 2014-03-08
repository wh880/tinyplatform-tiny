package org.tinygroup.rmi.test;

import java.rmi.Remote;
import java.rmi.RemoteException;

import org.tinygroup.rmi.Verifiable;

/**
 * Created by luoguo on 14-1-24.
 */
public interface Hello extends Remote,Verifiable {
    String sayHello(String name) throws RemoteException;
}
