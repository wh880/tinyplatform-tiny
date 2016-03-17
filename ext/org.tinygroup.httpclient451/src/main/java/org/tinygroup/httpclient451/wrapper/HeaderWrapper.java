package org.tinygroup.httpclient451.wrapper;

import org.tinygroup.httpvisitor.Header;

/**
 * Header包装类
 * @author yancheng11334
 *
 */
public class HeaderWrapper implements Header{

	private org.apache.http.Header header;
	
	public HeaderWrapper(org.apache.http.Header header){
		this.header = header;
	}
	
	public String getName() {
		return header.getName();
	}

	public String getValue() {
		return header.getValue();
	}

}
