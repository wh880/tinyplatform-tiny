package org.tinygroup.httpclient31.response;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.HttpState;
import org.tinygroup.commons.io.ByteArrayOutputStream;
import org.tinygroup.commons.io.StreamUtil;
import org.tinygroup.httpclient31.wrapper.CookieWrapper;
import org.tinygroup.httpclient31.wrapper.HeaderWrapper;
import org.tinygroup.httpclient31.wrapper.StatusLineWrapper;
import org.tinygroup.httpvisitor.Cookie;
import org.tinygroup.httpvisitor.Header;
import org.tinygroup.httpvisitor.Response;
import org.tinygroup.httpvisitor.StatusLine;
import org.tinygroup.httpvisitor.execption.HttpVisitorException;
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
		org.apache.commons.httpclient.Header[] headers = method
				.getResponseHeaders();
		Header[] newHeaders = null;
		if (headers != null) {
			newHeaders = new Header[headers.length];
			for (int i = 0; i < headers.length; i++) {
				newHeaders[i] = new HeaderWrapper(headers[i]);
			}
		}
		return newHeaders;
	}

	public Header getHeader(String name) {
		org.apache.commons.httpclient.Header header = method
				.getResponseHeader(name);
		if (header != null) {
			return new HeaderWrapper(header);
		}
		return null;
	}

	public Cookie[] getCookies() {
		org.apache.commons.httpclient.Cookie[] cookies = state.getCookies();
		Cookie[] newCookies = null;
		if (cookies != null) {
			newCookies = new Cookie[cookies.length];
			for (int i = 0; i < cookies.length; i++) {
				newCookies[i] = new CookieWrapper(cookies[i]);
			}
		}
		return newCookies;
	}

	public Cookie getCookie(String name) {
		org.apache.commons.httpclient.Cookie[] cookies = state.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals(name)) {
					return new CookieWrapper(cookies[i]);
				}
			}
		}
		return null;
	}

	protected boolean isGzip() {
		org.apache.commons.httpclient.Header responseHeader = method
				.getResponseHeader("Content-Encoding");
		if (responseHeader != null) {
			String acceptEncoding = responseHeader.getValue();
			if (acceptEncoding != null && ("gzip").equals(acceptEncoding)) {
				return true;
			}
		}
		return false;
	}

	public String text() {
		byte[] b = bytes();
		return b == null ? null : new String(b, charset);
	}

	public byte[] bytes() {
		try {
			InputStream inputStream = stream();
			if(inputStream==null){
			   return null;
			}
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			StreamUtil.io(inputStream, outputStream, false, false);
			return outputStream.toByteArray().toByteArray();
		} catch (IOException e) {
			throw new HttpVisitorException("读取数组发生异常", e);
		}
	}

	public InputStream stream() {
		try {
			return isGzip() ? new GZIPInputStream(
					method.getResponseBodyAsStream()) : method
					.getResponseBodyAsStream();
		} catch (IOException e) {
			throw new HttpVisitorException("读取流发生异常", e);
		}
	}

	protected Response self() {
		return this;
	}

}
