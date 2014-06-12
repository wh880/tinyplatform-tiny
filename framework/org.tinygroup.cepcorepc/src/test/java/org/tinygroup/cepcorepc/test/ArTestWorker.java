package org.tinygroup.cepcorepc.test;

import java.rmi.RemoteException;
import java.util.Map;

import org.tinygroup.event.ServiceInfo;
import org.tinygroup.tinypc.Warehouse;
import org.tinygroup.tinypc.Work;
import org.tinygroup.tinypc.Worker;

public class ArTestWorker implements Worker {
	
	private static final long serialVersionUID = -8302946955163643890L;
	
	DataUtil d = new DataUtil();
	String id;
	public ArTestWorker(String id) {
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
		return ArTestWork.WORK_TYPE.equals(work.getType());
	}

	public String getType() throws RemoteException {
		return ArTestWork.WORK_TYPE;
	}

}
