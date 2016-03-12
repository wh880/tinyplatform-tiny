package org.tinygroup.httpvisitor.client;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.List;

import org.tinygroup.httpvisitor.BodyElement;
import org.tinygroup.httpvisitor.Certifiable;
import org.tinygroup.httpvisitor.Cookie;
import org.tinygroup.httpvisitor.Header;
import org.tinygroup.httpvisitor.Request;
import org.tinygroup.httpvisitor.Response;
import org.tinygroup.httpvisitor.execption.HttpVisitorException;
import org.tinygroup.httpvisitor.struct.KeyCert;
import org.tinygroup.httpvisitor.struct.Parameter;
import org.tinygroup.httpvisitor.struct.PasswordCert;
import org.tinygroup.httpvisitor.struct.Proxy;

/**
 * 抽象的HTTP客户端基类
 * @author yancheng11334
 *
 */
public abstract class AbstractClient implements ClientInterface{

	protected static final Charset DEFAULT_REQUEST_CHARSET = Charset.forName("ISO-8859-1");
	

	public Response execute(Request request) {
		dealHttpMethod(request);
		dealHeaders(request.getHeaders());
		dealCookies(request.getCookies());	
		dealBodyElement(request.getBodyElements());		
		return executeMethod();
	}
	
	/**
	 * 处理代理
	 * @param proxy
	 */
    protected abstract void initProxy(Proxy proxy);
    
    /**
     * 初始化认证
     * @param cert
     */
    protected void initCert(Certifiable cert){
    	if(cert!=null){
    	   if(cert instanceof KeyCert){
    		   initKeyCert((KeyCert)cert);
    	   }else if(cert instanceof PasswordCert){
    		   initPasswordCert((PasswordCert)cert);
    	   }else{
    		   throw new HttpVisitorException("未知的认证类型:"+cert.getClass());
    	   }
    	}
    }
    
    /**
     * 初始化秘钥认证
     * @param cert
     */
    protected abstract void initKeyCert(KeyCert cert);
	
    /**
     * 初始化口令认证
     * @param cert
     */
    protected abstract void initPasswordCert(PasswordCert cert);
    
	/**
	 * 处理HTTP协议
	 * @param request
	 */
	protected abstract void dealHttpMethod(Request request);
	
	/**
	 * 处理Header
	 * @param headers
	 */
	protected abstract void dealHeaders(List<Header> headers);
	
	/**
	 * 处理Cookie
	 * @param cookies
	 */
	protected abstract void dealCookies(List<Cookie> cookies);
	
	/**
	 * 处理HTTP的多段正文
	 * @param bodyElements
	 */
	protected abstract void dealBodyElement(List<BodyElement> bodyElements);
	
	/**
	 * 执行HTTP通讯并得到结果
	 * @return
	 */
	protected abstract Response executeMethod();
	
	/**
	 * 得到带参数的URL
	 * @param request
	 * @return
	 */
	protected String getUrl(Request request){
		StringBuilder sb = new StringBuilder(request.getUrl());
		if(request.getParameters()!=null){
		   if(request.getUrl().indexOf("?")<0){
			  sb.append("?");
		   }
		   for(Parameter p:request.getParameters()){
			  Object value = p.getValue();
			  String key = p.getName();
			  try{
				  if (value.getClass().isArray()) {
	                  Object[] arrayValue = (Object[]) value;
	                  for (Object o : arrayValue) {
	                      appendParameter(request,sb, key, o);
	                  }
	              } else {
	                  appendParameter(request,sb, key, value);
	              }
			  }catch(Exception e){
				  throw new HttpVisitorException("创建URL发生异常",e);
			  }
			  
		   }
		}
		return sb.toString();
	}
	
	protected Charset getCharset(Request request){
		return request.getCharset()==null?DEFAULT_REQUEST_CHARSET:request.getCharset();
	}
	
	private void appendParameter(Request request,StringBuilder sb, String key, Object value) throws UnsupportedEncodingException {
        sb.append("&");
        sb.append(URLEncoder.encode(key, getCharset(request).name()));
        sb.append("=");
        sb.append(URLEncoder.encode(value.toString(), getCharset(request).name()));
    }
}
