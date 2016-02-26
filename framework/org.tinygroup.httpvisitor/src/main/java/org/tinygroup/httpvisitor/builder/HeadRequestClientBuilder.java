package org.tinygroup.httpvisitor.builder;

import org.tinygroup.httpvisitor.client.ClientBuilder;
import org.tinygroup.httpvisitor.client.SingleClientBuilder;
import org.tinygroup.httpvisitor.factory.MethodMode;
import org.tinygroup.httpvisitor.request.HeadRequestBuilder;
import org.tinygroup.httpvisitor.request.HttpRequestBuilder;

public class HeadRequestClientBuilder extends RequestClientBuilder<HeadRequestClientBuilder,HeadRequestBuilder>{

    private SingleClientBuilder singleClientBuilder;
    private HeadRequestBuilder requestBuilder;
    
    public HeadRequestClientBuilder(MethodMode methodMode, String url){
    	requestBuilder = new HeadRequestBuilder(methodMode,url);
    	singleClientBuilder = new SingleClientBuilder();
    }

	protected HeadRequestClientBuilder self() {
		return this;
	}

	protected ClientBuilder findClientBuilder() {
		return singleClientBuilder;
	}

	protected HttpRequestBuilder<HeadRequestBuilder> findRequsetBuilder() {
		return requestBuilder;
	}

}
