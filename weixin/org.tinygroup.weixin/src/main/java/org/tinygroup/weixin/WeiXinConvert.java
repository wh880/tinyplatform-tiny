package org.tinygroup.weixin;

/**
 * 微信消息/结果转换统一接口
 * @author yancheng11334
 *
 */
public interface WeiXinConvert extends Comparable<WeiXinConvert> {
    
	/**
	 * 获得优先级
	 * @return
	 */
	int getPriority();

	/**
	 * 设置优先级
	 * @param priority
	 */
    void setPriority(int priority);
    
    /**
     * 获得报文的状态
     * @return
     */
    WeiXinConvertMode getWeiXinConvertMode();
    
    /**
     * 获得结果类型
     * @return
     */
    Class<?> getCalssType();
    
	/**
	 * 判断转换接口能否处理输入信息（微信报文会出现不同类型报文字段一致的情况，需要根据上下文判断）
	 * @param <INPUT>
	 * @param input
	 * @param context
	 * @return
	 */
    <INPUT> boolean isMatch(INPUT input,WeiXinContext context);
	
	/**
	 * 转换消息（微信报文会出现不同类型报文字段一致的情况，需要根据上下文判断）
	 * @param input
	 * @return
	 */
    <OUTPUT,INPUT> OUTPUT convert(INPUT input,WeiXinContext context);
	
}
