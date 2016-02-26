package org.tinygroup.httpvisitor.builder;

import java.io.InputStream;

import org.tinygroup.httpvisitor.client.ClientBuilder;
import org.tinygroup.httpvisitor.client.SingleClientBuilder;
import org.tinygroup.httpvisitor.factory.MethodMode;
import org.tinygroup.httpvisitor.request.BodyRequestBuilder;
import org.tinygroup.httpvisitor.request.BodyRequestBuilderInterface;
import org.tinygroup.httpvisitor.request.HttpRequestBuilder;

public class BodyRequestClientBuilder extends RequestClientBuilder<BodyRequestClientBuilder,BodyRequestBuilder> implements BodyRequestBuilderInterface<BodyRequestClientBuilder>{

	private SingleClientBuilder singleClientBuilder;
    private BodyRequestBuilder requestBuilder;
    
    public BodyRequestClientBuilder(MethodMode methodMode, String url){
    	requestBuilder = new BodyRequestBuilder(methodMode,url);
    	singleClientBuilder = new SingleClientBuilder();
    } 
    
	protected BodyRequestClientBuilder self() {
		return this;
	}

	protected ClientBuilder findClientBuilder() {
		return singleClientBuilder;
	}

	protected HttpRequestBuilder<BodyRequestBuilder> findRequsetBuilder() {
		return requestBuilder;
	}

	public BodyRequestClientBuilder data(byte[] data) {
		requestBuilder.data(data);
		return self();
	}

	public BodyRequestClientBuilder data(InputStream in) {
		requestBuilder.data(in);
		return self();
	}

	public BodyRequestClientBuilder data(String body) {
		requestBuilder.data(body);
		return self();
	}

}
