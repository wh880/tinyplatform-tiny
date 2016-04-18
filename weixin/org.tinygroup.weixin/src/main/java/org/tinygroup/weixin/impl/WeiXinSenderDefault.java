package org.tinygroup.weixin.impl;

import java.util.ArrayList;
import java.util.List;

import org.tinygroup.beancontainer.BeanContainer;
import org.tinygroup.beancontainer.BeanContainerFactory;
import org.tinygroup.commons.tools.StringUtil;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.weixin.WeiXinContext;
import org.tinygroup.weixin.WeiXinConvertMode;
import org.tinygroup.weixin.WeiXinHandler;
import org.tinygroup.weixin.WeiXinHandlerMode;
import org.tinygroup.weixin.WeiXinManager;
import org.tinygroup.weixin.WeiXinSender;
import org.tinygroup.weixin.common.Client;
import org.tinygroup.weixin.common.GetTicket;
import org.tinygroup.weixin.common.ToServerMessage;
import org.tinygroup.weixin.common.UrlConfig;
import org.tinygroup.weixin.exception.WeiXinException;
import org.tinygroup.weixin.result.AccessToken;
import org.tinygroup.weixin.result.JsApiTicket;
import org.tinygroup.weixin.util.WeiXinParserUtil;
import org.tinygroup.weixinhttp.WeiXinHttpConnector;
import org.tinygroup.weixinhttp.WeiXinHttpUpload;
import org.tinygroup.weixinhttp.cert.WeiXinCert;

public class WeiXinSenderDefault implements WeiXinSender{

	private static final Logger LOGGER = LoggerFactory.getLogger(WeiXinSenderDefault.class);
	
	private WeiXinHttpConnector weiXinHttpConnector;
	private WeiXinManager weiXinManager;
	private List<WeiXinHandler> sendHandlerList=new ArrayList<WeiXinHandler>(); 

	private BeanContainer<?> beanContainer;
    
    public WeiXinSenderDefault(){
    	beanContainer = BeanContainerFactory.getBeanContainer(getClass().getClassLoader());
    }
	
	public WeiXinManager getWeiXinManager() {
		if(weiXinManager==null){
		   try{
			   weiXinManager = beanContainer.getBean(WeiXinManager.DEFAULT_BEAN_NAME);
		   }catch(Exception e){
			  throw new WeiXinException("实例化默认WeiXinManager失败:",e);
		   }
		}
		return weiXinManager;
	}

	public void setWeiXinManager(WeiXinManager manager) {
		this.weiXinManager = manager;
	}
	

	public WeiXinHttpConnector getWeiXinHttpConnector() {
		if(weiXinHttpConnector==null){
			try{
				weiXinHttpConnector = beanContainer.getBean(WeiXinHttpConnector.DEFAULT_BEAN_NAME); 
			}catch(Exception e){
			    throw new WeiXinException("实例化默认weiXinHttpConnector失败:",e);
			}
		}
		return weiXinHttpConnector;
	}

	public void setWeiXinHttpConnector(WeiXinHttpConnector weiXinHttpConnector) {
		this.weiXinHttpConnector = weiXinHttpConnector;
	}
	

	public void send(ToServerMessage message, WeiXinContext context) {
		context.setInput(message);
		beforeExecute(context);
		execute(message.getWeiXinKey(),context);
		afterExecute(context);
	}

	public void upload(WeiXinHttpUpload upload, WeiXinContext context) {
		context.setInput(upload);
		beforeExecute(context);
		execute(upload.getWeiXinKey(),context);
		afterExecute(context);
	}

	public AccessToken connect(Client client) {
		WeiXinContext context= new WeiXinContextDefault();
		context.put(CONNECTOR_URL_KEY, client);
		try{
			execute(CONNECTOR_URL_KEY,context);
			return (AccessToken) WeiXinParserUtil.parse((String)context.getOutput(),context,WeiXinConvertMode.SEND);
		}catch(Exception e){
			//忽略转换异常
			LOGGER.logMessage(LogLevel.ERROR, "获取微信访问认证令牌发生异常:", e);
			return null;
		}
	}
	

	public JsApiTicket getJsApiTicket(GetTicket ticket) {
		WeiXinContext context= new WeiXinContextDefault();
		context.put(ticket.getWeiXinKey(), ticket);
		try{
			execute(ticket.getWeiXinKey(),context);
			return (JsApiTicket) WeiXinParserUtil.parse((String)context.getOutput(),context,WeiXinConvertMode.SEND);
		}catch(Exception e){
			//忽略转换异常
			LOGGER.logMessage(LogLevel.ERROR, "获取微信JS的临时票据发生异常:", e);
			return null;
		}
	}

	private void execute(String key, WeiXinContext context){
		//加载URL配置
		UrlConfig config = getWeiXinManager().getUrl(key);
		if(config==null){
			throw new WeiXinException(String.format("没有找到[%s]对应的URL配置", key));
		}
		context.put(UrlConfig.DEFAULT_CONTEXT_NAME, config);
		
		//模板渲染
		String url = getWeiXinManager().renderUrl(key, context);

		//访问微信服务器
		String result=null;
		if("get".equalsIgnoreCase(config.getMethod())){
			result=getWeiXinHttpConnector().getUrl(url);
		}else if("post".equalsIgnoreCase(config.getMethod())){
			if(context.getInput() instanceof WeiXinHttpUpload){
				result=getWeiXinHttpConnector().upload(url,(WeiXinHttpUpload) context.getInput());
			}else{
				if(StringUtil.isEmpty(config.getCertBean())){
				   //非加密访问
				   result=getWeiXinHttpConnector().postUrl(url,context.getInput().toString(),null);
				}else{
				   //证书认证
				   WeiXinCert cert = beanContainer.getBean(config.getCertBean());
				   result=getWeiXinHttpConnector().postUrl(url,context.getInput().toString(),cert);
				}
				
			}
		}else{
			throw new WeiXinException(String.format("未知的HTTP请求类型:[%s]", config.getMethod()));
		}
		
		//处理结果参数
		context.setOutput(result);
	}
	
	private void beforeExecute(WeiXinContext context){
		List<WeiXinHandler> inputHandlers = getMatchWeiXinHandlers(WeiXinHandlerMode.SEND_INPUT);
		for(WeiXinHandler handler:inputHandlers){
			if(handler.isMatch(context.getInput(),context)){
			   handler.process(context.getInput(), context);
			}
		}
	}
	
	private void afterExecute(WeiXinContext context){
		List<WeiXinHandler> outputHandlers = getMatchWeiXinHandlers(WeiXinHandlerMode.SEND_OUPUT);
		for(WeiXinHandler handler:outputHandlers){
			//LOGGER.logMessage(LogLevel.INFO, "handler="+handler.getClass().getName()+" priority="+handler.getPriority());
			if(handler.isMatch(context.getOutput(),context)){
			   handler.process(context.getOutput(), context);
			}
		}
	}

	public List<WeiXinHandler> getMatchWeiXinHandlers(WeiXinHandlerMode mode) {
		List<WeiXinHandler> list = new ArrayList<WeiXinHandler>();
		for(WeiXinHandler handler:sendHandlerList){
			if(handler.getWeiXinHandlerMode()==mode){
				list.add(handler);
			}
		}
		if(list.size()>0){
		   java.util.Collections.sort(list);
		}
		return list;
	}

	public void setSendHandlerList(List<WeiXinHandler> sendHandlerList) {
		this.sendHandlerList = sendHandlerList;
		java.util.Collections.sort(this.sendHandlerList);
	}


}
