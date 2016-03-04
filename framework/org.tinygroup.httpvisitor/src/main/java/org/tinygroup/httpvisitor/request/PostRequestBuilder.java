package org.tinygroup.httpvisitor.request;

import java.io.InputStream;

import org.tinygroup.httpvisitor.MethodMode;
import org.tinygroup.httpvisitor.Request;
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
		return new Request(methodMode,url,headers,cookies,parameters,bodyElements,charset);
	}

	protected PostRequestBuilder self() {
		return this;
	}


}
