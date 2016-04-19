package org.tinygroup.weixin.handler;

import org.tinygroup.commons.tools.StringUtil;
import org.tinygroup.menucommand.CommandExecutor;
import org.tinygroup.menucommand.CommandResult;
import org.tinygroup.weixin.WeiXinContext;
import org.tinygroup.weixin.WeiXinSession;
import org.tinygroup.weixin.event.ClickEvent;
import org.tinygroup.weixin.exception.WeiXinException;
import org.tinygroup.weixin.handler.AbstractMenuConfigHandler;
import org.tinygroup.weixin.replymessage.TextReplyMessage;

/**
 * 微信菜单处理器(点击推送消息)
 * @author yancheng11334
 *
 */
public class ClickMenuConfigHandler extends AbstractMenuConfigHandler {

	protected <T> boolean isMatchType(T message) {
		return message instanceof ClickEvent;
	}
	
	@SuppressWarnings("unchecked")
	protected <T, OUTPUT> OUTPUT wrapperReplyMessage(T message, String content) {
		ClickEvent clickEvent = (ClickEvent) message;

		TextReplyMessage replyMessage= new TextReplyMessage();
		replyMessage.setContent(content);
		replyMessage.setToUserName(clickEvent.getFromUserName());
		replyMessage.setFromUserName(clickEvent.getToUserName());
		return (OUTPUT)replyMessage;
	}
	

	public <T> void process(T message,WeiXinContext context){
		WeiXinSession  session =  context.getWeiXinSession();
		if(session==null){
		   throw new WeiXinException("没有找到该消息对应的会话");
		}
		//对于click事件，菜单Id就是content
		String menuId = getContent(message);
		try{
			CommandExecutor executor = getMenuConfigManager().getCommandExecutor(menuId, "", context);
			CommandResult result = executor.execute(context);
			if(result!=null){
				context.setOutput(wrapperReplyMessage(message,result.getMessage()));
				session.setParameter(MENU_ID_NAME, result.getMenuId());
				getWeiXinSessionManager().addWeiXinSession(session);
			}
			
		}catch(Exception e){
			throw new WeiXinException("菜单信息处理器发生异常",e);
		}
		
	}

	protected <T> String getContent(T message) {
		ClickEvent clickEvent = (ClickEvent) message;
		return clickEvent.getEventKey();
	}

	protected  boolean isMatchMessage(String content,WeiXinContext context){
		//这里消息内容就是菜单Id
		return (content!=null && getMenuConfigManager().getCommandExecutor(content, "", context)!=null)?true:false;
	}

}
