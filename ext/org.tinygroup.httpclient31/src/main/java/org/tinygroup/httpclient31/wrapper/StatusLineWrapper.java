package org.tinygroup.httpclient31.wrapper;

import org.tinygroup.httpvisitor.StatusLine;

/**
 * StatusLine包装类
 * @author yancheng11334
 *
 */
public class StatusLineWrapper implements StatusLine{

	private org.apache.commons.httpclient.StatusLine statusLine;
	
	public StatusLineWrapper(org.apache.commons.httpclient.StatusLine statusLine){
		this.statusLine = statusLine;
	}
	
	public String getProtocol() {
		return statusLine.getHttpVersion();
	}

	public int getStatusCode() {
		return statusLine.getStatusCode();
	}

	public String getReasonPhrase() {
		return statusLine.getReasonPhrase();
	}

}
