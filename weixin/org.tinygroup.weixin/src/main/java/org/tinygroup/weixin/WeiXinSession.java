package org.tinygroup.weixin;

import java.io.Serializable;

/**
 * 微信的用户会话
 * @author yancheng11334
 *
 */
public interface WeiXinSession extends Serializable{

	/**
	 * 会话Id
	 * @return
	 */
	String getSessionId();
	
	/**
	 * 是否包含某元素
	 * @param name
	 * @return
	 */
	boolean contains(String name);
	
	/**
	 * 返回指定name的序列化对象
	 * @param <T>
	 * @param name
	 * @return
	 */
	<T extends Serializable> T getParameter(String name);
	
	/**
	 * 设置序列化的参数对象
	 * @param <T>
	 * @param name
	 * @param value
	 */
	<T extends Serializable> void setParameter(String name,T value);
	
	 /**
     * 取得session的创建时间。
     *
     * @return 创建时间戮
     */
    long getCreationTime();

    /**
     * 取得最近访问时间。
     *
     * @return 最近访问时间戮
     */
    long getLastAccessedTime();

    /**
     * 取得session的最大不活动期限，超过此时间，session就会失效。
     *
     * @return 不活动期限的秒数,0表示永不过期
     */
    int getMaxInactiveInterval();
    
    /**
     * 设置session的最大不活动期限，单位秒
     * @param maxInactiveInterval
     */
    void setMaxInactiveInterval(int maxInactiveInterval);
	
	/**
     * 判断session有没有过期。
     *
     * @return 如果过期了，则返回<code>true</code>
     */
    boolean isExpired();
    
    /**
     * 更新session
     */
    void update();
}
