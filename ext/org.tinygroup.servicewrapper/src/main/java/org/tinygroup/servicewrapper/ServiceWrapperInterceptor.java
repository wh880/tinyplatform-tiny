package org.tinygroup.servicewrapper;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.core.annotation.AnnotationUtils;
import org.tinygroup.cepcore.CEPCore;
import org.tinygroup.commons.tools.Assert;
import org.tinygroup.commons.tools.StringUtil;
import org.tinygroup.context.Context;
import org.tinygroup.context.impl.ContextImpl;
import org.tinygroup.event.Event;
import org.tinygroup.event.Parameter;
import org.tinygroup.event.ServiceInfo;
import org.tinygroup.event.ServiceRequest;
import org.tinygroup.servicewrapper.annotation.ServiceWrapper;
import org.tinygroup.servicewrapper.config.MethodConfig;
import org.tinygroup.servicewrapper.config.ParameterType;

/**
 * 拦截器
 *
 */
public class ServiceWrapperInterceptor implements MethodInterceptor {
	private CEPCore core;
	
	private ServiceWrapperConfigManager manager;

	public CEPCore getCore() {
		return core;
	}

	public void setCore(CEPCore core) {
		this.core = core;
	}

	public Object invoke(MethodInvocation invocation) throws Throwable {
		Object[] args = invocation.getArguments();
		Method method = invocation.getMethod();
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
		if(StringUtil.isBlank(serviceId)){
			throw new RuntimeException(String.format("方法:%s,未发布成服务,不能进行访问", method));
		}
		Context context = new ContextImpl();
		ServiceInfo serviceInfo = core.getServiceInfo(serviceId);
		List<Parameter> params = serviceInfo.getParameters();
		int size = params != null ? params.size() : 0;
		int argsize = args != null ? args.length : 0;
		if (params != null && args != null) {
			Assert.assertTrue(size == argsize, "服务配置描述的参数个数与实际方法的参数个数不一致");// 参数个数必须一致
		}
		for (int i = 0; i < size; i++) {
			context.put(params.get(i).getName(), args[i]);
		}
		return callServiceAndCallBack(serviceId, context);

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

	private Event getEvent(String serviceId, Context context) throws Exception {
		Event event = new Event();
		event.setEventId(UUID.randomUUID().toString());
		ServiceRequest serviceRequest = new ServiceRequest();
		serviceRequest.setContext(context);
		serviceRequest.setServiceId(serviceId);
		event.setServiceRequest(serviceRequest);
		return event;
	}

	private <T> T callServiceAndCallBack(String serviceId, Context context)
			throws Exception {
		Event event = getEvent(serviceId, context);
		core.process(event);
		ServiceInfo info = core.getServiceInfo(serviceId);
		List<Parameter> resultsParam = info.getResults();
		if (resultsParam == null || resultsParam.size() == 0) {
			return null;
		}
		return event.getServiceRequest().getContext()
				.get(resultsParam.get(0).getName());
	}

	public ServiceWrapperConfigManager getManager() {
		return manager;
	}

	public void setManager(ServiceWrapperConfigManager manager) {
		this.manager = manager;
	}

}
