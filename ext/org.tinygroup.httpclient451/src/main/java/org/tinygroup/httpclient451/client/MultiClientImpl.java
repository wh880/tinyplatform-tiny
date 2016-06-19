package org.tinygroup.httpclient451.client;

import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.tinygroup.context.Context;
import org.tinygroup.httpclient451.response.DefaultResponse;
import org.tinygroup.httpvisitor.Request;
import org.tinygroup.httpvisitor.Response;
import org.tinygroup.httpvisitor.client.ClientConstants;
import org.tinygroup.httpvisitor.execption.HttpVisitorException;

public class MultiClientImpl extends AbstractHttpClient451{

	public Response execute(Request request) {
		Charset requestCharset = getCharset(request);
		HttpRequestBase method = dealHttpMethod(requestCharset,request);
		dealHeaders(method,request.getHeaders());
		dealCookies(method,request.getCookies());
		dealBodyElement(method,requestCharset,request.getBodyElements());
		// 具体执行逻辑
		try {
			CloseableHttpResponse closeableHttpResponse = httpClient.execute(
					method, httpClientContext);
			return new DefaultResponse(method,httpClientContext.getCookieStore(),
					closeableHttpResponse);
		} catch (ClientProtocolException e) {
			throw new HttpVisitorException("Http协议标识存在错误", e);
		} catch (IOException e) {
			throw new HttpVisitorException("Http通讯发生异常", e);
		}
	}

	public boolean allowMultiton() {
		return true;
	}

	protected HttpClientConnectionManager buildHttpClientConnectionManager(
			Context context) {
		PoolingHttpClientConnectionManager  manager = new PoolingHttpClientConnectionManager();
		
		Integer maxTotalConnections = (Integer)context.get(ClientConstants.MAX_TOTAL_CONNECTIONS);
		Integer maxConnectionsPerHost = (Integer)context.get(ClientConstants.MAX_CONNECTIONS_PER_HOST);
		if(maxTotalConnections!=null){
		   manager.setMaxTotal(maxTotalConnections);
		}
		if(maxConnectionsPerHost!=null){
		   manager.setDefaultMaxPerRoute(maxConnectionsPerHost);
		}
		return manager;
	}

}
