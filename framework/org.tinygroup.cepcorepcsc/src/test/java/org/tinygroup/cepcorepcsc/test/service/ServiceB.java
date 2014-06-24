package org.tinygroup.cepcorepcsc.test.service;

import java.util.List;

import org.tinygroup.event.Parameter;
import org.tinygroup.event.ServiceInfo;

public class ServiceB implements ServiceInfo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4823466287226452738L;
	private String serviceId;
	public ServiceB(String s){
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
	public String getCategory() {
		// TODO Auto-generated method stub
		return null;
	}

}
