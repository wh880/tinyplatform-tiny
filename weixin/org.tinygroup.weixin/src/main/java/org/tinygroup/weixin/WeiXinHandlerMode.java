package org.tinygroup.weixin;

/**
 * 微信处理器的类型
 * @author yancheng11334
 *
 */
public enum WeiXinHandlerMode {
    /**
     * 处理发送操作的输入对象
     */
	SEND_INPUT,
	/**
	 * 处理发送操作的输出对象
	 */
	SEND_OUPUT,
	/**
	 * 处理接收操作
	 */
	RECEIVE
}
