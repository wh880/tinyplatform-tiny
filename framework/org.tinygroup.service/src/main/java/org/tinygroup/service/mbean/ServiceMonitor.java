package org.tinygroup.service.mbean;

import org.tinygroup.service.registry.ServiceRegistry;

public class ServiceMonitor implements ServiceMonitorMBean{

	ServiceRegistry serviceRegistry;
	
	public ServiceRegistry getServiceRegistry() {
		return serviceRegistry;
	}

	public void setServiceRegistry(ServiceRegistry serviceRegistry) {
		this.serviceRegistry = serviceRegistry;
	}
	
	public Integer getServiceTotal() {
		Integer total = 0;
		if(!serviceRegistry.getServiceRegistryItems().isEmpty()){
			total =  serviceRegistry.getServiceRegistryItems().size();
		}
		return total;
	}
	
	public boolean isExistLocalService(String serviceId) {
		boolean exist = false;
		if(!serviceRegistry.getServiceRegistryItems().isEmpty()){
			if(serviceRegistry.getServiceRegistryItem(serviceId)!=null){
				exist = true;
			}
		}
		return exist;
	}
}

	