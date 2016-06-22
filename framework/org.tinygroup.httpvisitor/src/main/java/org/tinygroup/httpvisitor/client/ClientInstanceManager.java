package org.tinygroup.httpvisitor.client;

import java.io.IOException;

/**
 * 客户端实例管理器
 * @author yancheng11334
 *
 */
public interface ClientInstanceManager {
	
	public static final String DEFAULT_BEAN_NAME = "clientInstanceManager";

	void registerClient(String templateId,ClientInterface client);
	
	ClientInterface getClientInterface(String templateId);
	
	void closeClient(String templateId) throws IOException;
	
	void closeAllClients() throws IOException;
}
