package org.tinygroup.httpvisitor.request;

import java.nio.charset.Charset;
import java.util.Map;

import org.tinygroup.httpvisitor.Cookie;
import org.tinygroup.httpvisitor.Response;

/**
 * HTTP链式构造器接口
 * @author yancheng11334
 *
 * @param <T>
 */
public interface HttpRequestBuilderInterface<T> {
	
	/**
	 * 设置请求编码
	 * @param charset
	 * @return
	 */
	T charset(String charset);
	
	/**
	 * 设置请求编码
	 * @param charset
	 * @return
	 */
	T charset(Charset charset);
	
	/**
	 * 增加单条URL参数
	 * @param name
	 * @param value
	 * @return
	 */
	T param(String name,Object value);
	
	/**
	 * 批量增加URL参数
	 * @param maps
	 * @return
	 */
	T params(Map<String,Object> maps);
	
	/**
	 * 增加单条HTTP报文首部参数
	 * @param name
	 * @param value
	 * @return
	 */
    T header(String name,String value);
	
    /**
     * 批量增加HTTP报文首部参数
     * @param maps
     * @return
     */
	T headers(Map<String,String> maps);
	
	/**
	 * 增加单个cookie
	 * @param domain
	 * @param name
	 * @param value
	 * @return
	 */
	T cookie(String domain,String name,String value);
	
	/**
	 * 增加单个cookie
	 * @param cookie
	 * @return
	 */
	T cookie(Cookie cookie);
	
	/**
	 * 批量增加cookie
	 * @param cookies
	 * @return
	 */
	T cookies(Map<String,Cookie> cookies);
	
	
}
