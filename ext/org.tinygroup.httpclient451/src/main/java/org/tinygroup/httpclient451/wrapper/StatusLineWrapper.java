package org.tinygroup.httpclient451.wrapper;

import org.tinygroup.httpvisitor.StatusLine;

/**
 * StatusLine包装类
 * @author yancheng11334
 *
 */
public class StatusLineWrapper implements StatusLine{

	private org.apache.http.StatusLine statusLine;
	
	public StatusLineWrapper(org.apache.http.StatusLine statusLine){
		this.statusLine = statusLine;
	}
	
	public String getProtocol() {
		return statusLine.getProtocolVersion().getProtocol();
	}

	public int getStatusCode() {
		return statusLine.getStatusCode();
	}

	public String getReasonPhrase() {
		return statusLine.getReasonPhrase();
	}

}
