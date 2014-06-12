package org.tinygroup.cepcorepc;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.tinygroup.cepcorepc.impl.OperatorUtil;
import org.tinygroup.cepcorepc.impl.ScOperator;
import org.tinygroup.event.ServiceInfo;
import org.tinygroup.event.central.Node;
import org.tinygroup.tinypc.Warehouse;
import org.tinygroup.tinypc.Work;
import org.tinygroup.tinypc.Worker;
import org.tinygroup.tinypc.impl.WarehouseDefault;

public class ScWorker implements Worker {
	
	private static final long serialVersionUID = 2239395981835114070L;
	
	private String id;
	
	public ScWorker(String id) {
		this.id = id;
	}
	public String getId() throws RemoteException {
		return id;
	}

	public Warehouse work(Work work) throws RemoteException {

		ScWork scWork = (ScWork)work;
		String arWorkerId = scWork.getWorker();
		putServiceToAr(arWorkerId);
		List<ServiceInfo> list = work.getInputWarehouse().get(OperatorUtil.AR_TO_SC_SERVICE_KEY);
		Node arNode = work.getInputWarehouse().get(OperatorUtil.NODE_KEY);
		Map<Node,List<ServiceInfo>> map = new HashMap<Node, List<ServiceInfo>>();
		map.put(arNode, list);
		putServiceToArs(map);
		return null;
	}
	
	//再把发起work的ar的推送给所有ar
	private void putServiceToArs(Map<Node, List<ServiceInfo>> map) throws RemoteException{
		Work doWork = new ArWork();
		doWork.setInputWarehouse(new WarehouseDefault());
		doWork.getInputWarehouse().put(OperatorUtil.SC_TO_AR_SERVICE_KEY, map);
		try {
			ScOperator.getJobCenter().doWork(doWork);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//把sc已有的全部推送给发起work的ar
	private void putServiceToAr(String arWorkerId) throws RemoteException{
		Work doWork = new ArWork();
		Worker arWorker = null;
		List<Worker> workers = ScOperator.getJobCenter().getWorkerList(doWork);
		for(Worker w:workers){
			if(w.getId().equals(arWorkerId)){
				arWorker = w;
				break;
			}
		}
		Map<Node, List<ServiceInfo>> map = ScOperator.gettArServices();
		doWork.setInputWarehouse(new WarehouseDefault());
		doWork.getInputWarehouse().put(OperatorUtil.SC_TO_AR_SERVICE_KEY,map );
		arWorker.work(doWork);
	}

	public boolean acceptWork(Work work) throws RemoteException {
		return true;
	}

	public String getType() throws RemoteException {
		return ScWork.WORK_TYPE;
	}

}
