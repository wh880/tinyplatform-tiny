package org.tinygroup.httpvisitor.request;

import java.util.List;

import org.tinygroup.commons.tools.CollectionUtil;
import org.tinygroup.httpvisitor.Cookie;
import org.tinygroup.httpvisitor.Header;
import org.tinygroup.httpvisitor.MethodMode;
import org.tinygroup.httpvisitor.Request;
import org.tinygroup.httpvisitor.struct.Parameter;


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
		List<Header> hs = CollectionUtil.createArrayList(headers.values());
		List<Cookie> cs = CollectionUtil.createArrayList(cookies.values());
		List<Parameter> ps = CollectionUtil.createArrayList(parameters.values());
		return new Request(methodMode,url,hs,cs,ps,null,charset);
	}

	protected HeadRequestBuilder self() {
		return this;
	}


}
