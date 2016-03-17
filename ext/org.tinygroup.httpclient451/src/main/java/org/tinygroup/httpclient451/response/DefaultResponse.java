package org.tinygroup.httpclient451.response;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.tinygroup.httpclient451.wrapper.CookieArrayWrapper;
import org.tinygroup.httpclient451.wrapper.HeaderArrayWrapper;
import org.tinygroup.httpclient451.wrapper.StatusLineWrapper;
import org.tinygroup.httpvisitor.Cookie;
import org.tinygroup.httpvisitor.Header;
import org.tinygroup.httpvisitor.Response;
import org.tinygroup.httpvisitor.StatusLine;
import org.tinygroup.httpvisitor.response.AbstractResponse;

/**
 * 基于httpclient4.5.1的响应实现
 * @author yancheng11334
 *
 */
public class DefaultResponse extends AbstractResponse implements Response {

	private CookieStore cookieStore;
	private CloseableHttpResponse closeableHttpResponse;
	private StatusLine statusLine;

	public DefaultResponse(CookieStore cookieStore,
			CloseableHttpResponse closeableHttpResponse) {
		super();
		this.cookieStore = cookieStore;
		this.closeableHttpResponse = closeableHttpResponse;
	}

	public void close() throws IOException {
		closeableHttpResponse.close();
	}

	public StatusLine getStatusLine() {
		if(statusLine==null){
		   statusLine = new StatusLineWrapper(closeableHttpResponse.getStatusLine());
		}
		return statusLine;
	}

	public Header[] getHeaders() {
		return new HeaderArrayWrapper(closeableHttpResponse.getAllHeaders()).getHeaders();
	}

	public Cookie[] getCookies() {
		return new CookieArrayWrapper(cookieStore.getCookies()).getCookies();
	}

	protected InputStream getSourceInputStream() throws IOException {
		return closeableHttpResponse.getEntity()==null?null:closeableHttpResponse.getEntity().getContent();
	}

	protected Response self() {
		return this;
	}

}
