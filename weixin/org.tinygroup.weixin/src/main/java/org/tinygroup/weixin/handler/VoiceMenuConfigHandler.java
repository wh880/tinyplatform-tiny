package org.tinygroup.weixin.handler;

import org.tinygroup.weixin.message.VoiceMessage;
import org.tinygroup.weixin.replymessage.TextReplyMessage;

/**
 * 微信菜单处理器(语音消息)
 * 
 * @author yancheng11334
 * 
 */
public class VoiceMenuConfigHandler extends AbstractMenuConfigHandler {

	protected <T> String getContent(T message) {
		VoiceMessage voiceMessage = (VoiceMessage) message;
		return voiceMessage.getRecognition();

	}

	@SuppressWarnings("unchecked")
	protected <T, OUTPUT> OUTPUT wrapperReplyMessage(T message, String content) {
		VoiceMessage voiceMessage = (VoiceMessage) message;
		
		TextReplyMessage replyMessage= new TextReplyMessage();
		replyMessage.setContent(content);
		replyMessage.setToUserName(voiceMessage.getFromUserName());
		replyMessage.setFromUserName(voiceMessage.getToUserName());
		return (OUTPUT)replyMessage;
	}

	@Override
	protected <T> boolean isMatchType(T message) {
		return message instanceof VoiceMessage;
	}

}
