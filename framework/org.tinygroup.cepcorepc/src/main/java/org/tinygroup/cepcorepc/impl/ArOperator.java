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
import org.tinygroup.springutil.SpringUtil;
import org.tinygroup.tinypc.JobCenter;
import org.tinygroup.tinypc.impl.JobCenterRemote;
import org.tinygroup.tinypc.impl.WarehouseDefault;

public class ArOperator  implements PcCepOperator{
	private String port = "8888";
	private String ip;
	private String nodeName;
	private String remoteIp;
	private String remotePort;
	private int weight = Node.DEFAULT_WEIGHT;
	private CEPCoreRemoteInterface remoteImpl;
	private Node localNode;

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
		localNode.setNodeName(nodeName);
		localNode.setType("");
		localNode.setWeight(weight);
		return localNode;
	}

	public void startCEPCore(CEPCore cep) {
		remoteImpl.startCEPCore(cep, getNode());
	}

	public void reg() throws RemoteException {
		PcCepCore cep = SpringUtil.getBean(PcCepCore.PC_CEP_CORE_BEAN);
		List<ServiceInfo> list = cep.getServiceInfos();
		JobCenter jobCenter = null;
		try {
			jobCenter = new JobCenterRemote(ip, Integer.parseInt(port),
					remoteIp, Integer.parseInt(remotePort));
		} catch (Exception e) {
			e.printStackTrace();
		}
		String workerId = cep.getNodeName();
		ArWorker worker = new ArWorker(workerId);
		jobCenter.registerWorker(worker);
		ScWork w = new ScWork();
		w.setWorker(workerId);
		w.setInputWarehouse(new WarehouseDefault());
		w.getInputWarehouse().put(OperatorUtil.AR_TO_SC_SERVICE_KEY, list);
		w.getInputWarehouse().put(OperatorUtil.NODE_KEY, getNode());
		try {
			jobCenter.doWork(w);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
