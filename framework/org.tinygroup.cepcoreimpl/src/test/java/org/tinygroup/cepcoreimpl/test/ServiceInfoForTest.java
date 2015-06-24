package org.tinygroup.cepcoreimpl.test;

import org.tinygroup.event.Parameter;
import org.tinygroup.event.ServiceInfo;

import java.util.ArrayList;
import java.util.List;

public class ServiceInfoForTest implements ServiceInfo{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6510299323984908078L;
	private String serviceId;
	private List<Parameter> in =new ArrayList<Parameter>(); 
	private List<Parameter> out =new ArrayList<Parameter>(); 
	
	public int compareTo(ServiceInfo o) {
		return 0;
	}

	public String getServiceId() {
		return serviceId;
	}
	
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public List<Parameter> getParameters() {
		return in;
	}

	public List<Parameter> getResults() {
		return out;
	}

	public String getCategory() {
		return null;
	}
	

}
