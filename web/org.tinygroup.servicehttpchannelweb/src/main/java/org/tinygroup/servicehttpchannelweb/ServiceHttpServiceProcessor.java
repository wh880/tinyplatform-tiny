package org.tinygroup.servicehttpchannelweb;

import java.io.IOException;

import javax.servlet.ServletException;

import org.tinygroup.cepcore.CEPCore;
import org.tinygroup.context.Context;
import org.tinygroup.event.Event;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.servicehttpchannel.Hession;
import org.tinygroup.weblayer.AbstractTinyProcessor;
import org.tinygroup.weblayer.WebContext;

public class ServiceHttpServiceProcessor extends AbstractTinyProcessor {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ServiceHttpServiceProcessor.class);
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
		Event event = callService(serviceId, context);
		event.setType(Event.EVENT_TYPE_RESPONSE);
		if (urlString.endsWith("mockservice") ) {// 返回xml
			context.getResponse().getOutputStream().write(Hession.serialize(event));
		}
	}

	private Event callService(String serviceId, Context context) {
		Event event = null;
		try {
			event = (Event) Hession.deserialize( (byte[]) context.get("TINY_MOCK_SERVICE"));
		} catch (IOException e1) {
			throw new RuntimeException("请求:"+serviceId+"对象反序列化失败",e1);
		}
		try {
			core.process(event);
		} catch (Exception e1) {
			LOGGER.errorMessage("请求:{}执行异常", e1,serviceId);
			event.setThrowable(e1);
		}
		return event;
	}

	public CEPCore getCore() {
		return core;
	}

	public void setCore(CEPCore core) {
		this.core = core;
	}
	
}
