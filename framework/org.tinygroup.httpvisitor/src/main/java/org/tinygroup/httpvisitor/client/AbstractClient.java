package org.tinygroup.httpvisitor.client;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;

import org.tinygroup.httpvisitor.Request;
import org.tinygroup.httpvisitor.execption.HttpVisitorException;
import org.tinygroup.httpvisitor.struct.Parameter;

public abstract class AbstractClient implements ClientInterface{

	protected static final Charset DEFAULT_REQUEST_CHARSET = Charset.forName("ISO-8859-1");
	
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
