package org.tinygroup.cepcorepc.impl;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.util.List;

import org.tinygroup.cepcore.CEPCore;
import org.tinygroup.cepcore.CEPCoreRemoteInterface;
import org.tinygroup.cepcore.exception.CEPRunException;
import org.tinygroup.cepcorepc.ArWorker;
import org.tinygroup.cepcorepc.PcCepCore;
import org.tinygroup.cepcorepc.PcCepOperator;
import org.tinygroup.cepcorepc.ScWork;
import org.tinygroup.event.ServiceInfo;
import org.tinygroup.event.central.Node;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.rpc.CEPCoreRMIRemoteImpl;
import org.tinygroup.springutil.SpringUtil;
import org.tinygroup.tinypc.JobCenter;
import org.tinygroup.tinypc.impl.JobCenterRemote;
import org.tinygroup.tinypc.impl.WarehouseDefault;

public class ArOperator implements PcCepOperator {
	private String port = "8888";
	private String ip;
	private String remoteIp;
	private String remotePort;
	private int weight = Node.DEFAULT_WEIGHT;
	private CEPCoreRemoteInterface remoteImpl = new CEPCoreRMIRemoteImpl();
	private Node localNode;
	private JobCenter jobCenter;
	private CEPCore cep;
	private ArWorker arWorker;
	private Logger logger = LoggerFactory.getLogger(ArOperator.class);

	public ArOperator(String ip, String port, String scIp, String scPort) {
		this.ip = ip;
		this.port = port;
		this.remoteIp = scIp;
		this.remotePort = scPort;
	}

	public void setCep(CEPCore cep){
		this.cep =cep;
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
			logger.errorMessage("添加重连触发器时出错",e);
		}
		remoteImpl.startCEPCore(cep, getNode());
		try {
			reg();
		} catch (RemoteException e) {
			logger.errorMessage("向远端服务器注册时出错",e);
		}
	}
	public void init(){
		String workerId = cep.getNodeName();
		arWorker = new ArWorker(workerId);
		arWorker.setCore(cep);
		try {
			jobCenter.registerWorker(arWorker);
		} catch (RemoteException e) {
			logger.errorMessage("注册ArWorker时出错",e);
		}
	}
	public void reg() throws RemoteException {
		if (cep == null)
			cep = SpringUtil.getBean(PcCepCore.PC_CEP_CORE_BEAN);
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
			logger.errorMessage("向服务器分发执行服务注册任务时出错",e);
		}
	}
	public void unreg(){
		if (cep == null)
			cep = SpringUtil.getBean(PcCepCore.PC_CEP_CORE_BEAN);
		ScWork w = new ScWork();
		w.setInputWarehouse(new WarehouseDefault());
		w.getInputWarehouse().put(OperatorUtil.NODE_KEY, getNode());
		w.getInputWarehouse().put(OperatorUtil.TYPE_KEY, OperatorUtil.UNREG_KEY);
		try {
			jobCenter.doWork(w);
		} catch (IOException e) {
			logger.errorMessage("向服务器分发执行服务注销任务时出错",e);
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

	public void stopCEPCore(CEPCore cep) {
		try {
			jobCenter.unregisterWorker(arWorker);
			unreg();
		} catch (RemoteException e) {
			logger.errorMessage("服务器执行注销时出错",e);
		}
		remoteImpl.stopCEPCore(cep, getNode());
	}
}
