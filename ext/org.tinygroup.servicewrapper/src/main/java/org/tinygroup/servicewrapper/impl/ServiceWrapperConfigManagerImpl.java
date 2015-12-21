package org.tinygroup.servicewrapper.impl;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.tinygroup.servicewrapper.ServiceWrapperConfigManager;
import org.tinygroup.servicewrapper.config.MethodConfigs;
import org.tinygroup.servicewrapper.config.MethodConfig;
import org.tinygroup.servicewrapper.config.MethodDescription;
import org.tinygroup.servicewrapper.config.ParameterType;

public class ServiceWrapperConfigManagerImpl implements
		ServiceWrapperConfigManager {
	private Map<MethodDescription, String> methodActionMap = new HashMap<MethodDescription, String>();

	public void addServiceWrappers(MethodConfigs serviceWrappers) {
		List<MethodConfig> methodConfigList = serviceWrappers
				.getMethodConfigs();
		for (MethodConfig methodConfig : methodConfigList) {
			putServiceWrapper(methodConfig);
		}
	}

	public void removeServiceWrappers(MethodConfigs serviceWrappers) {
		List<MethodConfig> methodConfigList = serviceWrappers
				.getMethodConfigs();
		for (MethodConfig methodConfig : methodConfigList) {
			MethodDescription description = new MethodDescription();
			description.setClassName(methodConfig.getType());
			description.setMethodName(methodConfig.getMethodName());
			List<ParameterType> paramTypes = methodConfig.getParamTypes();
			StringBuilder typeBuilder = new StringBuilder();
			for (int i = 0; i < paramTypes.size(); i++) {
				typeBuilder.append(paramTypes.get(i).getType());
				if (i < paramTypes.size() - 1) {
					typeBuilder.append(";");
				}
			}
			description.setParameterTypes(typeBuilder.toString());
			methodActionMap.remove(description);
		}
	}

	public String getServiceIdWithMethod(Method method) {
		MethodDescription description = MethodDescription
				.createMethodDescription(method);
		return methodActionMap.get(description);
	}

	public void putServiceWrapper(MethodConfig serviceWrapper) {
		MethodDescription description = new MethodDescription();
		description.setClassName(serviceWrapper.getType());
		description.setMethodName(serviceWrapper.getMethodName());
		List<ParameterType> paramTypes = serviceWrapper.getParamTypes();
		StringBuilder typeBuilder = new StringBuilder();
		for (int i = 0; i < paramTypes.size(); i++) {
			typeBuilder.append(paramTypes.get(i).getType());
			if (i < paramTypes.size() - 1) {
				typeBuilder.append(";");
			}
		}
		description.setParameterTypes(typeBuilder.toString());
		methodActionMap.put(description, serviceWrapper.getServiceId());
	}

}
