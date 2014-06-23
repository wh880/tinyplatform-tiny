package org.tinygroup.cepcorepcsc.impl;

import java.util.ArrayList;
import java.util.List;

import org.tinygroup.cepcore.CEPCore;
import org.tinygroup.cepcore.CEPCoreRemoteInterface;
import org.tinygroup.cepcore.EventProcessor;
import org.tinygroup.event.Event;
import org.tinygroup.event.ServiceInfo;
import org.tinygroup.event.central.Node;

public class ServicePcEventProcessor implements EventProcessor {
	private Node remoteNode;
	private String nodeName;
	private List<ServiceInfo> services = new ArrayList<ServiceInfo>();
	private CEPCoreRemoteInterface remoteImpl;
	public ServicePcEventProcessor(String nodeName, List<ServiceInfo> list,Node remoteNode,CEPCoreRemoteInterface remoteImpl) {
		this.services = list;
		this.nodeName = nodeName;
		this.remoteNode = remoteNode;
		this.remoteImpl = remoteImpl;
	}
	
	public void process(Event event) {
		remoteImpl.remoteprocess(event, remoteNode);
	}

	public void setCepCore(CEPCore cepCore) {

	}

	public List<ServiceInfo> getServiceInfos() {
		return services;
	}

	public String getId() {
		return nodeName;
	}

	public int getType() {
		return EventProcessor.TYPE_CHANNEL;
	}

	public int getWeight() {
		// TODO Auto-generated method stub
		return 0;
	}

}
