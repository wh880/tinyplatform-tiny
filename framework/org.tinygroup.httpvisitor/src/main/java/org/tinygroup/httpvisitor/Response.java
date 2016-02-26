package org.tinygroup.httpvisitor;

import java.io.Closeable;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * HTTP响应结果(统一不同底层实现)
 * @author yancheng11334
 *
 */
public interface Response extends Closeable{

	StatusLine getStatusLine();
	
	Header[] getHeaders();
	
	Header getHeader(String name);
	
	Cookie[] getCookies();
	
	Cookie getCookie(String name);
	
	/**
	 * 设置响应编码
	 * @param charset
	 * @return
	 */
	Response charset(String charset);
	
	/**
	 * 设置响应编码
	 * @param charset
	 * @return
	 */
	Response charset(Charset charset);
	
	/**
	 * 得到文本内容
	 * @return
	 */
	String text();
	
	/**
	 * 得到字节数组
	 * @return
	 */
	byte[] bytes();
	
	/**
	 * 得到流
	 * @return
	 */
	InputStream stream();
	
}
