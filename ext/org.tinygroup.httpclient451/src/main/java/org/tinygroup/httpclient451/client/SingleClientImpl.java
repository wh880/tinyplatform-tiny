package org.tinygroup.httpclient451.client;

import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.tinygroup.context.Context;
import org.tinygroup.httpclient451.response.DefaultResponse;
import org.tinygroup.httpvisitor.Request;
import org.tinygroup.httpvisitor.Response;
import org.tinygroup.httpvisitor.execption.HttpVisitorException;

/**
 * 线程使用一个HttpClient
 * @author yancheng11334
 *
 */
public class SingleClientImpl extends AbstractHttpClient451{

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
		return false;
	}

	protected HttpClientConnectionManager buildHttpClientConnectionManager(
			Context context) {
		return new BasicHttpClientConnectionManager();
	}

}
