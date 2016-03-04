package org.tinygroup.httpvisitor.request;

import java.io.InputStream;

import org.tinygroup.httpvisitor.MethodMode;
import org.tinygroup.httpvisitor.Request;

/**
 * 支持Head、Body的请求构建
 * @author yancheng11334
 *
 */
public class BodyRequestBuilder extends HttpRequestBuilder<BodyRequestBuilder> implements BodyRequestBuilderInterface<BodyRequestBuilder>{

	public BodyRequestBuilder(MethodMode methodMode, String url) {
		super(methodMode, url);
	}

	public Request execute() {
		return new Request(methodMode,url,headers,cookies,parameters,bodyElements,charset);
	}

	protected BodyRequestBuilder self() {
		return this;
	}

	public BodyRequestBuilder data(byte[] data) {
		// TODO
		return self();
	}

	public BodyRequestBuilder data(InputStream in) {
		// TODO
		return self();
	}

	public BodyRequestBuilder data(String body) {
		// TODO
		return self();
	}

}
