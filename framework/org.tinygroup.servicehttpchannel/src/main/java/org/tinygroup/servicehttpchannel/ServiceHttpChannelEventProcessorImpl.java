package org.tinygroup.servicehttpchannel;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.tinygroup.cepcore.CEPCore;
import org.tinygroup.cepcore.impl.AbstractEventProcessor;
import org.tinygroup.commons.tools.StringUtil;
import org.tinygroup.config.Configuration;
import org.tinygroup.event.Event;
import org.tinygroup.event.ServiceInfo;
import org.tinygroup.httpvisit.HttpVisitor;
import org.tinygroup.httpvisit.impl.HttpVisitorImpl;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.xmlparser.node.XmlNode;

@SuppressWarnings("deprecation")
public class ServiceHttpChannelEventProcessorImpl extends AbstractEventProcessor
		implements Configuration {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ServiceHttpChannelEventProcessorImpl.class);
	private static final String SERVER_PATH_CONFIG = "/application/server-path";
	private XmlNode appConfig;
	private String serverPath;
	HttpVisitor visitor;
	private ServiceHttpChannelManager manager;

	public void process(Event event) {
		if (serverPath == null) {
			throw new RuntimeException("服务器地址路径未配置");
		}
		String serviceId = event.getServiceRequest().getServiceId();
		String url = serverPath + serviceId + ".mockservice";
		Event result  = execute(event, url);
		Throwable throwable = result.getThrowable();
		if (throwable != null) {// 如果有异常发生，则抛出异常
			LOGGER.errorMessage("Http请求发生异常,serviceId:{},eventId:{}", throwable,
					result.getServiceRequest().getServiceId(),
					result.getEventId());
			if (throwable instanceof RuntimeException) {
				throw (RuntimeException) throwable;
			} else {
				throw new RuntimeException(throwable);// 此处的RuntimeException类型需要调整
			}
		}
		event.getServiceRequest()
		.getContext()
		.putSubContext(result.getEventId(),
				result.getServiceRequest().getContext());
		

	}

	private Event execute(Event event, String url) {
		byte[] data;
		try {
			data = Hession.serialize(event);
		} catch (IOException e) {
			throw new RuntimeException("序列化失败", e);
		}
		PostMethod method = new PostMethod(url);
		HttpMethodParams params = new HttpMethodParams();
		 params.setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(0,false));
		method.setParams(params);
		ByteArrayInputStream bis = new ByteArrayInputStream(data);
		method.setRequestBody(bis);
		HttpClient client = getHttpVisitor().getHttpClient();

		try {
			int iGetResultCode = client.executeMethod(method);
			if (iGetResultCode == HttpStatus.SC_OK) {
				String serviceId = event.getServiceRequest().getServiceId();
				String eventId = event.getEventId();
				LOGGER.logMessage(LogLevel.DEBUG, "请求:eventId:{},serviceId:{} 成功返回。",eventId,serviceId);
				return (Event)Hession.deserialize(method.getResponseBody());
			}
			LOGGER.logMessage(LogLevel.ERROR, "请求:eventId:{},serviceId:{},返回失败，原因：{}", method
					.getStatusLine().toString());
			throw new RuntimeException(method.getStatusLine().toString());
		} catch (Exception e) {
			throw new RuntimeException("执行http请求失败", e);
		} finally {
			method.releaseConnection();
		}

	}

	private Type getResultType(String serviceId) {
		return manager.getService(serviceId).getResultType();
	}

	private String getResultName(String serviceId) {
		return manager.getService(serviceId).getResultName();
	}

	private HttpVisitor getHttpVisitor() {
		if (visitor == null) {
			visitor = new HttpVisitorImpl();
			visitor.init();
		}

		return visitor;
	}

	public void setVisitor(HttpVisitor visitor) {
		this.visitor = visitor;
	}

	public String getApplicationNodePath() {
		return SERVER_PATH_CONFIG;
	}

	public String getComponentConfigPath() {
		return null;
	}

	public void config(XmlNode applicationConfig, XmlNode componentConfig) {
		this.appConfig = applicationConfig;
		if (appConfig != null) {
			serverPath = appConfig.getAttribute("path");
			if (!StringUtil.isBlank(serverPath) && !serverPath.endsWith("/")) {
				serverPath = serverPath + "/";
			}
		}
	}

	public XmlNode getComponentConfig() {
		return null;
	}

	public XmlNode getApplicationConfig() {
		return appConfig;
	}

	public void setCepCore(CEPCore cepCore) {

	}

	public List<ServiceInfo> getServiceInfos() {
		return manager.getInfos();
	}

	public int getWeight() {
		return 0;
	}

	public List<String> getRegex() {
		return null;
	}

	public boolean isRead() {
		return true;
	}

	public void setRead(boolean read) {
	}

	public ServiceHttpChannelManager getManager() {
		return manager;
	}

	public void setManager(ServiceHttpChannelManager manager) {
		this.manager = manager;
	}

}
