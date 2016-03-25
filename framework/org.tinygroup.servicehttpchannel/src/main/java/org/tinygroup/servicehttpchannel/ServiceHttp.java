package org.tinygroup.servicehttpchannel;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.tinygroup.event.AbstractServiceInfo;
import org.tinygroup.event.Parameter;

public class ServiceHttp extends AbstractServiceInfo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4345831876683679140L;
	private String serviceId;
	private String category;
	private List<Parameter> parameters = new ArrayList<Parameter>();
	private List<Parameter> results = new ArrayList<Parameter>();
	transient Type resultType;
	transient Class<?> type;
	transient String methodName;


	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public List<Parameter> getParameters() {
		return parameters;
	}

	public void setParameters(List<Parameter> parameters) {
		this.parameters = parameters;
	}

	public List<Parameter> getResults() {
		return results;
	}

	public void setResults(List<Parameter> results) {
		this.results = results;
	}

	

	public void setType(Class<?> type) {
		this.type = type;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	
	
	public String getResultName(){
		if(results.size()==0){
			return null;
		}
		return results.get(0).getName();
	}
	
}
