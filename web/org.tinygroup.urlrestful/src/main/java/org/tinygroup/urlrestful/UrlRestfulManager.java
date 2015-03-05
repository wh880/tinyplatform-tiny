package org.tinygroup.urlrestful;

import org.tinygroup.urlrestful.config.Rules;

/**
 * restful管理器
 * @author renhui
 *
 */
public interface UrlRestfulManager {
	
	String URL_RESTFUL_XSTREAM ="urlrestful";
	
	/**
	 * 增加restful配置信息
	 * @param Rules
	 */
	public void addRules(Rules Rules);
	/**
	 * 移除restful配置信息
	 * @param Rules
	 */
	public void removeRules(Rules Rules);
	
	/**
	 * 根据请求路径、请求的方法以及请求头的accept 组装此次请求的上下文对象
	 * @param requestPath
	 * @param httpMethod
	 * @param accept
	 * @return
	 */
	public Context getContext(String requestPath, String httpMethod, String accept);
	

}
