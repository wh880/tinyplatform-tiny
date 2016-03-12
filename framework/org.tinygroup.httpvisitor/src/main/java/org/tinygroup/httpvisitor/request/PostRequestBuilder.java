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

public  class PostRequestBuilder extends HttpRequestBuilder<PostRequestBuilder> implements PostRequestBuilderInterface<PostRequestBuilder>{

	
	public PostRequestBuilder(MethodMode methodMode, String url) {
		super(methodMode, url);
	}

	public PostRequestBuilder data(byte[] data) {
		bodyElements.add(new ByteArrayElement(data));
		return self();
	}

	public PostRequestBuilder data(InputStream in) {
		bodyElements.add(new InputStreamElement(in));
		return self();
	}

	public PostRequestBuilder data(String body) {
		bodyElements.add(new StringElement(body));
		return self();
	}

	public PostRequestBuilder data(File file) {
		bodyElements.add(new FileElement(file));
		return self();
	}

	public PostRequestBuilder data(BodyElement element) {
		bodyElements.add(element);
		return self();
	}

	public PostRequestBuilder multipart(BodyElement... elements) {
		for(BodyElement element:elements){
		    bodyElements.add(element);
		}
		return self();
	}

	public Request execute() {
		List<Header> hs = CollectionUtil.createArrayList(headers.values());
		List<Cookie> cs = CollectionUtil.createArrayList(cookies.values());
		List<Parameter> ps = CollectionUtil.createArrayList(parameters.values());
		return new Request(methodMode,url,hs,cs,ps,bodyElements,charset);
	}

	protected PostRequestBuilder self() {
		return this;
	}



}
