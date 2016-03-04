package org.tinygroup.httpvisitor;

import java.nio.charset.Charset;
import java.util.List;

import org.tinygroup.httpvisitor.struct.BodyElement;
import org.tinygroup.httpvisitor.struct.Parameter;


/**
 * HTTP请求对象(框架构建，无需考虑包装)
 * @author yancheng11334
 *
 */
public class Request {

	private final MethodMode method;
    private final String url;
    private final List<Header> headers;
    private final List<Cookie> cookies;
    private final List<Parameter> parameters;
    private final List<BodyElement> bodyElements;
    private final Charset charset;
	
	public Request(MethodMode method, String url, List<Header> headers,
			List<Cookie> cookies, List<Parameter> parameters,
			List<BodyElement> bodyElements,  Charset charset) {
		super();
		this.method = method;
		this.url = url;
		this.headers = headers;
		this.cookies = cookies;
		this.parameters = parameters;
		this.bodyElements = bodyElements;
		this.charset = charset;
	}
	public List<BodyElement> getBodyElements() {
		return bodyElements;
	}
	public MethodMode getMethod() {
		return method;
	}
	public String getUrl() {
		return url;
	}
	public List<Header> getHeaders() {
		return headers;
	}
	public List<Cookie> getCookies() {
		return cookies;
	}
	public List<Parameter> getParameters() {
		return parameters;
	}
	public Charset getCharset() {
		return charset;
	}
    
}
