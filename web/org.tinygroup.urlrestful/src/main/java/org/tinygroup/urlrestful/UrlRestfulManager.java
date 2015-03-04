package org.tinygroup.urlrestful;

import org.tinygroup.urlrestful.config.UrlRestfuls;

/**
 * restful管理器
 * @author renhui
 *
 */
public interface UrlRestfulManager {
	
	String URLREST_XSTREAM="urlrestful";
	
	/**
	 * 增加restful配置信息
	 * @param urlRestfuls
	 */
	public void addUrlRestfuls(UrlRestfuls urlRestfuls);
	/**
	 * 移除restful配置信息
	 * @param urlRestfuls
	 */
	public void removeRestfuls(UrlRestfuls urlRestfuls);
	
	/**
	 * 根据请求路径、请求的方法以及请求头的accept 组装此次请求的上下文对象
	 * @param requestPath
	 * @param httpMethod
	 * @param accept
	 * @return
	 */
	public RestfulContext getUrlMappingWithRequet(String requestPath,String httpMethod,String accept);
	

}
