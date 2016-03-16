package org.tinygroup.servicehttpchannel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.tinygroup.event.Parameter;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.service.config.ServiceComponent;
import org.tinygroup.service.config.ServiceComponents;
import org.tinygroup.service.config.ServiceMethod;
import org.tinygroup.service.config.ServiceParameter;
import org.tinygroup.service.exception.ServiceLoadException;
import org.tinygroup.service.util.ServiceUtil;

public class ServiceHttpChannelLoader {
	private final static Logger LOGGER = LoggerFactory
			.getLogger(ServiceHttpChannelLoader.class);
	private static Map<String, Class<?>> classMap = new HashMap<String, Class<?>>();
	private ServiceHttpChannelManager manager;
	
	static {
		classMap.put("int", int.class);
		classMap.put("short", short.class);
		classMap.put("byte", byte.class);
		classMap.put("char", char.class);
		classMap.put("long", long.class);
		classMap.put("double", double.class);
		classMap.put("float", float.class);
		classMap.put("boolean", boolean.class);
		classMap.put("void", void.class);
	}

	public void addService(List<ServiceComponents> list) {
		for(ServiceComponents components:list){
			addService(components);
		}
	}

	public void addService(ServiceComponents components) {
		for (ServiceComponent serviceComponent : components
				.getServiceComponents()) {
			addService(serviceComponent);
		}
	}

	public void addService(ServiceComponent serviceComponent) {
		String className = serviceComponent.getType();
		Class<?> classType = null;
		try {
			classType = Class.forName(className);
		} catch (ClassNotFoundException e) {
			LOGGER.errorMessage("解析服务时,获取服务对应class时出错", e);
			return;
		}
		for (ServiceMethod serviceMethod : serviceComponent.getServiceMethods()) {
			try {
				ServiceHttp item = new ServiceHttp();
				item.setServiceId(serviceMethod.getServiceId());
				item.setCategory(serviceMethod.getCategory());
				item.setMethodName(serviceMethod.getMethodName());
				item.setType(classType);
				getInputParameterNames(item, serviceMethod);
				getOutputParameterNames(item, serviceMethod);
				manager.addService(item);
			} catch (Exception e) {
				LOGGER.errorMessage("解析服务时出错,服务id:{}",e,serviceMethod.getServiceId());
			}

		}
	}

	private void getInputParameterNames(ServiceHttp item,
			ServiceMethod serviceMethod) throws ClassNotFoundException,
			ServiceLoadException {
		List<Parameter> inputParameterDescriptors = new ArrayList<Parameter>();
		// ==================入参处理 begin========================
		for (ServiceParameter serviceParameter : serviceMethod
				.getServiceParameters()) {
			String type = serviceParameter.getType();
			Class<?> parameterType = classMap.get(type);
			if (parameterType == null) {
				parameterType = Class.forName(type);
				classMap.put(type, parameterType);
			}
			Parameter descriptor = new Parameter();
			if (!ServiceUtil.assignFromSerializable(parameterType)) {
				throw new ServiceLoadException("服务返回值类型:<"
						+ parameterType.getName() + ">必须实现Serializable接口");
			}
			descriptor.setType(parameterType.getName());
			descriptor.setArray(serviceParameter.isArray());
			descriptor.setName(serviceParameter.getName());
			descriptor.setRequired(serviceParameter.isRequired());
			descriptor.setValidatorSence(serviceParameter.getValidatorScene());
			descriptor.setTitle(serviceParameter.getLocalName());
			descriptor.setCollectionType(serviceParameter.getCollectionType());
			descriptor.setDescription(serviceParameter.getDescription());
			inputParameterDescriptors.add(descriptor);
		}
		// ==================入参处理 end========================
		item.setParameters(inputParameterDescriptors);
	}

	private void getOutputParameterNames(ServiceHttp item,
			ServiceMethod serviceMethod) throws ClassNotFoundException,
			ServiceLoadException {
		// ==================出参处理 begin========================
		if (serviceMethod.getServiceResult() != null) {
			ServiceParameter serviceResult = serviceMethod.getServiceResult();
			String type = serviceResult.getType();
			Class<?> parameterType = classMap.get(type);
			if (parameterType == null) {
				parameterType = Class.forName(type);
				classMap.put(type, parameterType);
			}
			Parameter descriptor = new Parameter();
			if (!ServiceUtil.assignFromSerializable(parameterType)) {
				throw new ServiceLoadException("服务返回值类型:<"
						+ parameterType.getName() + ">必须实现Serializable接口");
			}
			descriptor.setType(parameterType.getName());
			descriptor.setArray(serviceResult.isArray());
			descriptor.setRequired(serviceResult.isRequired());
			descriptor.setName(serviceResult.getName());
			descriptor.setValidatorSence(serviceResult.getValidatorScene());
			descriptor.setTitle(serviceResult.getLocalName());
			descriptor.setDescription(serviceResult.getDescription());
			descriptor.setCollectionType(serviceResult.getCollectionType());
			List<Parameter> outputParameterDescriptors = new ArrayList<Parameter>();
			outputParameterDescriptors.add(descriptor);
			item.setResults(outputParameterDescriptors);
		}
		// ==================出参处理 end========================
	}

	public ServiceHttpChannelManager getManager() {
		return manager;
	}

	public void setManager(ServiceHttpChannelManager manager) {
		this.manager = manager;
	}
	
}
