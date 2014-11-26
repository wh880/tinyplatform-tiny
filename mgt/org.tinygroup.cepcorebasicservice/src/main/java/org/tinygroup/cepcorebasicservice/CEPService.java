package org.tinygroup.cepcorebasicservice;

import java.util.List;

import org.tinygroup.cepcore.CEPCore;
import org.tinygroup.event.ServiceInfo;

public class CEPService {

	private CEPCore core;

	public CEPCore getCore() {
		return core;
	}

	public void setCore(CEPCore core) {
		this.core = core;
	}
	
	public int getServiceCount(){
		return core.getServiceInfos().size();
	}
	
	public List<ServiceInfo> getServiceInfos(){
		return core.getServiceInfos();
	}
	
	public ServiceInfo getServiceInfo(String serviceId){
		return core.getServiceInfo(serviceId);
	}
	
}
