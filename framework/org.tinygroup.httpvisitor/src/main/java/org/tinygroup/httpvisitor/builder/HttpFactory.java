package org.tinygroup.httpvisitor.builder;

import org.tinygroup.beancontainer.BeanContainerFactory;
import org.tinygroup.httpvisitor.MethodMode;
import org.tinygroup.httpvisitor.client.ClientInstanceManager;

/**
 * HTTP工厂类
 * @author yancheng11334
 *
 */
public final class HttpFactory {
	
	private HttpFactory(){
		
	}
	
	/**
	 * 构造GET操作
	 * @param url
	 * @return
	 */
	public static HeadRequestClientBuilder get(String url){
		return get(url,null);
	}
	
	/**
	 * 构造指定模板的GET操作
	 * @param url
	 * @param templateId
	 * @return
	 */
	public static HeadRequestClientBuilder get(String url,String templateId){
		return new HeadRequestClientBuilder(MethodMode.GET,url,templateId);
	}
	
	/**
	 * 构造OPTIONS操作
	 * @param url
	 * @return
	 */
	public static HeadRequestClientBuilder options(String url){
		return options(url,null);
	}
	
	/**
	 * 构造指定模板的OPTIONS操作
	 * @param url
	 * @param templateId
	 * @return
	 */
	public static HeadRequestClientBuilder options(String url,String templateId){
		return new HeadRequestClientBuilder(MethodMode.OPTIONS,url,templateId);
	}
	
	/**
	 * 构造TRACE操作
	 * @param url
	 * @return
	 */
	public static HeadRequestClientBuilder trace(String url){
		return trace(url,null);
	}
	
	/**
	 * 构造指定模板的TRACE操作
	 * @param url
	 * @param templateId
	 * @return
	 */
	public static HeadRequestClientBuilder trace(String url,String templateId){
		return new HeadRequestClientBuilder(MethodMode.TRACE,url,templateId);
	}
	
	/**
	 * 构造DELETE操作
	 * @param url
	 * @return
	 */
	public static HeadRequestClientBuilder delete(String url){
		return delete(url,null);
	}
	
	/**
	 * 构造指定模板的DELETE操作
	 * @param url
	 * @param templateId
	 * @return
	 */
	public static HeadRequestClientBuilder delete(String url,String templateId){
		return new HeadRequestClientBuilder(MethodMode.DELETE,url,templateId);
	}
	
	/**
	 * 构造HEAD操作
	 * @param url
	 * @return
	 */
	public static HeadRequestClientBuilder head(String url){
		return head(url,null);
	}
	
	/**
	 * 构造指定模板的HEAD操作
	 * @param url
	 * @param templateId
	 * @return
	 */
	public static HeadRequestClientBuilder head(String url,String templateId){
		return new HeadRequestClientBuilder(MethodMode.HEAD,url,templateId);
	}
	
	/**
	 * 构造PUT操作
	 * @param url
	 * @return
	 */
	public static BodyRequestClientBuilder put(String url){
		return put(url,null);
	}
	
	/**
	 * 构造指定模板的PUT操作
	 * @param url
	 * @param templateId
	 * @return
	 */
	public static BodyRequestClientBuilder put(String url,String templateId){
		return new BodyRequestClientBuilder(MethodMode.PUT,url,templateId);
	}
	
	/**
	 * 构造PATCH操作
	 * @param url
	 * @return
	 */
	public static BodyRequestClientBuilder patch(String url){
		return patch(url,null);
	}
	
	/**
	 * 构造指定模板的PATCH操作
	 * @param url
	 * @param templateId
	 * @return
	 */
	public static BodyRequestClientBuilder patch(String url,String templateId){
		return new BodyRequestClientBuilder(MethodMode.PATCH,url,templateId);
	}
	
	/**
	 * 构造POST操作
	 * @param url
	 * @return
	 */
	public static PostRequestClientBuilder post(String url){
		return post(url,null);
	}
	
	/**
	 * 构造指定模板的POST操作
	 * @param url
	 * @param templateId
	 * @return
	 */
	public static PostRequestClientBuilder post(String url,String templateId){
		return new PostRequestClientBuilder(MethodMode.POST,url,templateId);
	}

	/**
	 * 彻底释放指定模板的多线程共享的Client实例
	 * @param templateId
	 * @throws Exception
	 */
	public static void closeClient(String templateId) throws Exception{
		ClientInstanceManager manager = getClientInstanceManager();
		if(manager!=null){
		   manager.closeClient(templateId);
		}
	}
	
	/**
	 * 彻底释放多线程共享的Client全部实例
	 * @throws Exception
	 */
	public static void closeAllClients() throws Exception{
		ClientInstanceManager manager = getClientInstanceManager();
		if(manager!=null){
		   manager.closeAllClients();
		}
	}
	
	private static ClientInstanceManager getClientInstanceManager(){
		return BeanContainerFactory
				.getBeanContainer(HttpFactory.class.getClassLoader()).getBean(ClientInstanceManager.DEFAULT_BEAN_NAME);
	}
}
