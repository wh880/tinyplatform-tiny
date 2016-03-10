package org.tinygroup.httpvisitor.request;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.tinygroup.httpvisitor.Cookie;
import org.tinygroup.httpvisitor.Executable;
import org.tinygroup.httpvisitor.Header;
import org.tinygroup.httpvisitor.MethodMode;
import org.tinygroup.httpvisitor.Request;
import org.tinygroup.httpvisitor.struct.BodyElement;
import org.tinygroup.httpvisitor.struct.Parameter;
import org.tinygroup.httpvisitor.struct.SimpleCookie;
import org.tinygroup.httpvisitor.struct.SimpleHeader;

/**
 * 抽象的request构造器
 * 
 * @author yancheng11334
 * 
 * @param <T>
 */
public abstract class HttpRequestBuilder<T extends HttpRequestBuilder<T>>
		implements Executable<Request>, HttpRequestBuilderInterface<T> {
   
	protected final MethodMode methodMode;
	protected final String url;
	
    protected Map<String,Header> headers= new HashMap<String,Header>();
    protected Map<String,Cookie> cookies= new HashMap<String,Cookie>();
    protected Map<String,Parameter> parameters = new HashMap<String,Parameter>();
    protected Map<String,BodyElement> bodyElements = new HashMap<String,BodyElement>();
    protected Charset charset;
	
	public HttpRequestBuilder(MethodMode methodMode,String url){
		this.methodMode = methodMode;
		this.url = url;
	}
	
	public T charset(String charset){
		this.charset = Charset.forName(charset);
		return self();
	}
	
	public T charset(Charset charset){
		this.charset = charset;
		return self();
	}

	public T param(String name, Object value) {
		addParam(new Parameter(name,value));
		return self();
	}

	public T params(Map<String, Object> maps) {
		for(Entry<String,Object> entry:maps.entrySet()){
			addParam(new Parameter(entry.getKey(),entry.getValue()));
		}
		return self();
	}
	
	private void addParam(Parameter p){
		this.parameters.put(p.getName(), p);
	}

	public T header(String name, String value) {
		addHeader(new SimpleHeader(name,value));
		return self();
	}

	public T headers(Map<String, String> maps) {
		for(Entry<String,String> entry:maps.entrySet()){
			addHeader(new SimpleHeader(entry.getKey(),entry.getValue()));
		}
		return self();
	}
	
	private void addHeader(Header header){
		this.headers.put(header.getName(), header);
	}

	public T cookie(String domain, String name, String value) {
		addCookie(new SimpleCookie(name,value,null,null,domain,false));
		return self();
	}

	public T cookie(Cookie cookie) {
		addCookie(cookie);
		return self();
	}

	public T cookies(Map<String, Cookie> cookies) {
		for(Entry<String,Cookie> entry:cookies.entrySet()){
			addCookie(entry.getValue());
		}
		return self();
	}
	
	private void addCookie(Cookie cookie){
		this.cookies.put(cookie.getName(), cookie);
	}

	protected abstract T self();
}
