package org.tinygroup.httpvisitor.builder;

import java.io.InputStream;

import org.tinygroup.httpvisitor.client.ClientBuilder;
import org.tinygroup.httpvisitor.client.SingleClientBuilder;
import org.tinygroup.httpvisitor.factory.MethodMode;
import org.tinygroup.httpvisitor.request.HttpRequestBuilder;
import org.tinygroup.httpvisitor.request.PostRequestBuilder;
import org.tinygroup.httpvisitor.request.PostRequestBuilderInterface;
import org.tinygroup.vfs.FileObject;

public class PostRequestClientBuilder extends RequestClientBuilder<PostRequestClientBuilder,PostRequestBuilder> implements PostRequestBuilderInterface<PostRequestClientBuilder>{

	private SingleClientBuilder singleClientBuilder;
    private PostRequestBuilder requestBuilder;
    
    public PostRequestClientBuilder(MethodMode methodMode, String url){
    	requestBuilder = new PostRequestBuilder(methodMode,url);
    	singleClientBuilder = new SingleClientBuilder();
    } 
    
	public PostRequestClientBuilder data(byte[] data) {
		requestBuilder.data(data);
		return self();
	}

	public PostRequestClientBuilder data(InputStream in) {
		requestBuilder.data(in);
		return self();
	}

	public PostRequestClientBuilder data(String body) {
		requestBuilder.data(body);
		return self();
	}

	public PostRequestClientBuilder multipart(String name, String content) {
		requestBuilder.multipart(name, content);
		return self();
	}

	public PostRequestClientBuilder multipart(String name, FileObject file) {
		requestBuilder.multipart(name, file);
		return self();
	}

	protected PostRequestClientBuilder self() {
		return this;
	}

	protected ClientBuilder findClientBuilder() {
		return singleClientBuilder;
	}

	protected HttpRequestBuilder<PostRequestBuilder> findRequsetBuilder() {
		return requestBuilder;
	}

}
