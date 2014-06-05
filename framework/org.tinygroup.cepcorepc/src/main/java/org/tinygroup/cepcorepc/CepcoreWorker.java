package org.tinygroup.cepcorepc;

import java.rmi.RemoteException;
import java.util.Map;

import org.tinygroup.event.ServiceInfo;
import org.tinygroup.tinypc.Warehouse;
import org.tinygroup.tinypc.Work;
import org.tinygroup.tinypc.Worker;

public class CepcoreWorker implements Worker {
	/**
	 * 
	 */
	DataUtil d = new DataUtil();
	String id;
	private static final long serialVersionUID = -4183566064003919135L;
	public CepcoreWorker(String id) {
		this.id = id;
	}
	public String getId() throws RemoteException {
		return id;
	}

	public Warehouse work(Work work) throws RemoteException {
		d.plus();
		Map<String, ServiceInfo> serviceContext = work.getInputWarehouse().get(
				"services");
		d.addServices(serviceContext);
		return null;
	}

	public boolean acceptWork(Work work) throws RemoteException {
		return true;
	}

	public String getType() throws RemoteException {
		return "reg";
	}

}
