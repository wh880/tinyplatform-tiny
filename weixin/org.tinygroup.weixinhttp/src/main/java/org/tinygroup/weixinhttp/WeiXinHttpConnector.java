package org.tinygroup.weixinhttp;

import org.tinygroup.weixinhttp.cert.WeiXinCert;


/**
 * 微信HTTP操作接口
 * @author yancheng11334
 *
 */
public interface WeiXinHttpConnector {

	/**
	 * 默认的bean配置名称
	 */
	public static final String DEFAULT_BEAN_NAME="weiXinHttpConnector";
	
	 /**
     * 用get方式访问微信URL
     *
     * @param url       要访问的微信URL
     * @return 请求结果
     */
    String getUrl(String url);
    
    /**
     * 用post方式访问微信URL
     *
     * @param url       要访问的微信URL
     * @param content
     * @param cert
     * @return 请求结果
     */
    String postUrl(String url, String content,WeiXinCert cert);
    
    /**
     * 上传文件
     * @param url
     * @param upload
     * @return
     */
    String upload(String url,WeiXinHttpUpload upload);
}
