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
import org.tinygroup.vfs.FileObject;

public  class PostRequestBuilder extends HttpRequestBuilder<PostRequestBuilder> implements PostRequestBuilderInterface<PostRequestBuilder>{

	
	public PostRequestBuilder(MethodMode methodMode, String url) {
		super(methodMode, url);
	}

	public PostRequestBuilder multipart(String name, String content) {
		// TODO Auto-generated method stub
		return self();
	}

	public PostRequestBuilder multipart(String name, FileObject file) {
		// TODO Auto-generated method stub
		return self();
	}

	public PostRequestBuilder data(byte[] data) {
		// TODO Auto-generated method stub
		return self();
	}

	public PostRequestBuilder data(InputStream in) {
		// TODO Auto-generated method stub
		return self();
	}

	public PostRequestBuilder data(String body) {
		// TODO Auto-generated method stub
		return self();
	}

	public Request execute() {
		List<Header> hs = CollectionUtil.createArrayList(headers.values());
		List<Cookie> cs = CollectionUtil.createArrayList(cookies.values());
		List<Parameter> ps = CollectionUtil.createArrayList(parameters.values());
		List<BodyElement> bs = CollectionUtil.createArrayList(bodyElements.values());
		return new Request(methodMode,url,hs,cs,ps,bs,charset);
	}

	protected PostRequestBuilder self() {
		return this;
	}


}
