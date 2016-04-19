package org.tinygroup.weixin;

/**
 * 微信报文转换器的类型
 * @author yancheng11334
 *
 */
public enum WeiXinConvertMode {

	/**
     * 处理发送微信服务器的回复报文
     */
	SEND,
	/**
	 * 处理微信服务器的推送报文
	 */
	RECEIVE,
	/**
	 * 同时包含两种
	 */
	ALL
}
