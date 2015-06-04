package org.tinygroup.springmvc.extension;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * 在线程上下文中保持ExtensionMappingInstance实例和ServletWebRequest
 * 
 * @author renhui
 * 
 */
public class RequestInstanceHolder {
	
	
	private static final ThreadLocal<ExtensionMappingInstance> extensionLocal=new InheritableThreadLocal<ExtensionMappingInstance>();

	  /**
     * request local
     */
	private static final ThreadLocal<ServletWebRequest> reqLocal = new InheritableThreadLocal<ServletWebRequest>();
	
	public static ExtensionMappingInstance getMappingInstance(){
		return extensionLocal.get();
	}
	
	public static void setMappingInstance(ExtensionMappingInstance mappingInstance){
		extensionLocal.set(mappingInstance);
	}
	
	 /**
     * 从本地线程中取得{@link ServletWebRequest}对象。
     *
     * @return
     */
    public static ServletWebRequest getServletWebRequest() {
        return reqLocal.get();
    }

    /**
     * 在本地线程中设置{@link ServletWebRequest}对象。
     *
     * @param servletWebRequest
     */
    public static void setServletWebRequest(ServletWebRequest servletWebRequest) {
        reqLocal.set(servletWebRequest);
    }
	
	public static void  clearMappingInstance(){
		extensionLocal.set(null);
		reqLocal.set(null);
	}
	
}
