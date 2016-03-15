package org.tinygroup.httpclient31.wrapper;

import org.tinygroup.httpvisitor.Header;

/**
 * 对Header数组的包装类
 * 
 * @author yancheng11334
 * 
 */
public class HeaderArrayWrapper {

	private org.apache.commons.httpclient.Header[] headers;

	public HeaderArrayWrapper(org.apache.commons.httpclient.Header[] headers) {
		super();
		this.headers = headers;
	}

	public Header[] getHeaders() {
		if (headers != null) {
			Header[] newHeaders = new Header[headers.length];
			for (int i = 0; i < headers.length; i++) {
				newHeaders[i] = new HeaderWrapper(headers[i]);
			}
			return newHeaders;
		} else {
			return null;
		}
	}
}
