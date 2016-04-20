package org.tinygroup.weixin.impl;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.tinygroup.beancontainer.BeanContainer;
import org.tinygroup.beancontainer.BeanContainerFactory;
import org.tinygroup.commons.io.StreamUtil;
import org.tinygroup.commons.tools.StringUtil;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.weixin.WeiXinConnector;
import org.tinygroup.weixin.WeiXinContext;
import org.tinygroup.weixin.WeiXinConvertMode;
import org.tinygroup.weixin.WeiXinReceiver;
import org.tinygroup.weixin.WeiXinSender;
import org.tinygroup.weixin.WeiXinSession;
import org.tinygroup.weixin.WeiXinSessionManager;
import org.tinygroup.weixin.common.Client;
import org.tinygroup.weixin.common.FromServerMessage;
import org.tinygroup.weixin.common.GetTicket;
import org.tinygroup.weixin.common.ToServerMessage;
import org.tinygroup.weixin.event.CommonEvent;
import org.tinygroup.weixin.exception.WeiXinException;
import org.tinygroup.weixin.message.CommonMessage;
import org.tinygroup.weixin.result.AccessToken;
import org.tinygroup.weixin.result.JsApiTicket;
import org.tinygroup.weixin.util.WeiXinParserUtil;
import org.tinygroup.weixin.util.WeiXinSignatureUtil;
import org.tinygroup.weixinhttp.WeiXinHttpUpload;

public class WeiXinConnectorDefault implements WeiXinConnector{

	private static final Logger LOGGER = LoggerFactory.getLogger(WeiXinConnectorDefault.class);
	
	private BeanContainer<?> beanContainer;
	
	private Client client;
	
	private WeiXinReceiver weiXinReceiver;
	
	private WeiXinSender weiXinSender;
	
	private WeiXinSessionManager weiXinSessionManager;
	
	private long connectionTime = 0;
	private AccessToken accessToken;
	private JsApiTicket jsApiTicket;
	
	public WeiXinConnectorDefault(){
		beanContainer = BeanContainerFactory.getBeanContainer(getClass().getClassLoader());
		
	}
	
	public Client getClient() {
		if(client==null){
			try{
				client = beanContainer.getBean(Client.DEFAULT_BEAN_NAME); 
			}catch(Exception e){
			    throw new WeiXinException("实例化默认client失败:",e);
			}
		}
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public WeiXinReceiver getWeiXinReceiver() {
		if(weiXinReceiver==null){
			try{
				weiXinReceiver = beanContainer.getBean(WeiXinReceiver.DEFAULT_BEAN_NAME); 
			}catch(Exception e){
			    throw new WeiXinException("实例化默认WeiXinReceiver失败:",e);
			}
		}
		return weiXinReceiver;
	}

	public void setWeiXinReceiver(WeiXinReceiver weiXinReceiver) {
		this.weiXinReceiver = weiXinReceiver;
	}

	public WeiXinSender getWeiXinSender() {
		if(weiXinSender==null){
			try{
				weiXinSender = beanContainer.getBean(WeiXinSender.DEFAULT_BEAN_NAME); 
			}catch(Exception e){
			    throw new WeiXinException("实例化默认weiXinSender失败:",e);
			}
		}
		return weiXinSender;
	}

	public void setWeiXinSender(WeiXinSender weiXinSender) {
		this.weiXinSender = weiXinSender;
	}

	public WeiXinSessionManager getWeiXinSessionManager() {
		if(weiXinSessionManager==null){
			try{
				weiXinSessionManager = beanContainer.getBean(WeiXinSessionManager.DEFAULT_BEAN_NAME); 
			}catch(Exception e){
			    throw new WeiXinException("实例化默认weiXinSessionManager失败:",e);
			}
		}
		return weiXinSessionManager;
	}

	public void setWeiXinSessionManager(WeiXinSessionManager weiXinSessionManager) {
		this.weiXinSessionManager = weiXinSessionManager;
	}

	public AccessToken getAccessToken() {
		if (accessToken==null || (System.currentTimeMillis() - connectionTime) / 1000 > accessToken.getExpiresIn() + 60) {
			accessToken= getWeiXinSender().connect(getClient());
			connectionTime = System.currentTimeMillis();
        }
        return accessToken;
	}
	

	public JsApiTicket getJsApiTicket() {
		if (jsApiTicket==null || (System.currentTimeMillis() - connectionTime) / 1000 > jsApiTicket.getExpiresIn() + 60) {
			GetTicket ticket = new GetTicket();
			ticket.setAccessToken(getAccessToken().getAccessToken());
			jsApiTicket = getWeiXinSender().getJsApiTicket(ticket);
			//jsApiTicket依赖accessToken，因此共享connectionTime时间
		}
		return jsApiTicket;
	}


	public void send(ToServerMessage message) {
		getWeiXinSender().send(message, wrapperMessage(message));
	}

	public void upload(WeiXinHttpUpload upload) {
		getWeiXinSender().upload(upload, wrapperUpload(upload));
	}

	public void receive(HttpServletRequest request, HttpServletResponse response) {
		LOGGER.logMessage(LogLevel.DEBUG, "HTTP服务器推送处理开始...");
		
		//如果需要检查签名，以检查结果为准;不需要检查签名，直接返回true
		boolean checktag = getClient().isCheckSignature()?checkSignature(request,response):true;
		
		if(checktag){
			String method = request.getMethod();
			if("get".equalsIgnoreCase(method)){
				try {
					dealService(request,response);
				} catch (Exception e) {
					throw new WeiXinException("响应公众号服务发生异常",e);
				}
			}else if("post".equalsIgnoreCase(method)){
				try {
					receiveMessage(request,response);
				} catch (Exception e) {
					throw new WeiXinException("推送消息发生异常",e);
				}
			}else{
				throw new WeiXinException("不支持的HTTP请求："+method);
			}
			LOGGER.logMessage(LogLevel.DEBUG, "HTTP服务器推送处理结束!");
		}else{
			if(getClient().isCheckSignature() ){
				LOGGER.logMessage(LogLevel.ERROR, "开发服务器验证微信签名失败,URL参数:{0},token:{1}",request.getQueryString(),getClient().getToken());
			}
		}
		
	}
	
	/**
	 * 处理post消息
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	private void receiveMessage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		LOGGER.logMessage(LogLevel.DEBUG, "推送消息处理开始...");
		WeiXinContext context = new WeiXinContextDefault();
		String xml = StreamUtil.readText(request.getInputStream(), "UTF-8", false);
		LOGGER.logMessage(LogLevel.DEBUG, "URL参数:"+request.getQueryString());
		LOGGER.logMessage(LogLevel.DEBUG, "推送报文:"+xml);
		
		//获得模式参数
		String encryptType = request.getParameter("encrypt_type");
		String timestamp=request.getParameter("timestamp");
		String nonce=request.getParameter("nonce");
		//执行解密逻辑
		if("aes".equals(encryptType)){
			xml = WeiXinSignatureUtil.decryptMessage(xml, timestamp, nonce, getClient());
			LOGGER.logMessage(LogLevel.DEBUG, "解密后报文:"+xml);
		}
		FromServerMessage message = WeiXinParserUtil.parse(xml,context,WeiXinConvertMode.RECEIVE);
		dealWeiXinSession(message,context);
		context.setInput(message);
		getWeiXinReceiver().receive(context);
		Object result = context.getOutput();
		if(result!=null){
			String source = result.toString();
			LOGGER.logMessage(LogLevel.DEBUG, "原始返回报文:"+source);
			response.setCharacterEncoding("UTF-8");
			//执行加密逻辑
			if("aes".equals(encryptType)){
				source = WeiXinSignatureUtil.encryptMessage(source, timestamp, nonce, client);
				LOGGER.logMessage(LogLevel.DEBUG, "加密后返回报文:"+source);
			}
			response.getWriter().write(source);
		}else{
			LOGGER.logMessage(LogLevel.DEBUG, "无返回报文");
		}
		LOGGER.logMessage(LogLevel.DEBUG, "推送消息处理结束!");
	}
	
    /**
     * 验证签名(符合微信规范)
     * @param request
     * @param response
     * @throws IOException 
     */
	private boolean checkSignature(HttpServletRequest request,HttpServletResponse response){
		String timestamp=request.getParameter("timestamp");
		String nonce=request.getParameter("nonce");
		String signature=request.getParameter("signature");
		
		LOGGER.logMessage(LogLevel.DEBUG, "timestamp={0},nonce={1},signature={2},验证签名处理开始...",timestamp,nonce,signature);
		boolean checktag = false;
		//计算签名
		String result = WeiXinSignatureUtil.createSignature(getClient().getToken(), timestamp, nonce);
		if(result!=null && signature!=null && result.equals(signature)){
		   checktag = true;
		}
		//增加输出日志
		LOGGER.logMessage(LogLevel.DEBUG, "token={0},checktag={1},验证签名处理结束!",getClient().getToken(),checktag);
		return checktag;
	}
	
	 /**
	  * 响应微信服务器信息
	  * @param request
	  * @param response
	  * @throws Exception
	  */
	private void dealService(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String echostr=request.getParameter("echostr");
		
		if(echostr!=null){
			//返回echostr
			response.getWriter().write(echostr);
		}else{
			//返回空值
			response.getWriter().write("");
		}
	}
	
	private void dealWeiXinSession(FromServerMessage message,WeiXinContext context) throws Exception{
		String userId = null;
		//获取消息的用户Id
		if(message instanceof CommonMessage){
			CommonMessage commonMessage = (CommonMessage) message;
			if(!StringUtil.isEmpty(commonMessage.getFromUserName())){
				userId = commonMessage.getFromUserName();
			}
		}else if(message instanceof CommonEvent){
			CommonEvent commonEvent = (CommonEvent) message;
			if(!StringUtil.isEmpty(commonEvent.getFromUserName())){
				userId = commonEvent.getFromUserName();
			}
		}
		
		if(userId!=null){
			
			WeiXinSession session = getWeiXinSessionManager().getWeiXinSession(userId);
			
			if(session==null){
				//第一次访问创建会话
				session = getWeiXinSessionManager().createWeiXinSession(userId);
			
			}else{
				//更新访问时间
				
				session.update();
				
			}
			getWeiXinSessionManager().addWeiXinSession(session);
			context.put(WeiXinContext.WEIXIN_SESSION, session);
			
		}
		
	}
	
	
	private WeiXinContext wrapperMessage(ToServerMessage message){
		WeiXinContext context = new WeiXinContextDefault();
		context.setInput(message);
		context.put(ACCESS_TOKEN,getAccessToken().getAccessToken());
		context.put(Client.DEFAULT_CONTEXT_NAME,getClient());
		return context;
	}
	
	private WeiXinContext wrapperUpload(WeiXinHttpUpload upload){
		WeiXinContext context = new WeiXinContextDefault();
		context.setInput(upload);
		context.put(ACCESS_TOKEN,getAccessToken().getAccessToken());
		context.put(Client.DEFAULT_CONTEXT_NAME,getClient());
		return context;
	}

}
