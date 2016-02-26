package org.tinygroup.httpvisitor;

/**
 * 状态行接口(统一不同底层实现)
 * @author yancheng11334
 *
 */
public interface StatusLine {

	 String getProtocol();
	 
	 int getStatusCode();

	 String getReasonPhrase();
}
