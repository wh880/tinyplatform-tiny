package org.tinygroup.weixin;

import java.util.List;

public interface WeiXinReceiver {

	/**
	 * 默认的bean配置名称
	 */
	public static final String DEFAULT_BEAN_NAME="weiXinReceiver";
	
	List<WeiXinHandler> getMatchWeiXinHandlers(WeiXinHandlerMode mode);

	/**
	 * 批量注册Handler
	 * 
	 * @param receiverHandlerList
	 */
	void setReceiverHandlerList(List<WeiXinHandler> receiverHandlerList);
	
	/**
	 * 接收微信消息
	 * @param context
	 */
	void receive(WeiXinContext context);
}
