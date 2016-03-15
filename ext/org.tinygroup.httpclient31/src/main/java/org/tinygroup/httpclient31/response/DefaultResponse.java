package org.tinygroup.httpclient31.response;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.HttpState;
import org.tinygroup.httpclient31.wrapper.CookieArrayWrapper;
import org.tinygroup.httpclient31.wrapper.HeaderArrayWrapper;
import org.tinygroup.httpclient31.wrapper.StatusLineWrapper;
import org.tinygroup.httpvisitor.Cookie;
import org.tinygroup.httpvisitor.Header;
import org.tinygroup.httpvisitor.Response;
import org.tinygroup.httpvisitor.StatusLine;
import org.tinygroup.httpvisitor.response.AbstractResponse;

/**
 * 基于httpclient3.1的响应实现
 * 
 * @author yancheng11334
 * 
 */
public class DefaultResponse extends AbstractResponse implements Response {

	private HttpMethodBase method;
	private HttpState state;
	private StatusLine statusLine;

	public DefaultResponse(HttpMethodBase method, HttpState state) {
		this.method = method;
		this.state = state;
	}

	public void close() throws IOException {
		method.releaseConnection();
		state.clear();
	}

	public StatusLine getStatusLine() {
		if (statusLine == null) {
			statusLine = new StatusLineWrapper(method.getStatusLine());
		}
		return statusLine;
	}

	public Header[] getHeaders() {
		return new HeaderArrayWrapper(method.getResponseHeaders()).getHeaders();
	}

	public Cookie[] getCookies() {
		return new CookieArrayWrapper(state.getCookies()).getCookies();
	}

	protected InputStream getSourceInputStream() throws IOException {
		return method.getResponseBodyAsStream();
	}

	protected Response self() {
		return this;
	}


}
