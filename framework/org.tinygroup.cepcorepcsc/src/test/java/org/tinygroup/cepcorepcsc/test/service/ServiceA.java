package org.tinygroup.cepcorepcsc.test.service;

import java.util.List;

import org.tinygroup.event.Parameter;
import org.tinygroup.event.ServiceInfo;

public class ServiceA implements ServiceInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7209953440614952126L;
	private String serviceId;
	
	public ServiceA(String s){
		serviceId = s;
	}
	public int compareTo(ServiceInfo o) {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getServiceId() {
		// TODO Auto-generated method stub
		return serviceId;
	}

	public List<Parameter> getParameters() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Parameter> getResults() {
		// TODO Auto-generated method stub
		return null;
	}

}
