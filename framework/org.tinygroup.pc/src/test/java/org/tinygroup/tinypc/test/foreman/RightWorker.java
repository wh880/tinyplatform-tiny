package org.tinygroup.tinypc.test.foreman;

import java.rmi.RemoteException;

import org.tinygroup.tinypc.Warehouse;
import org.tinygroup.tinypc.Work;
import org.tinygroup.tinypc.impl.AbstractWorker;

public class RightWorker extends AbstractWorker {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2418099267597610109L;

	public RightWorker(String type) throws RemoteException {
		super(type);
	}

	protected Warehouse doWork(Work work) throws RemoteException {
		System.out.println("execute work:"+work.getId());
		return work.getInputWarehouse();
	}

}
