package org.tinygroup.cepcorepcsc.impl;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.util.List;

import org.tinygroup.cepcore.CEPCore;
import org.tinygroup.cepcore.CEPCoreOperator;
import org.tinygroup.cepcore.CEPCoreRemoteInterface;
import org.tinygroup.cepcore.EventProcessorChoose;
import org.tinygroup.cepcore.exception.CEPRunException;
import org.tinygroup.cepcorepc.impl.OperatorUtil;
import org.tinygroup.cepcorepcsc.ArWorker;
import org.tinygroup.cepcorepcsc.ScWork;
import org.tinygroup.event.ServiceInfo;
import org.tinygroup.event.central.Node;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.rpc.CEPCoreRMIRemoteImpl;
import org.tinygroup.springutil.SpringUtil;
import org.tinygroup.tinypc.JobCenter;
import org.tinygroup.tinypc.impl.JobCenterRemote;
import org.tinygroup.tinypc.impl.WarehouseDefault;

public class ArOperator implements CEPCoreOperator {
	
	private static Logger logger = LoggerFactory.getLogger(ArOperator.class);
	private CEPCoreRemoteInterface remoteImpl = new CEPCoreRMIRemoteImpl();
	
	private String port = "8888";
	private String ip;
	private String remoteIp;
	private String remotePort;
	private int weight = EventProcessorChoose.DEFAULT_WEIGHT;
	
	private Node localNode;
	private JobCenter jobCenter;
	private CEPCore cep;
	private ArWorker arWorker;
	
	public ArOperator(){}
	
	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getRemoteIp() {
		return remoteIp;
	}

	public void setRemoteIp(String remoteIp) {
		this.remoteIp = remoteIp;
	}

	public String getRemotePort() {
		return remotePort;
	}

	public void setRemotePort(String remotePort) {
		this.remotePort = remotePort;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}
	
	public ArOperator(String ip, String port, String scIp, String scPort,int weight) {
		this.ip = ip;
		this.port = port;
		this.remoteIp = scIp;
		this.remotePort = scPort;
		this.weight = weight;
	}
	
	protected Node getNode() {
		if (localNode != null) {
			return localNode;
		}
		localNode = new Node();
		String lIp = this.ip;
		if (lIp == null || "".equals(lIp)) {
			try {
				lIp = InetAddress.getLocalHost().getHostAddress();
			} catch (UnknownHostException e) {
				throw new CEPRunException(e, "cepcore.getIpError");
			}
		}
		localNode.setIp(lIp);
		if (port == null || "".equals(port)) {
			port = "8888";
		}
		localNode.setPort(port);
		localNode.setNodeName(cep.getNodeName());
		localNode.setType(Node.CEP_NODE);
		localNode.setWeight(weight);
		return localNode;
	}

	public void startCEPCore(CEPCore cep) {
		JobCenter j = getJobCenter();
		init();
		try {
			j.getRmiServer().addTrigger(new ArReConnectTrigger(this));
		} catch (RemoteException e) {
			logger.errorMessage("添加重连触发器时出错", e);
		}
		remoteImpl.startCEPCore(cep, getNode());
		try {
			reg();
		} catch (RemoteException e) {
			logger.errorMessage("向远端服务器注册时出错", e);
		}
	}

	public void init() {
		String workerId = cep.getNodeName();
		arWorker = new ArWorker(workerId,this);
		try {
			jobCenter.registerWorker(arWorker);
		} catch (RemoteException e) {
			logger.errorMessage("注册ArWorker时出错", e);
		}
	}

	public void reg() throws RemoteException {
		if (cep == null)
			cep = SpringUtil.getBean(CEPCore.CEP_CORE_BEAN);
		List<ServiceInfo> list = cep.getServiceInfos();
		ScWork w = new ScWork();
		w.setWorker(cep.getNodeName());
		w.setInputWarehouse(new WarehouseDefault());
		w.getInputWarehouse().put(OperatorUtil.AR_TO_SC_SERVICE_KEY, list);
		w.getInputWarehouse().put(OperatorUtil.NODE_KEY, getNode());
		w.getInputWarehouse().put(OperatorUtil.TYPE_KEY, OperatorUtil.REG_KEY);
		try {
			jobCenter.doWork(w);
		} catch (IOException e) {
			logger.errorMessage("向服务器分发执行服务注册任务时出错", e);
		}
	}

	public void unreg() {
		if (cep == null)
			cep = SpringUtil.getBean(CEPCore.CEP_CORE_BEAN);
		ScWork w = new ScWork();
		w.setInputWarehouse(new WarehouseDefault());
		w.getInputWarehouse().put(OperatorUtil.NODE_KEY, getNode());
		w.getInputWarehouse()
				.put(OperatorUtil.TYPE_KEY, OperatorUtil.UNREG_KEY);
		try {
			jobCenter.doWork(w);
		} catch (IOException e) {
			logger.errorMessage("向服务器分发执行服务注销任务时出错", e);
		}
	}

	private JobCenter getJobCenter() {
		if (jobCenter != null) {
			return jobCenter;
		}
		try {
			jobCenter = new JobCenterRemote(ip, Integer.parseInt(port),
					remoteIp, Integer.parseInt(remotePort));
			return jobCenter;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public CEPCoreRemoteInterface getRemoteImpl() {
		return remoteImpl;
	}

	public void stopCEPCore(CEPCore cep) {
		try {
			jobCenter.unregisterWorker(arWorker);
			unreg();
		} catch (RemoteException e) {
			logger.errorMessage("服务器执行注销时出错", e);
		}
		remoteImpl.stopCEPCore(cep, getNode());
	}

	public void setCEPCore(CEPCore cep) {
		this.cep = cep;
	}
}
