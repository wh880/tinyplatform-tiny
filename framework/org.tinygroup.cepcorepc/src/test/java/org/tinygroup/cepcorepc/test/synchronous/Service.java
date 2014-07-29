package org.tinygroup.cepcorepc.test.synchronous;

import java.util.ArrayList;
import java.util.List;

import org.tinygroup.event.Parameter;
import org.tinygroup.event.ServiceInfo;

public class Service implements ServiceInfo{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String id;
	
	public Service(String id){
		this.id = id;
	}
	public int compareTo(ServiceInfo o) {
		return 0;
	}

	public String getServiceId() {
		return id;
	}

	public List<Parameter> getParameters() {
		return new ArrayList<Parameter>();
	}

	public List<Parameter> getResults() {
		return new ArrayList<Parameter>();
	}

	public String getCategory() {
		return null;
	}

}
