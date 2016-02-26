package org.tinygroup.httpvisitor.request;

import org.tinygroup.httpvisitor.Request;
import org.tinygroup.httpvisitor.factory.MethodMode;


/**
 * 仅支持Head构建
 * @author yancheng11334
 * 
 */
public class HeadRequestBuilder extends HttpRequestBuilder<HeadRequestBuilder>{

	public HeadRequestBuilder(MethodMode methodMode, String url) {
		super(methodMode, url);
	}

	public Request execute() {
		return new Request(methodMode,url,headers,cookies,parameters,null,charset);
	}

	protected HeadRequestBuilder self() {
		return this;
	}


}
