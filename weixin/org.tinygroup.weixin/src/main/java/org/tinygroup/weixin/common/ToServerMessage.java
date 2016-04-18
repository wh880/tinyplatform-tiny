package org.tinygroup.weixin.common;


/**
 * 发送到微信服务器的消息
 * @author yancheng11334
 *
 */
public interface ToServerMessage {

	/**
	 * 获取微信通讯的URL键值
	 * @return
	 */
	String getWeiXinKey();
}
