package org.tinygroup.mockservice;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.zip.GZIPInputStream;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.tinygroup.cepcore.CEPCore;
import org.tinygroup.cepcore.impl.AbstractEventProcessor;
import org.tinygroup.commons.file.IOUtils;
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

import com.alibaba.fastjson.JSON;

@SuppressWarnings("deprecation")
public class MockServiceEventProcessorImpl extends AbstractEventProcessor
		implements Configuration {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(MockServiceEventProcessorImpl.class);
	private static final String SERVER_PATH_CONFIG = "/application/server-path";
	private XmlNode appConfig;
	private String serverPath;
	HttpVisitor visitor;
	private MockServiceManager manager;
	private String responseCharset = "UTF-8";

	public void process(Event event) {
		if (serverPath == null) {
			throw new RuntimeException("服务器地址路径未配置");
		}
		String serviceId = event.getServiceRequest().getServiceId();
		String url = serverPath + serviceId + ".mockservice";
		Object result  = execute(event, url);
//		System.out.println(resultString);
		Type resultType = getResultType(serviceId);
		if (!"void".equals(resultType.toString())) {
			// 目前.servicejson序列化方式使用fastJson
			// 如果.servicejson的处理器ServiceTinyProcessor中对应的ObjectToJson方式变化，此处需要同步调整
//			Object result = JSON.parseObject(resultString, resultType);
			event.getServiceRequest().getContext()
					.put(getResultName(serviceId), result);
		}
		event.setType(Event.EVENT_TYPE_RESPONSE);

	}

	private Object execute(Event event, String url) {
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
				LOGGER.logMessage(LogLevel.DEBUG, "结果成功返回。");
//				Header responseHeader = method
//						.getResponseHeader("Content-Encoding");
//				if (responseHeader != null) {
//					String acceptEncoding = responseHeader.getValue();
//					if (acceptEncoding != null
//							&& ("gzip").equals(acceptEncoding)) {
//						// 如果是gzip压缩方式
//						ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
//								method.getResponseBody());
//						GZIPInputStream gzipInputStream = new GZIPInputStream(
//								byteArrayInputStream);
//						return IOUtils.readFromInputStream(gzipInputStream,
//								responseCharset);
//					}
//				}
				String serviceId = event.getServiceRequest().getServiceId();
				Type resultType = getResultType(serviceId);
				if (!"void".equals(resultType.toString())) {
					return Hession.deserialize(method.getResponseBody());
				}else{
					return null;
				}
				
//				return new String(method.getResponseBody(), responseCharset);
			}
			LOGGER.logMessage(LogLevel.ERROR, "结果返回失败，原因：{}", method
					.getStatusLine().toString());
			throw new RuntimeException(method.getStatusLine().toString());
		} catch (Exception e) {
			throw new RuntimeException("执行http请求失败", e);
		} finally {
			method.releaseConnection();
		}

	}

	private Type getResultType(String serviceId) {
		return manager.getMockService(serviceId).getResultType();
	}

	private String getResultName(String serviceId) {
		return manager.getMockService(serviceId).getResultName();
	}

	private HttpVisitor getHttpVisitor() {
		if (visitor == null) {
			visitor = new HttpVisitorImpl();
			visitor.init();
		}

		return visitor;
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

	public MockServiceManager getManager() {
		return manager;
	}

	public void setManager(MockServiceManager manager) {
		this.manager = manager;
	}

}
