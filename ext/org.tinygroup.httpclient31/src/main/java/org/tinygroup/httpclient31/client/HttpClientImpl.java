package org.tinygroup.httpclient31.client;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.KeyStore;
import java.util.List;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.EntityEnclosingMethod;
import org.apache.commons.httpclient.methods.FileRequestEntity;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.HeadMethod;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.OptionsMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.methods.TraceMethod;
import org.apache.commons.httpclient.methods.multipart.ByteArrayPartSource;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.protocol.ProtocolSocketFactory;
import org.tinygroup.commons.tools.CollectionUtil;
import org.tinygroup.context.Context;
import org.tinygroup.httpclient31.body.InputStreamPartSource;
import org.tinygroup.httpclient31.cert.AuthSSLProtocolSocketFactory;
import org.tinygroup.httpclient31.response.DefaultResponse;
import org.tinygroup.httpvisitor.BodyElement;
import org.tinygroup.httpvisitor.Certifiable;
import org.tinygroup.httpvisitor.Cookie;
import org.tinygroup.httpvisitor.Header;
import org.tinygroup.httpvisitor.Request;
import org.tinygroup.httpvisitor.Response;
import org.tinygroup.httpvisitor.client.AbstractClient;
import org.tinygroup.httpvisitor.client.ClientConstants;
import org.tinygroup.httpvisitor.client.ClientInterface;
import org.tinygroup.httpvisitor.execption.HttpVisitorException;
import org.tinygroup.httpvisitor.struct.KeyCert;
import org.tinygroup.httpvisitor.struct.Parameter;
import org.tinygroup.httpvisitor.struct.PasswordCert;
import org.tinygroup.httpvisitor.struct.Proxy;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.vfs.FileObject;
import org.tinygroup.vfs.VFS;

/**
 * 基于HttpClient3.1的实现方案
 * 
 * @author yancheng11334
 * 
 */
public class HttpClientImpl extends AbstractClient implements ClientInterface {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(HttpClientImpl.class);
	
	private static final String DEFAULT_USER_AGENT = "HttpClient3.1";

	private HttpClient httpClient;

	private HttpMethodBase method;

	private boolean followRedirects = true;

	private String userAgent = DEFAULT_USER_AGENT;
	
	private Charset requestCharset = DEFAULT_REQUEST_CHARSET;

	public void close() throws IOException {
		if (method != null) {
			method.releaseConnection();
		}
	}

	public void init(Context context) {
		LOGGER.logMessage(LogLevel.DEBUG, "正在初始化HTTP通讯客户端...");

		httpClient = new HttpClient(new MultiThreadedHttpConnectionManager());

		Integer connectTime = (Integer)context.get(ClientConstants.CLIENT_CONNECT_TIME);
		Integer socketTime = (Integer) context.get(ClientConstants.CLIENT_SOCKET_TIME);
		initTimeOut(connectTime, socketTime);

		followRedirects = (Boolean)context.get(ClientConstants.CLIENT_ALLOW_REDIRECT);
		userAgent = (String)context.get(ClientConstants.CLIENT_USER_AGENT,DEFAULT_USER_AGENT);

		Proxy proxy = (Proxy) context.get(ClientConstants.CLIENT_PROXY); // 设置代理
		initProxy(proxy);
		
		Certifiable cert = (Certifiable) context.get(ClientConstants.CLIENT_CERT); // 设置认证
		initCert(cert);

		LOGGER.logMessage(LogLevel.DEBUG, "初始化HTTP通讯客户端完成!");
	}

	protected Response executeMethod() {
		// 具体执行逻辑
		try {
			httpClient.executeMethod(method);
			return new DefaultResponse(method, httpClient.getState());
		} catch (Exception e) {
			throw new HttpVisitorException("执行HTTP访问发生异常", e);
		}
	}

	protected void initTimeOut(int connectTime, int socketTime) {
		httpClient.getHttpConnectionManager().getParams()
				.setConnectionTimeout(connectTime); // 设置connect超时
		httpClient.getHttpConnectionManager().getParams()
				.setSoTimeout(socketTime); // 设置socket超时
	}

	protected void initProxy(Proxy proxy) {
		if (proxy != null) {
			httpClient.getHostConfiguration().setProxy(proxy.getHost(),
					proxy.getPort());
			// 如果代理需要认证
			if (proxy.getProxyName() != null && proxy.getPassword() != null) {
				httpClient.getState().setProxyCredentials(
						new AuthScope(proxy.getHost(), proxy.getPort()),
						new UsernamePasswordCredentials(proxy.getProxyName(),
								proxy.getPassword()));
			}
		}
	}
	
	protected void initKeyCert(KeyCert cert){
		try{
			KeyStore keyStore  = KeyStore.getInstance(cert.getCertType());
	        FileObject certObjcet = VFS.resolveFile(cert.getCertPath());
	        char[] password = cert.getPassword().toCharArray();
	        try {
	            keyStore.load(certObjcet.getInputStream(), password);
	        } finally {
	        	certObjcet.clean();
	        }
	        KeyManagerFactory factory = KeyManagerFactory.getInstance(KeyManagerFactory. getDefaultAlgorithm());
	        factory.init(keyStore, password);
	        
	        // Trust own CA and all self-signed certs
	        SSLContext sslcontext = SSLContext.getInstance("TLS");
	        sslcontext.init(factory.getKeyManagers(), null, null);
	        
	        ProtocolSocketFactory protocolSocketFactory =  new AuthSSLProtocolSocketFactory( VFS.resolveFile(cert.getCertPath()), cert.getPassword(),  null,null);
	        Protocol authhttps = new Protocol("https", protocolSocketFactory, 443);  
	        Protocol.registerProtocol("https", authhttps);  
		}catch(Exception e){
			throw new HttpVisitorException("初始化证书认证发生异常", e);
		}
	}
	
	protected void initPasswordCert(PasswordCert cert){
		AuthScope authScope = new AuthScope(httpClient.getHostConfiguration().getHost(), httpClient.getHostConfiguration().getPort());
		httpClient.getState().setCredentials(authScope, new UsernamePasswordCredentials(cert.getUserName(),
				cert.getPassword()));
	}

	@SuppressWarnings("deprecation")
	protected void dealCookies(List<Cookie> cookies) {
		if (!CollectionUtil.isEmpty(cookies)) {
			httpClient.getParams().setCookiePolicy(
					CookiePolicy.BROWSER_COMPATIBILITY);
			httpClient.getParams().setParameter(
					"http.protocol.single-cookie-header", true);
			for (Cookie cookie : cookies) {
				httpClient.getState().addCookie(
						new org.apache.commons.httpclient.Cookie(cookie
								.getDomain() == null ? method
								.getHostConfiguration().getHost() : cookie
								.getDomain(), cookie.getName(), cookie
								.getValue(), cookie.getPath() == null ? method
								.getPath() : cookie.getPath(), cookie
								.getExpiryDate(), cookie.isSecure()));
			}
		} else {
			httpClient.getParams().setCookiePolicy(CookiePolicy.IGNORE_COOKIES);
		}

	}

	protected void dealHeaders(List<Header> headers) {
		if (!CollectionUtil.isEmpty(headers)) {
			for (Header header : headers) {
				method.addRequestHeader(header.getName(), header.getValue());
			}
		}
		dealDefaultHeader(ClientConstants.CLIENT_USER_AGENT, userAgent);
		//dealDefaultHeader(ClientConstants.CLIENT_CONTENT_TYPE, String.format("text/html; charset=%s",requestCharset));
	}

	private void dealDefaultHeader(String name, String value) {
		// 若用户没有设置指定Header，则设置；如果用户设置，不操作
		if (method.getRequestHeader(name) == null) {
			method.addRequestHeader(name, value);
		}
	}

	protected void dealBodyElement(List<BodyElement> bodyElements) {
		try {
			if (method instanceof EntityEnclosingMethod
					&& !CollectionUtil.isEmpty(bodyElements)) {
				EntityEnclosingMethod entityMethod = (EntityEnclosingMethod) method;
				if (bodyElements.size() == 1) {
					// 单块正文
					BodyElement element = bodyElements.get(0);
					switch (element.getType()) {
					case STRING: {
						entityMethod
								.setRequestEntity(new StringRequestEntity(
										(String) element.getElement(), element
												.getContentType(), element
												.getCharset()==null?requestCharset.name():element.getCharset()));
						break;
					}
					case BYTEARRAY: {
						entityMethod
								.setRequestEntity(new ByteArrayRequestEntity(
										(byte[]) element.getElement(), element
												.getContentType()));
						break;
					}
					case INPUTSTREAM: {
						entityMethod
								.setRequestEntity(new InputStreamRequestEntity(
										(InputStream) element.getElement(),
										element.getContentType()));
						break;
					}
					case FILE: {
						entityMethod.setRequestEntity(new FileRequestEntity(
								(File) element.getElement(), element
										.getContentType()));
						break;
					}
					}
				} else {
					// 多块正文
					Part[] parts = new Part[bodyElements.size()];
					for (int i = 0; i < bodyElements.size(); i++) {
						BodyElement element = bodyElements.get(i);
						switch (element.getType()) {
						case STRING: {
							parts[i] = new StringPart(element.getName(),
									(String) element.getElement(),
									element
									.getCharset()==null?requestCharset.name():element.getCharset());
							break;
						}
						case BYTEARRAY: {
							parts[i] = new FilePart(element.getName(),
									new ByteArrayPartSource(element.getName(),
											(byte[]) element.getElement()),
									element.getContentType(),
									element.getCharset());
							break;
						}
						case INPUTSTREAM: {
							parts[i] = new FilePart(
									element.getName(),
									new InputStreamPartSource(
											element.getName(),
											(InputStream) element.getElement()),
									element.getContentType(), element
											.getCharset());
							break;
						}
						case FILE: {
							parts[i] = new FilePart(element.getName(),
									(File) element.getElement(),
									element.getContentType(),
									element.getCharset());
							break;
						}
						}
					}
					entityMethod.setRequestEntity(new MultipartRequestEntity(
							parts, httpClient.getParams()));
				}
			}
		} catch (Exception e) {
			throw new HttpVisitorException("处理HTTP正文发生异常!", e);
		}

	}

	protected void dealHttpMethod(Request request) {
		requestCharset = getCharset(request);
		switch (request.getMethod()) {
		case GET: {
			method = new GetMethod(getUrl(request));
			method.setFollowRedirects(followRedirects);
			break;
		}
		case POST: {
			PostMethod post = new PostMethod(request.getUrl());
			addPostParameter(post, request.getParameters());
			method = post;
			break;
		}
		case HEAD: {
			method = new HeadMethod(getUrl(request));
			method.setFollowRedirects(followRedirects);
			break;
		}
		case PUT: {
			method = new PutMethod(getUrl(request));
			break;
		}
		case PATCH: {
			throw new HttpVisitorException("本HttpVisitor实现不支持PATCH操作!");
		}
		case DELETE: {
			method = new DeleteMethod(getUrl(request));
			method.setFollowRedirects(followRedirects);
			break;
		}
		case OPTIONS: {
			method = new OptionsMethod(getUrl(request));
			method.setFollowRedirects(followRedirects);
			break;
		}
		case TRACE: {
			method = new TraceMethod(getUrl(request));
			method.setFollowRedirects(followRedirects);
			break;
		}
		}
		//method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, getCharset(request).name());
	}

	private void addPostParameter(PostMethod post, List<Parameter> parameters) {
		if (!CollectionUtil.isEmpty(parameters)) {
			for (Parameter p : parameters) {
				String key = p.getName();
				Object value = p.getValue();
				if (value.getClass().isArray()) {
					Object[] arrayValue = (Object[]) value;
					for (Object o : arrayValue) {
						post.addParameter(key, o.toString());
					}
				} else {
					post.setParameter(key, value.toString());
				}
			}
		}
	}


}
