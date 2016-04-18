package org.tinygroup.weixin;

import java.util.List;

import org.tinygroup.weixin.common.Client;
import org.tinygroup.weixin.common.GetTicket;
import org.tinygroup.weixin.common.ToServerMessage;
import org.tinygroup.weixin.result.AccessToken;
import org.tinygroup.weixin.result.JsApiTicket;
import org.tinygroup.weixinhttp.WeiXinHttpConnector;
import org.tinygroup.weixinhttp.WeiXinHttpUpload;

/**
 * 负责处理微信的发送请求
 * @author yancheng11334
 *
 */
public interface WeiXinSender {

	/**
	 * 默认的bean配置名称
	 */
	public static final String DEFAULT_BEAN_NAME="weiXinSender";
	public static final String CONNECTOR_URL_KEY = "token";
	
	WeiXinManager getWeiXinManager();
	
	/**
	 * HTTP协议操作者
	 * @return
	 */
	WeiXinHttpConnector getWeiXinHttpConnector();
	
	void setWeiXinManager(WeiXinManager manager);
	
	List<WeiXinHandler> getMatchWeiXinHandlers(WeiXinHandlerMode mode);
	
	/**
     * 批量注册Handler
     * @param sendHandlerList
     */
    void setSendHandlerList(List<WeiXinHandler> sendHandlerList);
	
    /**
	 * 发送微信消息
	 * @param message
	 */
    void send(ToServerMessage message,WeiXinContext context);
    
    /**
     * 上传微信文件
     * @param upload
     */
    void upload(WeiXinHttpUpload upload,WeiXinContext context);
    
    /**
     * 取得微信认证
     * @param client
     */
    AccessToken connect(Client client);
    
    /**
     * 微信的js临时票据
     * @param ticket
     * @return
     */
    JsApiTicket getJsApiTicket(GetTicket ticket);
}
