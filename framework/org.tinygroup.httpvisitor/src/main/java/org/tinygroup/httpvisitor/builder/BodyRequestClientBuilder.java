package org.tinygroup.httpvisitor.builder;

import java.io.File;
import java.io.InputStream;

import org.tinygroup.httpvisitor.BodyElement;
import org.tinygroup.httpvisitor.MethodMode;
import org.tinygroup.httpvisitor.client.ClientBuilder;
import org.tinygroup.httpvisitor.client.SingleClientBuilder;
import org.tinygroup.httpvisitor.request.BodyRequestBuilder;
import org.tinygroup.httpvisitor.request.BodyRequestBuilderInterface;
import org.tinygroup.httpvisitor.request.HttpRequestBuilder;

public class BodyRequestClientBuilder extends RequestClientBuilder<BodyRequestClientBuilder,BodyRequestBuilder> implements BodyRequestBuilderInterface<BodyRequestClientBuilder>{

	private SingleClientBuilder singleClientBuilder;
    private BodyRequestBuilder requestBuilder;
    
    /**
     * 通过工厂构造
     * @param methodMode
     * @param url
     */
    BodyRequestClientBuilder(MethodMode methodMode, String url,String templateId){
    	requestBuilder = new BodyRequestBuilder(methodMode,url);
    	singleClientBuilder = new SingleClientBuilder(templateId);
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

	public BodyRequestClientBuilder data(File file) {
		requestBuilder.data(file);
		return self();
	}

	public BodyRequestClientBuilder data(BodyElement element) {
		requestBuilder.data(element);
		return self();
	}

}
