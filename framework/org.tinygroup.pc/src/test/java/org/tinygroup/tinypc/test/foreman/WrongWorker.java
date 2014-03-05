package org.tinygroup.tinypc.test.foreman;

import java.rmi.RemoteException;

import org.tinygroup.tinypc.Warehouse;
import org.tinygroup.tinypc.Work;
import org.tinygroup.tinypc.impl.AbstractWorker;

public class WrongWorker extends AbstractWorker {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2187410936638096505L;

	public WrongWorker(String type) throws RemoteException {
		super(type);	
	}

	protected Warehouse doWork(Work work) throws RemoteException {
		throw new RuntimeException("execute error");
	}

}
