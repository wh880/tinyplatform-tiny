package org.tinygroup.cepcorepcsc.test;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import org.tinygroup.event.ServiceInfo;
import org.tinygroup.tinypc.Warehouse;
import org.tinygroup.tinypc.Work;
import org.tinygroup.tinypc.Worker;
import org.tinygroup.tinypc.impl.WarehouseDefault;

public class ScTestWorker implements Worker {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2239395981835114070L;
	/**
	 * 
	 */
	DataUtil d = new DataUtil();
	String id;
	public ScTestWorker(String id) {
		this.id = id;
	}
	public String getId() throws RemoteException {
		return id;
	}

	public Warehouse work(Work work) throws RemoteException {
		d.plus();
		
		ScTestWork scWork = (ScTestWork)work;
		//把sc已有的全部推送给ar
		putServiceToAr(scWork);
		
		Map<String, ServiceInfo> serviceContext = work.getInputWarehouse().get(
				"services");
		d.addServices(serviceContext);
		//再把ar的推送给所有ar
		
		putServiceToArs(serviceContext);
		
		return null;
	}
	
	private void putServiceToArs(Map<String, ServiceInfo> serviceContext ) throws RemoteException{
		System.out.println("========推送service去所有的ar===========");
		Work doWork = new ArTestWork();
		doWork.setInputWarehouse(new WarehouseDefault());
		doWork.getInputWarehouse().put("services", serviceContext);
		try {
			Server.jobCenter.doWork(doWork);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("========推送service去所有的ar完成===========");
		
	}
	
	private void putServiceToAr(ScTestWork scWork) throws RemoteException{
		System.out.println("========推送历史service去ar===========");
		Work doWork = new ArTestWork();
		String arWorkerId = scWork.getWorker();
		Worker arWorker = null;
		List<Worker> workers = Server.jobCenter.getWorkerList(doWork);
		for(Worker w:workers){
			if(w.getId().equals(arWorkerId)){
				arWorker = w;
				break;
			}
		}
		doWork.setInputWarehouse(new WarehouseDefault());
		doWork.getInputWarehouse().put("services",d.getServices()  );
		arWorker.work(doWork);
		System.out.println("========推送历史service去ar完成===========");
	}

	public boolean acceptWork(Work work) throws RemoteException {
		return true;
	}

	public String getType() throws RemoteException {
		return "sc";
	}

}
