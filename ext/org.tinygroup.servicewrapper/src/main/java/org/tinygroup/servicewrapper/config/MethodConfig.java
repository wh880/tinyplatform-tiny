package org.tinygroup.servicewrapper.config;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("method-config")
public class MethodConfig {
	
	@XStreamAlias("service-id")
	@XStreamAsAttribute
	private String serviceId;
	
	@XStreamAsAttribute
	private String type;
	
	@XStreamAsAttribute
	@XStreamAlias("method-name")
	private String methodName;
	
	@XStreamImplicit
	private List<ParameterType> paramTypes;

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	
	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public List<ParameterType> getTypes() {
		if(paramTypes==null){
			paramTypes=new ArrayList<ParameterType>();
		}
		return paramTypes;
	}

	public List<ParameterType> getParamTypes() {
		return paramTypes;
	}

	public void setParamTypes(List<ParameterType> paramTypes) {
		this.paramTypes = paramTypes;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
