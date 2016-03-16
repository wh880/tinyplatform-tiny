package org.tinygroup.servicehttpchannel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.tinygroup.event.ServiceInfo;

public class ServiceHttpChannelManager {
	private Map<String, ServiceHttp> map = new HashMap<String, ServiceHttp>();
	private List<ServiceInfo> infos = new ArrayList<ServiceInfo>();

	public void addService(ServiceHttp service) {
		map.put(service.getServiceId(), service);
		infos.add(service);
	}

	public ServiceHttp getService(String id) {
		return map.get(id);
	}

	public List<ServiceInfo> getInfos() {
		return infos;
	}
}
