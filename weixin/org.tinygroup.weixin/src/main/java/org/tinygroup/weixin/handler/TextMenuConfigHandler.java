package org.tinygroup.weixin.handler;

import org.tinygroup.weixin.message.TextMessage;
import org.tinygroup.weixin.replymessage.TextReplyMessage;

/**
 * 微信菜单处理器(文本消息)
 * @author yancheng11334
 *
 */
public class TextMenuConfigHandler extends AbstractMenuConfigHandler {

	protected <T> String getContent(T message) {
		TextMessage textMessage = (TextMessage) message;
		return textMessage.getContent();
	}

	@SuppressWarnings("unchecked")
	protected <T, OUTPUT> OUTPUT wrapperReplyMessage(
			T message, String content) {
		TextMessage textMessage = (TextMessage) message;
		
		TextReplyMessage replyMessage= new TextReplyMessage();
		replyMessage.setContent(content);
		replyMessage.setToUserName(textMessage.getFromUserName());
		replyMessage.setFromUserName(textMessage.getToUserName());
		return (OUTPUT)replyMessage;
	}

	@Override
	protected <T> boolean isMatchType(T message) {
		return message instanceof TextMessage;
	}

}
