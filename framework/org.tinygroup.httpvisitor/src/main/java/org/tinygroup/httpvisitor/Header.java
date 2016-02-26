package org.tinygroup.httpvisitor;

/**
 * HTTP请求和响应的首部(统一底层具体实现)
 * @author yancheng11334
 *
 */
public interface Header {

	String getName();
	
	String getValue();
}
