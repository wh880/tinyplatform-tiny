package org.tinygroup.cepcorepc;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import org.tinygroup.cepcorepc.impl.OperatorUtil;
import org.tinygroup.cepcorepc.impl.ServicePcEventProcessor;
import org.tinygroup.event.ServiceInfo;
import org.tinygroup.event.central.Node;
import org.tinygroup.springutil.SpringUtil;
import org.tinygroup.tinypc.Warehouse;
import org.tinygroup.tinypc.Work;
import org.tinygroup.tinypc.Worker;

public class ArWorker implements Worker {

	private static final long serialVersionUID = -8302946955163643890L;

	private String id;

	public ArWorker(String id) {
		this.id = id;
	}

	public String getId() throws RemoteException {
		return id;
	}

	public Warehouse work(Work work) throws RemoteException {
		Map<Node, List<ServiceInfo>> services = work.getInputWarehouse().get(
				OperatorUtil.SC_TO_AR_SERVICE_KEY);
		Node remoteNode = work.getInputWarehouse().get(OperatorUtil.NODE_KEY);
		PcCepCore core = SpringUtil.getBean(PcCepCore.PC_CEP_CORE_BEAN);
		for (Node node : services.keySet()) {
			ServicePcEventProcessor p = new ServicePcEventProcessor(
					node.getNodeName(), services.get(node), remoteNode);
			core.registerEventProcessor(p);
		}

		return null;
	}

	public boolean acceptWork(Work work) throws RemoteException {
		return ArWork.WORK_TYPE.equals(work.getType());
	}

	public String getType() throws RemoteException {
		return ArWork.WORK_TYPE;
	}

}
