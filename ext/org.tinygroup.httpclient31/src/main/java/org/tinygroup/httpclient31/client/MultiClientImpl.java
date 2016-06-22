package org.tinygroup.httpclient31.client;

import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.tinygroup.httpclient31.response.DefaultResponse;
import org.tinygroup.httpvisitor.Request;
import org.tinygroup.httpvisitor.Response;
import org.tinygroup.httpvisitor.execption.HttpVisitorException;

/**
 * 多线程共享一个HttpClient
 * @author yancheng11334
 *
 */
public class MultiClientImpl extends AbstractHttpClient31{

	public void close() throws IOException {
		httpClient.getHttpConnectionManager().closeIdleConnections(0);
	}

	public  Response execute(Request request) {
		Charset requestCharset = getCharset(request);
		HttpMethodBase method = dealHttpMethod(request);
		final HttpState state = httpClient.getState();
		dealHeaders(method,request.getHeaders());
		dealCookies(method,state,request.getCookies());
		dealBodyElement(method,requestCharset,request.getBodyElements());
		// 具体执行逻辑
		try {
			httpClient.executeMethod(null,method,state);
			return new DefaultResponse(method, state);
		} catch (Exception e) {
			throw new HttpVisitorException("执行HTTP访问发生异常", e);
		}
	}

	protected  HttpClient buildHttpClient() {
		return new HttpClient(new MultiThreadedHttpConnectionManager());
	}

	public boolean allowMultiton() {
		return true;
	}

}
