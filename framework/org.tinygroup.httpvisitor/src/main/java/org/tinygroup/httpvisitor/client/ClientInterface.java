package org.tinygroup.httpvisitor.client;

import java.io.Closeable;

import org.tinygroup.context.Context;
import org.tinygroup.httpvisitor.Request;
import org.tinygroup.httpvisitor.Response;

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
}
