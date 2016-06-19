package org.tinygroup.httpvisitor.builder;

import org.tinygroup.httpvisitor.MethodMode;
import org.tinygroup.httpvisitor.client.ClientBuilder;
import org.tinygroup.httpvisitor.client.SingleClientBuilder;
import org.tinygroup.httpvisitor.request.HeadRequestBuilder;
import org.tinygroup.httpvisitor.request.HttpRequestBuilder;

public class HeadRequestClientBuilder extends RequestClientBuilder<HeadRequestClientBuilder,HeadRequestBuilder>{

    private SingleClientBuilder singleClientBuilder;
    private HeadRequestBuilder requestBuilder;
    
    /**
     * 通过工厂构造
     * @param methodMode
     * @param url
     */
    HeadRequestClientBuilder(MethodMode methodMode, String url,String templateId){
    	requestBuilder = new HeadRequestBuilder(methodMode,url);
    	singleClientBuilder = new SingleClientBuilder(templateId);
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
