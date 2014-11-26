package org.tinygroup.servicebasicservice;

import java.util.ArrayList;
import java.util.List;

import org.tinygroup.service.registry.ServiceRegistry;
import org.tinygroup.service.registry.ServiceRegistryItem;

public class BasicService {
	private ServiceRegistry serviceRegistry;

	public int getServiceCount() {
		return serviceRegistry.getServiceRegistryItems().size();
	}

	public List<ServiceRegistryItem> getServiceRegistryItems() {
		List<ServiceRegistryItem> list = new ArrayList<ServiceRegistryItem>();
		for (ServiceRegistryItem item : serviceRegistry
				.getServiceRegistryItems()) {
			list.add(item);
		}
		return list;
	}

	public ServiceRegistryItem getServiceRegistryItem(String id) {
		return serviceRegistry.getServiceRegistryItem(id);
	}

	public ServiceRegistry getServiceRegistry() {
		return serviceRegistry;
	}

	public void setServiceRegistry(ServiceRegistry serviceRegistry) {
		this.serviceRegistry = serviceRegistry;
	}

}
