package org.tinygroup.weixin;



/**
 * 微信业务处理器
 * @author yancheng11334
 *
 */
public interface WeiXinHandler extends Comparable<WeiXinHandler> {
    
    int getPriority();

    void setPriority(int priority);
    
    WeiXinHandlerMode getWeiXinHandlerMode();
    
    /**
     * 是否匹配对象和上下文
     * @param <T>
     * @param message
     * @return
     */
    <T> boolean isMatch(T message,WeiXinContext context);

    
    /**
     * 处理对象
     * @param <T>
     * @param message
     * @param context
     */
    <T> void process(T message,WeiXinContext context);
}
