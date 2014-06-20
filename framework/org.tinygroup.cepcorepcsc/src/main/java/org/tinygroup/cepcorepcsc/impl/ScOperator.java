package org.tinygroup.cepcorepcsc.impl;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.tinygroup.cepcore.CEPCore;
import org.tinygroup.cepcore.CEPCoreOperator;
import org.tinygroup.cepcorepcsc.ArWork;
import org.tinygroup.cepcorepcsc.ScWork;
import org.tinygroup.cepcorepcsc.ScWorker;
import org.tinygroup.event.ServiceInfo;
import org.tinygroup.event.central.Node;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.rmi.RemoteObject;
import org.tinygroup.tinypc.Foreman;
import org.tinygroup.tinypc.JobCenter;
import org.tinygroup.tinypc.impl.ForemanSelectAllWorker;
import org.tinygroup.tinypc.impl.ForemanSelectOneWorker;
import org.tinygroup.tinypc.impl.JobCenterLocal;

public class ScOperator implements CEPCoreOperator, RemoteObject {
	private static Logger logger = LoggerFactory.getLogger(ScOperator.class);
	private static final long serialVersionUID = 6587086087705014319L;
	private Map<Node, List<ServiceInfo>> arServices = new HashMap<Node, List<ServiceInfo>>();
	private JobCenter jobCenter = null;
	private int port;
	private String ip;

	public ScOperator(String ip, int port) {
		this.ip = ip;
		this.port = port;
		getJobCenter();
	}

	public void putArServices(Map<Node, List<ServiceInfo>> services) {
		for (Node node : services.keySet()) {
			arServices.put(node, services.get(node));
		}

	}
	public void putArServices(Node node, List<ServiceInfo> list) {
		arServices.put(node, list);
	}
	
	public void removeArServices(String nodeName) {
		Node removeNode = null;
		for (Node node : arServices.keySet()) {
			if(node.getNodeName().equals(nodeName)){
				removeNode = node;
				break;
			}
		}
		arServices.remove(removeNode);
		
	}

	public Map<Node, List<ServiceInfo>> gettArServices() {
		return arServices;
	}

	public JobCenter getJobCenter() {
		if (jobCenter == null) {
			try {
				jobCenter = new JobCenterLocal(ip, port);
				
			} catch (IOException e) {
				throw new RuntimeException("创建服务器失败",e);
			}
		}
		return jobCenter;
	}

	public void startCEPCore(CEPCore cep) {
		getJobCenter();
		init();
	}

	private void init() {
		try {
			ScWorker worker = new ScWorker(ip,this);
			jobCenter.registerWorker(worker);
			Foreman cepForeman = new ForemanSelectOneWorker(ScWork.WORK_TYPE);
			Foreman arForeman = new ForemanSelectAllWorker(ArWork.WORK_TYPE);
			jobCenter.registerForeman(cepForeman);
			jobCenter.registerForeman(arForeman);
		} catch (RemoteException e) {
			logger.errorMessage("初始化SC服务器的Foreman/ScWorker数据时出错",e);
			
		}
	}

	public void stopCEPCore(CEPCore cep) {
		// TODO Auto-generated method stub

	}
	
	public void setCEPCore(CEPCore cep) {
	}
}
