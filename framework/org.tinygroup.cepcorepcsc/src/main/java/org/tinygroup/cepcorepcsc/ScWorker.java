package org.tinygroup.cepcorepcsc;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.tinygroup.cepcorepc.impl.OperatorUtil;
import org.tinygroup.cepcorepcsc.impl.ScOperator;
import org.tinygroup.event.ServiceInfo;
import org.tinygroup.event.central.Node;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.tinypc.Warehouse;
import org.tinygroup.tinypc.Work;
import org.tinygroup.tinypc.Worker;
import org.tinygroup.tinypc.impl.WarehouseDefault;

public class ScWorker implements Worker {
	private static Logger logger = LoggerFactory.getLogger(ScWorker.class);
	private static final long serialVersionUID = 2239395981835114070L;
	private String id;
	private ScOperator scOperator;

	
	public void setId(String id) {
		this.id = id;
	}

	public ScWorker(String id ,ScOperator scOperator) {
		this.id = id;
		this.scOperator = scOperator;
	}

	public String getId() throws RemoteException {
		return id;
	}

	public Warehouse work(Work work) throws RemoteException {
		String workType = work.getInputWarehouse().get(
				OperatorUtil.TYPE_KEY);
		if(OperatorUtil.REG_KEY.equals(workType)){
			return dealReg(work);
		}else{
			return dealUnReg(work);
		}
		
	}
	public Warehouse dealUnReg(Work work) {
		logger.logMessage(LogLevel.INFO, "开始处理注销work");
		Node arNode = work.getInputWarehouse().get(OperatorUtil.NODE_KEY);
		scOperator.removeArServices(arNode.getNodeName());
		
		if(scOperator.gettArServices().size()==0){
			logger.logMessage(LogLevel.INFO, "已无其他服务器，无需进一步注销");
			logger.logMessage(LogLevel.INFO, "处理注销work完毕");
			return null;
		}
		
		logger.logMessage(LogLevel.INFO, "开始向所有服务器注销服务器 name:{}",
				arNode.getNodeName());
		Work doWork = new ArWork();
		doWork.setInputWarehouse(new WarehouseDefault());
		doWork.getInputWarehouse().put(OperatorUtil.NODE_KEY, arNode);
		doWork.getInputWarehouse().put(OperatorUtil.TYPE_KEY, OperatorUtil.UNREG_KEY);
		try {
			scOperator.getJobCenter().doWork(doWork);
		} catch (IOException e) {
			e.printStackTrace();
		}
		logger.logMessage(LogLevel.INFO, "向所有服务器注销服务器 name:{}完毕",
				arNode.getNodeName());
		logger.logMessage(LogLevel.INFO, "处理注销work完毕");
		return null;
	}

	public Warehouse dealReg(Work work) throws RemoteException {
		logger.logMessage(LogLevel.INFO, "开始处理注册work");
		ScWork scWork = (ScWork) work;
		String arWorkerId = scWork.getWorker();
		Node arNode = work.getInputWarehouse().get(OperatorUtil.NODE_KEY);
		logger.logMessage(LogLevel.INFO,
				"work所发起服务器为name:{0} 携带worker id为:{1}",
				arNode.getNodeName(), arWorkerId);
		putServiceToAr(arNode, arWorkerId);

		List<ServiceInfo> list = work.getInputWarehouse().get(
				OperatorUtil.AR_TO_SC_SERVICE_KEY);
		if(list==null||list.size()==0){
			logger.logMessage(LogLevel.INFO,
					"work所携带的服务为空,无需进一步处理");
			return null;
		}
		scOperator.putArServices(arNode,list);
		
		putServiceToArs(arNode, list);
		logger.logMessage(LogLevel.INFO, "处理注册work结束");
		return null;
	}

	// 再把发起work的ar的推送给所有ar
	private void putServiceToArs(Node arNode, List<ServiceInfo> list)
			throws RemoteException {
		logger.logMessage(LogLevel.INFO, "开始将服务器 name:{}的服务发送至所有服务器",
				arNode.getNodeName());
		Map<Node, List<ServiceInfo>> map = new HashMap<Node, List<ServiceInfo>>();
		map.put(arNode, list);
		Work doWork = new ArWork();
		doWork.setInputWarehouse(new WarehouseDefault());
		doWork.getInputWarehouse().put(OperatorUtil.SC_TO_AR_SERVICE_KEY, map);
		doWork.getInputWarehouse().put(OperatorUtil.TYPE_KEY, OperatorUtil.REG_KEY);
		try {
			scOperator.getJobCenter().doWork(doWork);
		} catch (IOException e) {
			e.printStackTrace();
		}
		logger.logMessage(LogLevel.INFO, "服务器 name:{}的服务发送至所有服务器完毕",
				arNode.getNodeName());
	}

	// 把sc已有的全部推送给发起work的ar
	private void putServiceToAr(Node arNode, String arWorkerId)
			throws RemoteException {

		logger.logMessage(LogLevel.INFO, "开始将服务中心所有服务发送至服务器name:{}",
				arNode.getNodeName());
		Work doWork = new ArWork();
		Worker arWorker = null;
		List<Worker> workers = scOperator.getJobCenter().getWorkerList(doWork);
		for (Worker w : workers) {
			if (w.getId().equals(arWorkerId)) {
				arWorker = w;
				break;
			}
		}
		if (arWorker == null) {
			logger.logMessage(LogLevel.INFO, "worker id:{}不存在，退出处理", arWorkerId);
			return;
		}
		Map<Node, List<ServiceInfo>> map = scOperator.gettArServices();
		doWork.setInputWarehouse(new WarehouseDefault());
		doWork.getInputWarehouse().put(OperatorUtil.SC_TO_AR_SERVICE_KEY, map);
		doWork.getInputWarehouse().put(OperatorUtil.TYPE_KEY, OperatorUtil.REG_KEY);
		arWorker.work(doWork);
		logger.logMessage(LogLevel.INFO, "将服务中心所有服务发送至服务器name:{}完毕",
				arNode.getNodeName());
	}

	public boolean acceptWork(Work work) throws RemoteException {
		return true;
	}

	public String getType() throws RemoteException {
		return ScWork.WORK_TYPE;
	}

}
