package org.tinygroup.httpvisitor.request;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import org.tinygroup.commons.tools.CollectionUtil;
import org.tinygroup.httpvisitor.BodyElement;
import org.tinygroup.httpvisitor.Cookie;
import org.tinygroup.httpvisitor.Header;
import org.tinygroup.httpvisitor.MethodMode;
import org.tinygroup.httpvisitor.Request;
import org.tinygroup.httpvisitor.struct.ByteArrayElement;
import org.tinygroup.httpvisitor.struct.FileElement;
import org.tinygroup.httpvisitor.struct.InputStreamElement;
import org.tinygroup.httpvisitor.struct.Parameter;
import org.tinygroup.httpvisitor.struct.StringElement;

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
		return new Request(methodMode,url,hs,cs,ps,bodyElements,charset);
	}

	protected BodyRequestBuilder self() {
		return this;
	}

	public BodyRequestBuilder data(byte[] data) {
		bodyElements.add(new ByteArrayElement(data));
		return self();
	}

	public BodyRequestBuilder data(InputStream in) {
		bodyElements.add(new InputStreamElement(in));
		return self();
	}

	public BodyRequestBuilder data(String body) {
		bodyElements.add(new StringElement(body));
		return self();
	}

	public BodyRequestBuilder data(File file) {
		bodyElements.add(new FileElement(file));
		return self();
	}

	public BodyRequestBuilder data(BodyElement element) {
		bodyElements.add(element);
		return self();
	}

}
