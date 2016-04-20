package org.tinygroup.weixin;

import org.tinygroup.weixin.common.UrlConfig;
import org.tinygroup.weixin.common.UrlConfigs;

/**
 * 微信配置管理
 * @author yancheng11334
 *
 */
public interface WeiXinManager {

	/**
	 * 默认的bean配置名称
	 */
	public static final String DEFAULT_BEAN_NAME="weiXinManager";
	
	 /**
     * 返回指定key对应的URL
     *
     * @param key
     * @return
     */
    UrlConfig getUrl(String key);

    /**
     * 添加一条配置
     *
     * @param urlConfig
     */
    void addUrlConfig(UrlConfig urlConfig);

    /**
     * 添加一组配置
     *
     * @param urlConfigs
     */
    void addUrlConfigs(UrlConfigs urlConfigs);
    
    /**
     * 移除一条配置
     *
     * @param urlConfig
     */
    void removeUrlConfig(UrlConfig urlConfig);

    /**
     * 移除一组配置
     *
     * @param urlConfigs
     */
    void removeUrlConfigs(UrlConfigs urlConfigs);
    
    /**
     * 渲染URL资源
     * @param key
     * @param context
     * @return
     */
    String renderUrl(String key,WeiXinContext context);
}
