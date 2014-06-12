package org.tinygroup.cepcorepc.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.tinygroup.cepcore.CEPCore;
import org.tinygroup.cepcorepc.PcCepOperator;
import org.tinygroup.event.ServiceInfo;
import org.tinygroup.event.central.Node;
import org.tinygroup.tinypc.JobCenter;
import org.tinygroup.tinypc.impl.JobCenterLocal;

public class ScOperator implements PcCepOperator {
	private Map<Node, List<ServiceInfo>> arServices = new HashMap<Node, List<ServiceInfo>>();
	private JobCenter jobCenter = null;
	private String port = "8888";
	private String ip;

	public void putArServices(Map<Node, List<ServiceInfo>> services) {
		for (Node node : services.keySet()) {
			arServices.put(node, services.get(node));
		}

	}

	public Map<Node, List<ServiceInfo>> gettArServices() {
		return arServices;
	}

	public JobCenter getJobCenter() {
		if (jobCenter == null) {
			try {
				jobCenter = new JobCenterLocal(ip, Integer.parseInt(port));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return jobCenter;
	}

	public void startCEPCore(CEPCore cep) {
		getJobCenter();
	}

	public void stopCEPCore(CEPCore cep) {
		// TODO Auto-generated method stub

	}
}
