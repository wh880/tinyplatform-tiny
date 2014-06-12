package org.tinygroup.cepcorepc.impl;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import org.tinygroup.cepcore.CEPCore;
import org.tinygroup.cepcore.CEPCoreRemoteInterface;
import org.tinygroup.cepcore.exception.CEPRunException;
import org.tinygroup.event.Event;
import org.tinygroup.event.ServiceInfo;
import org.tinygroup.event.central.Node;
import org.tinygroup.xmlparser.node.XmlNode;

public class PcCepCoreImpl2 extends PcCepCoreImpl {
	private CEPCoreRemoteInterface remoteImpl;

	public Event remoteProcess(Event event) {
		// TODO Auto-generated method stub
		return null;
	}

	public void registeNode(Node node, Node sourceNode) {
		// TODO Auto-generated method stub

	}

	public void registeNode(List<Node> nodes, Node sourceNode) {
		// TODO Auto-generated method stub

	}

	public void unregisteNode(Node node, Node sourceNode) {
		// TODO Auto-generated method stub

	}

	public boolean check(Node node) {
		// TODO Auto-generated method stub
		return false;
	}

	public void unregisteNode(List<Node> nodes, Node sourceNode) {
		// TODO Auto-generated method stub

	}

	public void addCentralNodes(List<Node> centralNodes) {
		// TODO Auto-generated method stub

	}

	public void addCentralNode(Node centralNode) {
		// TODO Auto-generated method stub

	}

	public void setConfig(XmlNode xml) {
		// TODO Auto-generated method stub

	}

	public Event remoteprocess(Event event, Node remoteNode) {
		// TODO Auto-generated method stub
		return null;
	}

	public void startCEPCore(CEPCore cep) {
		Node node = getNode();
		remoteImpl.startCEPCore(cep, node);
	}

	public void stopCEPCore(CEPCore cep) {
		Node node = getNode();// 获取本地node
		remoteImpl.stopCEPCore(cep, node);
	}

	public ServiceInfo getServiceInfo(String serviceId) {
		// TODO Auto-generated method stub
		return null;
	}

	private String port;
	private String ip;
	private String nodeName;
	private int weight = Node.DEFAULT_WEIGHT;
	protected Node getNode() {
		Node node = new Node();
		String lIp = this.ip;
		if (lIp == null || "".equals(lIp)) {
			try {
				lIp = InetAddress.getLocalHost().getHostAddress();
			} catch (UnknownHostException e) {
				throw new CEPRunException(e, "cepcore.getIpError");
			}
		}
		node.setIp(lIp);
		if (port == null || "".equals(port)) {
			port = DEFAULT_PORT;
		}
		node.setPort(port);
		node.setNodeName(nodeName);
		node.setType("");
		node.setWeight(weight);
		return node;
	}
}
