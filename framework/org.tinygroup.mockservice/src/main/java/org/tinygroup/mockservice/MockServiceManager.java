package org.tinygroup.mockservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.tinygroup.event.ServiceInfo;

public class MockServiceManager {
	private Map<String, MockService> map = new HashMap<String, MockService>();
	private List<ServiceInfo> infos = new ArrayList<ServiceInfo>();

	public void addMockService(MockService service) {
		map.put(service.getServiceId(), service);
		infos.add(service);
	}

	public MockService getMockService(String id) {
		return map.get(id);
	}

	public List<ServiceInfo> getInfos() {
		return infos;
	}
}
