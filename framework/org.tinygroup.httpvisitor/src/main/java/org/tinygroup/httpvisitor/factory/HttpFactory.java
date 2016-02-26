package org.tinygroup.httpvisitor.factory;

import org.tinygroup.httpvisitor.builder.BodyRequestClientBuilder;
import org.tinygroup.httpvisitor.builder.HeadRequestClientBuilder;
import org.tinygroup.httpvisitor.builder.PostRequestClientBuilder;

/**
 * HTTP工厂类
 * @author yancheng11334
 *
 */
public class HttpFactory {
	
	/**
	 * 构造GET操作
	 * @param url
	 * @return
	 */
	public static HeadRequestClientBuilder get(String url){
		return new HeadRequestClientBuilder(MethodMode.GET,url);
	}
	
	/**
	 * 构造OPTIONS操作
	 * @param url
	 * @return
	 */
	public static HeadRequestClientBuilder options(String url){
		return new HeadRequestClientBuilder(MethodMode.OPTIONS,url);
	}
	
	/**
	 * 构造TRACE操作
	 * @param url
	 * @return
	 */
	public static HeadRequestClientBuilder trace(String url){
		return new HeadRequestClientBuilder(MethodMode.TRACE,url);
	}
	
	/**
	 * 构造DELETE操作
	 * @param url
	 * @return
	 */
	public static HeadRequestClientBuilder delete(String url){
		return new HeadRequestClientBuilder(MethodMode.DELETE,url);
	}
	
	/**
	 * 构造HEAD操作
	 * @param url
	 * @return
	 */
	public static HeadRequestClientBuilder head(String url){
		return new HeadRequestClientBuilder(MethodMode.HEAD,url);
	}
	
	/**
	 * 构造PUT操作
	 * @param url
	 * @return
	 */
	public static BodyRequestClientBuilder put(String url){
		return new BodyRequestClientBuilder(MethodMode.PUT,url);
	}
	
	/**
	 * 构造PATCH操作
	 * @param url
	 * @return
	 */
	public static BodyRequestClientBuilder patch(String url){
		return new BodyRequestClientBuilder(MethodMode.PATCH,url);
	}
	
	/**
	 * 构造POST操作
	 * @param url
	 * @return
	 */
	public static PostRequestClientBuilder post(String url){
		return new PostRequestClientBuilder(MethodMode.POST,url);
	}

}
