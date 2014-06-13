package org.tinygroup.cepcorepc;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.tinygroup.cepcore.EventProcessor;
import org.tinygroup.cepcorepc.impl.OperatorUtil;
import org.tinygroup.cepcorepc.impl.ServicePcEventProcessor;
import org.tinygroup.event.ServiceInfo;
import org.tinygroup.event.central.Node;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.tinypc.Warehouse;
import org.tinygroup.tinypc.Work;
import org.tinygroup.tinypc.Worker;

public class ArWorker implements Worker {
	private static Logger logger = LoggerFactory.getLogger(ArWorker.class);
	private static final long serialVersionUID = -8302946955163643890L;
	private PcCepCore core;
	private String id;
	private Map<String, EventProcessor> eventprocessors = new HashMap<String, EventProcessor>();

	public ArWorker(String id) {
		this.id = id;
	}

	public PcCepCore getCore() {
		return core;
	}

	public void setCore(PcCepCore core) {
		this.core = core;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() throws RemoteException {
		return id;
	}

	public Warehouse work(Work work) throws RemoteException {
		String regType = work.getInputWarehouse().get(OperatorUtil.TYPE_KEY);
		if (OperatorUtil.REG_KEY.equals(regType)) {
			return dealReg(work);
		} else {
			return dealUnReg(work);
		}

	}

	public Warehouse dealUnReg(Work work) {
		Node unregNode = work.getInputWarehouse().get(OperatorUtil.NODE_KEY);
		EventProcessor unregProcessor = eventprocessors.remove(unregNode.getNodeName());
		if (unregProcessor != null)
			core.unregisterEventProcessor(unregProcessor);
		return null;
	}

	public Warehouse dealReg(Work work) {
		Map<Node, List<ServiceInfo>> services = work.getInputWarehouse().get(
				OperatorUtil.SC_TO_AR_SERVICE_KEY);
		logger.logMessage(LogLevel.INFO, "开始注册接收到的其他服务器服务列表");
		for (Node node : services.keySet()) {
			List<ServiceInfo> list = services.get(node);
			String nodeName = node.getNodeName();
			logger.logMessage(LogLevel.INFO, "处理服务器:{0}", node.getNodeName());
			if (list == null || list.size() == 0) {
				logger.logMessage(LogLevel.INFO, "服务器:{0}服务列表为空，无需处理",
						node.getNodeName());
				continue;
			}
			ServicePcEventProcessor p = new ServicePcEventProcessor(
					nodeName, list, node);
			if(eventprocessors.containsKey(nodeName)){
				EventProcessor unregProcessor = eventprocessors.remove(nodeName);
				if (unregProcessor != null)
					core.unregisterEventProcessor(unregProcessor);
			}
			eventprocessors.put(node.getNodeName(), p);
			core.registerEventProcessor(p);
			logger.logMessage(LogLevel.INFO, "处理服务器:{0}完成", node.getNodeName());
		}
		logger.logMessage(LogLevel.INFO, "注册接收到的其他服务器服务列表完成");
		return null;
	}

	public boolean acceptWork(Work work) throws RemoteException {
		return ArWork.WORK_TYPE.equals(work.getType());
	}

	public String getType() throws RemoteException {
		return ArWork.WORK_TYPE;
	}

}
