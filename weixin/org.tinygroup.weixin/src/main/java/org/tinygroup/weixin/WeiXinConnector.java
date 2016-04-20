package org.tinygroup.weixin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.tinygroup.weixin.common.Client;
import org.tinygroup.weixin.common.ToServerMessage;
import org.tinygroup.weixin.result.AccessToken;
import org.tinygroup.weixin.result.JsApiTicket;
import org.tinygroup.weixinhttp.WeiXinHttpUpload;

/**
 * 微信连接统一管理
 * @author yancheng11334
 *
 */
public interface WeiXinConnector {

	/**
	 * 默认的bean配置名称
	 */
	public static final String DEFAULT_BEAN_NAME="weiXinConnector";
	
	public static final String ACCESS_TOKEN="ACCESS_TOKEN";

	/**
	 * 获取当前的管理号客户端信息
	 * @return
	 */
	Client  getClient();
	
    /**
     * 获得微信消息发送者，负责往微信服务器发送消息
     * @return
     */
    WeiXinSender getWeiXinSender();
	
    /**
     * 获得微信消息接收者，负责解析微信服务器推送过来的消息
     * @return
     */
	WeiXinReceiver getWeiXinReceiver();
	
	/**
	 * 获取微信的会话管理者
	 * @return
	 */
	WeiXinSessionManager  getWeiXinSessionManager();
	
	/**
	 * 获取微信验证令牌
	 * @return
	 */
	AccessToken getAccessToken();
	
	/**
	 * 获得微信的JS访问票据
	 * @return
	 */
	JsApiTicket getJsApiTicket();
	
	/**
	 * 发送微信消息
	 * @param message
	 */
    void send(ToServerMessage message);
    
    /**
     * 上传微信文件
     * @param upload
     */
    void upload(WeiXinHttpUpload upload);
	
	/**
	 * 接收微信消息
	 * @param request
	 * @param response
	 */
	void receive(HttpServletRequest request,HttpServletResponse response);
	
}
