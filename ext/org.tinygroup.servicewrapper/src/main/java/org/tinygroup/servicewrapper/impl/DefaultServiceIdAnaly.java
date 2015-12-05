package org.tinygroup.servicewrapper.impl;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.annotation.AnnotationUtils;
import org.tinygroup.servicewrapper.ServiceIdAnaly;
import org.tinygroup.servicewrapper.ServiceWrapperConfigManager;
import org.tinygroup.servicewrapper.annotation.ServiceWrapper;
import org.tinygroup.servicewrapper.config.MethodConfig;
import org.tinygroup.servicewrapper.config.ParameterType;

public class DefaultServiceIdAnaly implements ServiceIdAnaly {

	private ServiceWrapperConfigManager manager;
	
	public ServiceWrapperConfigManager getManager() {
		return manager;
	}

	public void setManager(ServiceWrapperConfigManager manager) {
		this.manager = manager;
	}

	public String analyMethod(Method method) {
		String serviceId = manager.getServiceIdWithMethod(method);
		if(serviceId==null){
			ServiceWrapper serviceWrapper = AnnotationUtils.findAnnotation(method,
					ServiceWrapper.class);
			if(serviceWrapper==null){
				serviceId=method.getName();//如果不存在ServiceWrapper注解，那么serviceId取自方法名
			}else{
				serviceId = serviceWrapper.serviceId();
			}
			if(serviceId!=null){
				MethodConfig methodConfig = createMethodConfig(method);
				methodConfig.setServiceId(serviceId);
				manager.putServiceWrapper(methodConfig);
			}
		}
		return serviceId;
	}
	
	private MethodConfig createMethodConfig(Method method) {
		MethodConfig serviceWrapperConfig = new MethodConfig();
		serviceWrapperConfig.setType(method.getDeclaringClass().getName());
		serviceWrapperConfig.setMethodName(method.getName());
		Class<?>[] types=method.getParameterTypes();
		List<ParameterType> parameterTypeList = new ArrayList<ParameterType>();
		for (int i = 0; i < types.length; i++) {
			Class<?> type = types[i];
			ParameterType ptype = new ParameterType();
			ptype.setType(type.getName());
			parameterTypeList.add(ptype);
		}
		serviceWrapperConfig.setParamTypes(parameterTypeList);
		return serviceWrapperConfig;
	}

}
