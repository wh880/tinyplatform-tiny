package org.tinygroup.httpvisitor.request;

import java.io.InputStream;
import java.util.List;

import org.tinygroup.commons.tools.CollectionUtil;
import org.tinygroup.httpvisitor.Cookie;
import org.tinygroup.httpvisitor.Header;
import org.tinygroup.httpvisitor.MethodMode;
import org.tinygroup.httpvisitor.Request;
import org.tinygroup.httpvisitor.struct.BodyElement;
import org.tinygroup.httpvisitor.struct.Parameter;

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
		List<Header> hs = CollectionUtil.createArrayList(headers.values());
		List<Cookie> cs = CollectionUtil.createArrayList(cookies.values());
		List<Parameter> ps = CollectionUtil.createArrayList(parameters.values());
		List<BodyElement> bs = CollectionUtil.createArrayList(bodyElements.values());
		return new Request(methodMode,url,hs,cs,ps,bs,charset);
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
