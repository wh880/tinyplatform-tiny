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
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
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
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.protocol.ProtocolSocketFactory;
import org.tinygroup.commons.tools.CollectionUtil;
import org.tinygroup.context.Context;
import org.tinygroup.httpclient31.body.InputStreamPartSource;
import org.tinygroup.httpclient31.cert.AuthSSLProtocolSocketFactory;
import org.tinygroup.httpvisitor.BodyElement;
import org.tinygroup.httpvisitor.Certifiable;
import org.tinygroup.httpvisitor.Cookie;
import org.tinygroup.httpvisitor.Header;
import org.tinygroup.httpvisitor.Request;
import org.tinygroup.httpvisitor.client.AbstractClientInterface;
import org.tinygroup.httpvisitor.client.ClientConstants;
import org.tinygroup.httpvisitor.client.ClientInterface;
import org.tinygroup.httpvisitor.execption.HttpVisitorException;
import org.tinygroup.httpvisitor.struct.KeyCert;
import org.tinygroup.httpvisitor.struct.Parameter;
import org.tinygroup.httpvisitor.struct.PasswordCert;
import org.tinygroup.httpvisitor.struct.Proxy;
import org.tinygroup.vfs.FileObject;
import org.tinygroup.vfs.VFS;

public abstract class AbstractHttpClient31 extends AbstractClientInterface implements ClientInterface{

	private static final String DEFAULT_USER_AGENT = "HttpClient3.1";
	
	protected HttpClient httpClient;
	protected boolean allowRedirects;
	protected String userAgent;
	
	
	public void init(Context context) {
		updateHttpConfigTemplate(context);
		httpClient = initHttpClient(context);
	}
	
	protected HttpClient initHttpClient(Context context){
		allowRedirects = (Boolean)context.get(ClientConstants.CLIENT_ALLOW_REDIRECT,true);
		userAgent = (String)context.get(ClientConstants.CLIENT_USER_AGENT,DEFAULT_USER_AGENT);
		
		Proxy proxy = (Proxy) context.get(ClientConstants.CLIENT_PROXY); // 设置代理
		Certifiable cert = (Certifiable) context.get(ClientConstants.CLIENT_CERT); // 设置认证
		
		HttpClient client = buildHttpClient();
		initParams(context,client);
		initProxy(proxy,client);
		initCertifiable(cert,client);
		return client;
	}
	
	
	/**
	 * 由不同的实现决定
	 * @return
	 */
	protected abstract HttpClient buildHttpClient();
	
	protected void initParams(Context context,HttpClient client) {
		HttpConnectionManagerParams params = client.getHttpConnectionManager().getParams();
		
		Integer connectTimeOut = (Integer)context.get(ClientConstants.CLIENT_CONNECT_TIMEOUT);
		Integer socketTimeOut = (Integer)context.get(ClientConstants.CLIENT_SOCKET_TIMEOUT);
		Integer maxTotalConnections = (Integer)context.get(ClientConstants.MAX_TOTAL_CONNECTIONS);
		Integer maxConnectionsPerHost = (Integer)context.get(ClientConstants.MAX_CONNECTIONS_PER_HOST);
		
		if(connectTimeOut!=null){
			params.setConnectionTimeout(connectTimeOut); // 设置connect超时
		}
		if(socketTimeOut!=null){
			params.setSoTimeout(socketTimeOut); // 设置socket超时
		}
		if(maxTotalConnections!=null){
			params.setMaxTotalConnections(maxTotalConnections);
		}
		if(maxConnectionsPerHost!=null){
			params.setDefaultMaxConnectionsPerHost(maxConnectionsPerHost);
		}
	}
	
    protected void initProxy(Proxy proxy,HttpClient client){
    	if (proxy != null) {
    		client.getHostConfiguration().setProxy(proxy.getHost(),
					proxy.getPort());
			// 如果代理需要认证
			if (proxy.getProxyName() != null && proxy.getPassword() != null) {
				client.getState().setProxyCredentials(
						new AuthScope(proxy.getHost(), proxy.getPort()),
						new UsernamePasswordCredentials(proxy.getProxyName(),
								proxy.getPassword()));
			}
    	}
    }
    
    protected void initCertifiable(Certifiable cert,HttpClient client){
    	if(cert!=null){
     	   if(cert instanceof KeyCert){
     		   initKeyCert((KeyCert)cert);
     	   }else if(cert instanceof PasswordCert){
     		   initPasswordCert((PasswordCert)cert,client);
     	   }else{
     		   throw new HttpVisitorException("未知的认证类型:"+cert.getClass());
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
	
	protected void initPasswordCert(PasswordCert cert,HttpClient client){
		AuthScope authScope = new AuthScope(client.getHostConfiguration().getHost(), client.getHostConfiguration().getPort());
		client.getState().setCredentials(authScope, new UsernamePasswordCredentials(cert.getUserName(),
				cert.getPassword()));
	}
	
	protected HttpMethodBase dealHttpMethod(Request request) {
		HttpMethodBase method = null;
		
		switch (request.getMethod()) {
		case GET: {
			method = new GetMethod(getUrl(request));
			method.setFollowRedirects(allowRedirects);
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
			method.setFollowRedirects(allowRedirects);
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
			method.setFollowRedirects(allowRedirects);
			break;
		}
		case OPTIONS: {
			method = new OptionsMethod(getUrl(request));
			method.setFollowRedirects(allowRedirects);
			break;
		}
		case TRACE: {
			method = new TraceMethod(getUrl(request));
			method.setFollowRedirects(allowRedirects);
			break;
		}
		}
		return method;
	}
	
	protected void dealHeaders(HttpMethodBase method,List<Header> headers) {
		if (!CollectionUtil.isEmpty(headers)) {
			for (Header header : headers) {
				dealDefaultHeader(method,header.getName(), header.getValue());
			}
		}
		List<Header> templateHeaders = httpConfigTemplate==null?null:httpConfigTemplate.getHeaderParamters();
		if(!CollectionUtil.isEmpty(templateHeaders)){
		  for(Header header:templateHeaders){
			 dealDefaultHeader(method,header.getName(), header.getValue());
		  }
		}
		dealDefaultHeader(method,ClientConstants.CLIENT_USER_AGENT, userAgent);
	}
	
	@SuppressWarnings("deprecation")
	protected void dealCookies(HttpMethodBase method,HttpState state,List<Cookie> cookies) {
		if (!CollectionUtil.isEmpty(cookies)) {
			for (Cookie cookie : cookies) {
				state.addCookie(
						new org.apache.commons.httpclient.Cookie(cookie
								.getDomain() == null ? method
								.getHostConfiguration().getHost() : cookie
								.getDomain(), cookie.getName(), cookie
								.getValue(), cookie.getPath() == null ? method
								.getPath() : cookie.getPath(), cookie
								.getExpiryDate(), cookie.isSecure()));
			}
		} 
	}
	
	protected void dealBodyElement(HttpMethodBase method,Charset requestCharset,List<BodyElement> bodyElements) {
		if (method instanceof EntityEnclosingMethod
				&& !CollectionUtil.isEmpty(bodyElements)) {
			EntityEnclosingMethod entityMethod = (EntityEnclosingMethod) method;
			try{
				if (bodyElements.size() == 1) {
					BodyElement element = bodyElements.get(0);
					dealSingleBodyElement(entityMethod,requestCharset,element);
				}else{
					dealMultiBodyElement(entityMethod,requestCharset,bodyElements);
				}
			}catch(Exception e){
				throw new HttpVisitorException("处理HTTP正文发生异常!", e);
			}
			
		}
	}
	
	private void dealSingleBodyElement(EntityEnclosingMethod entityMethod,Charset requestCharset,BodyElement element) throws Exception{
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
	}
	
    private void dealMultiBodyElement(EntityEnclosingMethod entityMethod,Charset requestCharset,List<BodyElement> bodyElements) throws Exception{
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
	
	
	private void dealDefaultHeader(HttpMethodBase method,String name, String value) {
		// 若用户没有设置指定Header，则设置；如果用户设置，不操作
		if (method.getRequestHeader(name) == null) {
			method.addRequestHeader(name, value);
		}
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
