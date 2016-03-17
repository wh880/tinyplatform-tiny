package org.tinygroup.httpclient451.client;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;

import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpTrace;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.tinygroup.commons.tools.CollectionUtil;
import org.tinygroup.context.Context;
import org.tinygroup.httpclient451.response.DefaultResponse;
import org.tinygroup.httpclient451.ssl.SimpleHostnameVerifier;
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
 * 基于HttpClient4.5.1的实现方案
 * 
 * @author yancheng11334
 * 
 */
public class HttpClientImpl extends AbstractClient implements ClientInterface {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(HttpClientImpl.class);

	private static final String DEFAULT_USER_AGENT = "HttpClient4.5.1";

	private CloseableHttpClient httpClient;

	private HttpRequestBase method;
	
	private HttpClientContext httpClientContext;
	
	private HttpHost proxyHost=null;
	
	private CredentialsProvider credsProvider = null;
	
	private SSLConnectionSocketFactory sslsf = null;

	private boolean followRedirects = true;

	private String userAgent = DEFAULT_USER_AGENT;
	
	private Charset requestCharset = DEFAULT_REQUEST_CHARSET;

	public void init(Context context) {
		LOGGER.logMessage(LogLevel.DEBUG, "正在初始化HTTP通讯客户端...");

		followRedirects = (Boolean) context.get(ClientConstants.CLIENT_ALLOW_REDIRECT);
		userAgent = (String) context.get(ClientConstants.CLIENT_USER_AGENT,
				DEFAULT_USER_AGENT);

		httpClientContext = HttpClientContext.create();
		httpClientContext.setCookieStore(new BasicCookieStore());

		Proxy proxy = (Proxy) context.get(ClientConstants.CLIENT_PROXY); // 设置代理
		initProxy(proxy);

		Certifiable cert = (Certifiable) context.get(ClientConstants.CLIENT_CERT); // 设置认证
		initCert(cert);
		
		HttpClientBuilder httpClientBuilder = HttpClients.custom().setUserAgent(userAgent).setSSLHostnameVerifier(new SimpleHostnameVerifier());
		if(sslsf!=null){
		   httpClientBuilder.setSSLSocketFactory(sslsf);
		}
		httpClient = httpClientBuilder.build();

		// 设置超时
		Integer connectTime = context.get(ClientConstants.CLIENT_CONNECT_TIME);
		Integer socketTime = context.get(ClientConstants.CLIENT_SOCKET_TIME);
		RequestConfig requestConfig = RequestConfig.custom()
				.setConnectTimeout(connectTime).setSocketTimeout(socketTime)
				.setRedirectsEnabled(followRedirects).setProxy(proxyHost).build();

		httpClientContext.setRequestConfig(requestConfig);
		httpClientContext.setCredentialsProvider(credsProvider);

		LOGGER.logMessage(LogLevel.DEBUG, "初始化HTTP通讯客户端完成!");
	}

	public void close() throws IOException {
		if (method != null) {
			method.releaseConnection();
		}
		if (httpClient != null) {
			httpClient.close();
		}
	}

	protected void initProxy(Proxy proxy) {
		if (proxy != null) {
			proxyHost = new HttpHost(proxy.getHost(), proxy.getPort());
			// 如果代理需要认证
			if (proxy.getProxyName() != null && proxy.getPassword() != null) {
			    credsProvider = new BasicCredentialsProvider(); 
				credsProvider.setCredentials(  
		                  new AuthScope(proxy.getHost(),proxy.getPort()),  
		                  new UsernamePasswordCredentials(proxy.getProxyName(),proxy.getPassword())); 
			}
		}
	}

	protected void initKeyCert(KeyCert cert) {
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
	        
	        sslsf = new SSLConnectionSocketFactory(sslcontext);
	        
		}catch(Exception e){
			throw new HttpVisitorException("初始化证书认证发生异常", e);
		}
	}

	protected void initPasswordCert(PasswordCert cert) {
		credsProvider = new BasicCredentialsProvider(); 
		credsProvider.setCredentials(  
                  new AuthScope(method.getURI().getHost(),method.getURI().getPort()),  
                  new UsernamePasswordCredentials(cert.getUserName(),cert.getPassword()));  
	}

	protected void dealHttpMethod(Request request) {
		requestCharset = getCharset(request);
		switch (request.getMethod()) {
		case GET: {
			method = new HttpGet(getUrl(request));
			break;
		}
		case POST: {
			method = new HttpPost(request.getUrl());
			addPostParameter(request.getParameters());
			break;
		}
		case HEAD: {
			method = new HttpHead(getUrl(request));
			break;
		}
		case PUT: {
			method = new HttpPut(getUrl(request));
			break;
		}
		case PATCH: {
			method = new HttpPatch(getUrl(request));
			break;
		}
		case DELETE: {
			method = new HttpDelete(getUrl(request));
			break;
		}
		case OPTIONS: {
			method = new HttpOptions(getUrl(request));
			break;
		}
		case TRACE: {
			method = new HttpTrace(getUrl(request));
			break;
		}
		}
	}

	protected void dealHeaders(List<Header> headers) {
		if (!CollectionUtil.isEmpty(headers)) {
			for (Header header : headers) {
				method.setHeader(header.getName(), header.getValue());
			}
		}
	}

	protected void dealCookies(List<Cookie> cookies) {
		if (!CollectionUtil.isEmpty(cookies)) {
			for (Cookie cookie : cookies) {
				BasicClientCookie clientCookie = new BasicClientCookie(
						cookie.getName(), cookie.getValue());
				clientCookie.setDomain(cookie.getDomain() == null ? method
						.getURI().getHost() : cookie.getDomain());
				clientCookie.setPath(cookie.getPath() == null ? method.getURI()
						.getPath() : cookie.getPath());
				clientCookie.setExpiryDate(cookie.getExpiryDate());
				clientCookie.setSecure(cookie.isSecure());
				httpClientContext.getCookieStore().addCookie(clientCookie);
			}
		}
	}

	protected void dealBodyElement(List<BodyElement> bodyElements) {
		if (method instanceof HttpEntityEnclosingRequestBase
				&& !CollectionUtil.isEmpty(bodyElements)) {
			HttpEntityEnclosingRequestBase entityMethod = (HttpEntityEnclosingRequestBase) method;
			if (bodyElements.size() == 1) {
				// 单块正文
				BodyElement element = bodyElements.get(0);
				switch (element.getType()) {
				case STRING: {
					entityMethod.setEntity(new StringEntity((String) element
							.getElement(), requestCharset));
					break;
				}
				case BYTEARRAY: {
					entityMethod.setEntity(new ByteArrayEntity((byte[]) element
							.getElement(), getContentType(element.getContentType(),ContentType.DEFAULT_BINARY)));
					break;
				}
				case INPUTSTREAM: {
					entityMethod.setEntity(new InputStreamEntity(
							(InputStream) element.getElement(), getContentType(element.getContentType(),ContentType.DEFAULT_BINARY)));
					break;
				}
				case FILE: {
					entityMethod.setEntity(new FileEntity((File) element
							.getElement(), getContentType(element.getContentType(),ContentType.DEFAULT_BINARY)));
					break;
				}
				}
			} else {
				// 多块正文
				MultipartEntityBuilder entityBuilder = MultipartEntityBuilder
						.create();
				for (BodyElement element : bodyElements) {
					switch (element.getType()) {
					case STRING: {
						entityBuilder.addTextBody(element.getName(),
								(String) element.getElement(),
								getContentType(element.getContentType(),ContentType.DEFAULT_TEXT));
						break;
					}
					case BYTEARRAY: {
						entityBuilder.addBinaryBody(element.getName(),
								(byte[]) element.getElement(),
								getContentType(element.getContentType(),ContentType.DEFAULT_BINARY),
								element.getName());
						break;
					}
					case INPUTSTREAM: {
						entityBuilder.addBinaryBody(element.getName(),
								(InputStream) element.getElement(),
								getContentType(element.getContentType(),ContentType.DEFAULT_BINARY),
								element.getName());
						break;
					}
					case FILE: {
						File file = (File) element.getElement();
						entityBuilder.addBinaryBody(element.getName(), file,
								getContentType(element.getContentType(),ContentType.DEFAULT_BINARY),
								file.getName());
						break;
					}
					}
				}
				entityMethod.setEntity(entityBuilder.build());
			}
		}
	}
	
	private ContentType getContentType(String s,ContentType defaultType){
		try{
			return s==null?defaultType:ContentType.parse(s);
		}catch(Exception e){
			//解析异常
			LOGGER.logMessage(LogLevel.WARN, "解析ContentType发生异常",e);
			return defaultType;
		}
	}
	
	private void addPostParameter(List<Parameter> parameters){
		try{
			if (!CollectionUtil.isEmpty(parameters)) {
				HttpEntityEnclosingRequestBase entityMethod = (HttpEntityEnclosingRequestBase) method;
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				for(Parameter parameter:parameters){
					params.add(new BasicNameValuePair(parameter.getName(),parameter.getValue().toString()));
				}
				entityMethod.setEntity(new UrlEncodedFormEntity(params,requestCharset));
			}
		}catch (Exception e) {
			throw new HttpVisitorException("转换报文的字段发生异常", e);
		}
		
	}

	protected Response executeMethod() {
		// 具体执行逻辑
		try {
			CloseableHttpResponse closeableHttpResponse = httpClient.execute(
					method, httpClientContext);
			return new DefaultResponse(httpClientContext.getCookieStore(),
					closeableHttpResponse);
		} catch (ClientProtocolException e) {
			throw new HttpVisitorException("Http协议标识存在错误", e);
		} catch (IOException e) {
			throw new HttpVisitorException("Http通讯发生异常", e);
		}
	}

}
