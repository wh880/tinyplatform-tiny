package org.tinygroup.weixin.impl;

import java.util.ArrayList;
import java.util.List;

import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.weixin.WeiXinContext;
import org.tinygroup.weixin.WeiXinHandler;
import org.tinygroup.weixin.WeiXinHandlerMode;
import org.tinygroup.weixin.WeiXinReceiver;
import org.tinygroup.weixin.exception.RepeatMessageException;

public class WeiXinReceiverDefault implements WeiXinReceiver{

	private static final Logger LOGGER = LoggerFactory.getLogger(WeiXinReceiverDefault.class);
	
	private List<WeiXinHandler> receiverHandlerList=new ArrayList<WeiXinHandler>(); 

	public List<WeiXinHandler> getMatchWeiXinHandlers(WeiXinHandlerMode mode) {
		List<WeiXinHandler> list = new ArrayList<WeiXinHandler>();
		for(WeiXinHandler handler:receiverHandlerList){
			if(handler.getWeiXinHandlerMode()==mode){
				list.add(handler);
			}
		}
		if(list.size()>0){
		   java.util.Collections.sort(list);
		}
		return list;
	}

	public void setReceiverHandlerList(List<WeiXinHandler> receiverHandlerList) {
		this.receiverHandlerList = receiverHandlerList;
		java.util.Collections.sort(this.receiverHandlerList);
	}

	public void receive(WeiXinContext context) {
		List<WeiXinHandler> dealHandlers = getMatchWeiXinHandlers(WeiXinHandlerMode.RECEIVE);
		try{
			for(WeiXinHandler handler:dealHandlers){
				LOGGER.logMessage(LogLevel.DEBUG, "{0}开始匹配消息",handler.getClass().getName());
				if(handler.isMatch(context.getInput(),context)){
				   LOGGER.logMessage(LogLevel.DEBUG, "{0}开始处理消息",handler.getClass().getName());
				   handler.process(context.getInput(),context);
				   LOGGER.logMessage(LogLevel.DEBUG, "{0}处理消息结束",handler.getClass().getName());
				}
				LOGGER.logMessage(LogLevel.DEBUG, "{0}匹配消息结束",handler.getClass().getName());
			}
		}catch(RepeatMessageException e){
			//重复消息不用处理
		}
		
	}

}
