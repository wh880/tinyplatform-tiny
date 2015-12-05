package org.tinygroup.servicewrapper.namediscoverer;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;

import org.springframework.core.ParameterNameDiscoverer;
import org.tinygroup.cepcore.CEPCore;
import org.tinygroup.commons.tools.CollectionUtil;
import org.tinygroup.event.Parameter;
import org.tinygroup.event.ServiceInfo;
import org.tinygroup.servicewrapper.ServiceIdAnaly;

/**
 * 通过服务号获取对应方法的参数名称
 * @author renhui
 *
 */
public class ServiceParameterNameDiscoverer implements ParameterNameDiscoverer {
	private ServiceIdAnaly serviceIdAnaly;

	private CEPCore core;
	
	public CEPCore getCore() {
		return core;
	}

	public void setCore(CEPCore core) {
		this.core = core;
	}
	

	public ServiceIdAnaly getServiceIdAnaly() {
		return serviceIdAnaly;
	}

	public void setServiceIdAnaly(ServiceIdAnaly serviceIdAnaly) {
		this.serviceIdAnaly = serviceIdAnaly;
	}

	public String[] getParameterNames(Method method) {
		String serviceId=serviceIdAnaly.analyMethod(method);
		ServiceInfo serviceInfo = core.getServiceInfo(serviceId);
		List<Parameter> params = serviceInfo.getParameters();
		if(!CollectionUtil.isEmpty(params)){
			String[] parameterNames=new String[params.size()];
			for (int i = 0; i < parameterNames.length; i++) {
				parameterNames[i]=params.get(i).getName();
			}
			return parameterNames;
		}
		return null;
	}

	public String[] getParameterNames(Constructor ctor) {
		return null;
	}

}
