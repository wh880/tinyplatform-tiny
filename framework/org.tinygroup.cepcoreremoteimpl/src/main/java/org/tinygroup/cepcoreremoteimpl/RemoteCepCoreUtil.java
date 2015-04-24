package org.tinygroup.cepcoreremoteimpl;

import java.util.ArrayList;
import java.util.List;

import org.tinygroup.cepcoreremoteimpl.node.CEPCoreClientImpl;
import org.tinygroup.event.Event;
import org.tinygroup.event.central.Node;

public class RemoteCepCoreUtil {
	private static long requestTimeOut = 1000;
	
	private static List<String> scs = new ArrayList<String>();

	public static long getRequestTimeOut() {
		return requestTimeOut;
	}

	public static void setRequestTimeOut(long requestTimeOut) {
		RemoteCepCoreUtil.requestTimeOut = requestTimeOut;
	}

	public static Event sendEvent(CEPCoreClientImpl client, Event event) {
		return client.sentEvent(event);
	}

	public static void regScAddress(int remotePort, String remoteHost) {
		scs.add(remoteHost + ":" + remotePort);
	}

	public static boolean checkSc(int remotePort, String remoteHost) {
		return scs.contains(remoteHost + ":" + remotePort);
	}

	public static Event sendEvent(Node remoteNode, CEPCoreClientImpl client,
			Event event) {
		return client.sentEvent(event);
	}

	public static CEPCoreClientImpl getClient(Node remoteNode){
		CEPCoreClientImpl client = new CEPCoreClientImpl(Integer.parseInt(remoteNode.getPort()),
				remoteNode.getIp(),false);
		client.start();
		return client;
	}
}
