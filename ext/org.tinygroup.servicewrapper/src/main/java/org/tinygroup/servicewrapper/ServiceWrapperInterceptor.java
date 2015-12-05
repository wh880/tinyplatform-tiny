package org.tinygroup.servicewrapper;

import java.lang.reflect.Method;
import java.util.List;
import java.util.UUID;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.core.ParameterNameDiscoverer;
import org.tinygroup.cepcore.CEPCore;
import org.tinygroup.commons.tools.Assert;
import org.tinygroup.commons.tools.StringUtil;
import org.tinygroup.context.Context;
import org.tinygroup.context.impl.ContextImpl;
import org.tinygroup.event.Event;
import org.tinygroup.event.Parameter;
import org.tinygroup.event.ServiceInfo;
import org.tinygroup.event.ServiceRequest;

/**
 * 拦截器
 *
 */
public class ServiceWrapperInterceptor implements MethodInterceptor {
	private CEPCore core;
	
	private ServiceIdAnaly serviceIdAnaly;
	
	private ParameterNameDiscoverer parameterNameDiscoverer;
	
	public CEPCore getCore() {
		return core;
	}

	public void setCore(CEPCore core) {
		this.core = core;
	}
	
	public ParameterNameDiscoverer getParameterNameDiscoverer() {
		return parameterNameDiscoverer;
	}

	public void setParameterNameDiscoverer(
			ParameterNameDiscoverer parameterNameDiscoverer) {
		this.parameterNameDiscoverer = parameterNameDiscoverer;
	}

	public ServiceIdAnaly getServiceIdAnaly() {
		return serviceIdAnaly;
	}

	public void setServiceIdAnaly(ServiceIdAnaly serviceIdAnaly) {
		this.serviceIdAnaly = serviceIdAnaly;
	}

	public Object invoke(MethodInvocation invocation) throws Throwable {
		Object[] args = invocation.getArguments();
		Method method = invocation.getMethod();
		String serviceId =serviceIdAnaly.analyMethod(method);
		if(StringUtil.isBlank(serviceId)){
			throw new RuntimeException(String.format("方法:%s,未发布成服务,不能进行访问", method));
		}
		Context context = new ContextImpl();
		String[] paramNames=parameterNameDiscoverer.getParameterNames(method);
		int size = paramNames != null ? paramNames.length : 0;
		int argsize = args != null ? args.length : 0;
		if (paramNames != null && args != null) {
			Assert.assertTrue(size == argsize, "服务配置描述的参数个数与实际方法的参数个数不一致");// 参数个数必须一致
		}
		for (int i = 0; i < size; i++) {
			context.put(paramNames[i], args[i]);
		}
		return callServiceAndCallBack(serviceId, context);

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

}
