package org.tinygroup.servicehttpchannelweb;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import org.tinygroup.cepcore.CEPCore;
import org.tinygroup.context.Context;
import org.tinygroup.event.Event;
import org.tinygroup.event.Parameter;
import org.tinygroup.event.ServiceInfo;
import org.tinygroup.servicehttpchannel.Hession;
import org.tinygroup.weblayer.AbstractTinyProcessor;
import org.tinygroup.weblayer.WebContext;

public class ServiceHttpServiceProcessor extends AbstractTinyProcessor {
	CEPCore core;
	
	@Override
	protected void customInit() throws ServletException {

	}

	@Override
	public void reallyProcess(String urlString, WebContext context)
			throws ServletException, IOException {
		int lastSplash = urlString.lastIndexOf('/');
		int lastDot = urlString.lastIndexOf('.');
		String serviceId = urlString.substring(lastSplash + 1, lastDot);
		Object result = callService(serviceId, context);
		if (urlString.endsWith("mockservice") && result != null) {// 返回xml
			context.getResponse().getOutputStream().write(Hession.serialize(result));
		}
	}

	private Object callService(String serviceId, Context context) {
		// CEPCore core = SpringBeanContainer.getBean(CEPCore.CEP_CORE_BEAN);
		
		Event event = null;
		try {
			event = (Event) Hession.deserialize( (byte[]) context.get("TINY_MOCK_SERVICE"));
			core.process(event);
		} catch (IOException e1) {
			throw new RuntimeException("请求对象反序列化失败",e1);
		}
		ServiceInfo info = core.getServiceInfo(serviceId);
		List<Parameter> resultsParam = info.getResults();
		if (resultsParam == null || resultsParam.size() == 0) {
			return null;
		}
		return event.getServiceRequest().getContext()
				.get(resultsParam.get(0).getName());
	}

	public CEPCore getCore() {
		return core;
	}

	public void setCore(CEPCore core) {
		this.core = core;
	}
	
}
