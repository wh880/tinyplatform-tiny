package org.tinygroup.httpvisitor.client;

import java.io.Closeable;

import org.tinygroup.context.Context;
import org.tinygroup.httpvisitor.Request;
import org.tinygroup.httpvisitor.Response;
import org.tinygroup.httpvisitor.manager.HttpTemplateManager;

/**
 * HTTP客户端接口，具体实现在扩展工程完成
 * @author yancheng11334
 *
 */
public interface ClientInterface extends Closeable{

	public static final String DEFAULT_BEAN_NAME = "httpClientInterface";
	
	/**
	 * 初始化客户端
	 * @param context
	 */
    void init(Context context);
	
    /**
     * 执行请求，得到响应
     * @param request
     * @return
     */
	Response execute(Request request);
	
	/**
	 * 获得HTTP通讯配置模板管理器
	 * @return
	 */
	HttpTemplateManager getHttpTemplateManager();
	
	/**
	 * 允许多例共享通讯资源
	 * @return
	 */
	boolean allowMultiton();
}
