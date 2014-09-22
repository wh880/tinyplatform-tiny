package org.tinygroup.tinynetty.remote;

import java.util.ArrayList;
import java.util.List;

import org.tinygroup.cepcore.CEPCore;
import org.tinygroup.cepcore.EventProcessor;
import org.tinygroup.cepcore.exception.CEPConnectException;
import org.tinygroup.cepcorenetty.operator.ArUnregTrigger;
import org.tinygroup.event.Event;
import org.tinygroup.event.ServiceInfo;
import org.tinygroup.event.central.Node;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.netty.daemon.DaemonUtils;
import org.tinygroup.netty.exception.InterruptedRuntimeException;
import org.tinygroup.tinynetty.EventClient;

public class NettyEventProcessor implements EventProcessor {
	private static Logger logger = LoggerFactory
			.getLogger(NettyEventProcessor.class);
	private EventClientDaemonRunnable client;
	private Node remoteNode;
	private int timeout = 10;
	private List<ServiceInfo> list = new ArrayList<ServiceInfo>();
	private CEPCore core;
 	public NettyEventProcessor(Node remoteNode,List<ServiceInfo> list) {
 		this.list = list;
 		this.remoteNode = remoteNode;
		
	}
	
	private void initClient(){
		client = getNewClient(remoteNode);
	}

	public void process(Event event) {
		if (client == null) {
			initClient();
		}
		EventClient eventClient = client.getClient();
		logger.logMessage(LogLevel.INFO,
				"发送请求,目标节点{0}:{1}:{2},请求信息:[serviceId:{3}]",
				remoteNode.getIp(), remoteNode.getPort(), remoteNode
						.getNodeName(), event.getServiceRequest()
						.getServiceId());
		try {

			int i = 1;
			while (!eventClient.isReady()) {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// 此处无须处理
				}
				i++;
				if (i > timeout) {
					break;
				}
			}
			event = eventClient.sendObject(event);

			logger.logMessage(LogLevel.INFO,
					"请求成功,目标节点{0}:{1}:{2},请求信息:[serviceId:{3}]", remoteNode
							.getIp(), remoteNode.getPort(), remoteNode
							.getNodeName(), event.getServiceRequest()
							.getServiceId());

		} catch (InterruptedRuntimeException e) {
			logger.logMessage(LogLevel.ERROR,
					"请求失败,目标节点{0}:{1}:{2},请求信息:[serviceId:{3},信息:{5}",
					remoteNode.getIp(), remoteNode.getPort(), remoteNode
							.getNodeName(), event.getServiceRequest()
							.getServiceId(), e.getMessage());
			stopConnect();
			throw new CEPConnectException(e, remoteNode);
		} catch (RuntimeException e) {
			stopConnect();
			throw e;
		}
	}

	private void stopConnect() {
		client.stop();
		client = null;
	}

	public void setCepCore(CEPCore cepCore) {
		core = cepCore;
		initClient();
	}

	public List<ServiceInfo> getServiceInfos() {
		return list;
	}

	public String getId() {
		return remoteNode.toString();
	}

	public int getType() {
		return TYPE_REMOTE;
	}

	public int getWeight() {
		return remoteNode.getWeight();
	}

	public List<String> getRegex() {
		// TODO Auto-generated method stub
		return null;
	}

	private EventClientDaemonRunnable getNewClient(Node remoteNode) {
		String nodeInfo = remoteNode.toString();
		EventClientDaemonRunnable client = new EventClientDaemonRunnable(
				remoteNode.getIp(), Integer.parseInt(remoteNode.getPort()),false);
		client.addPostEventTrigger(new ArUnregTrigger(core, this));
		DaemonUtils.daemon(nodeInfo, client);
		return client;
	}
}
