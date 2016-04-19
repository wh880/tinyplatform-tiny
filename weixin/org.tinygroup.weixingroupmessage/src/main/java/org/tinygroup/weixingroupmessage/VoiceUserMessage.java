package org.tinygroup.weixingroupmessage;

import org.tinygroup.convert.objectjson.fastjson.ObjectToJson;
import org.tinygroup.weixingroupmessage.item.VoiceJsonItem;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * OpenId列表群发语音消息
 * @author yancheng11334
 *
 */
public class VoiceUserMessage extends UserGroupMessage{

	public VoiceUserMessage(){
		this(null);
	}
	
	public VoiceUserMessage(String mediaId){
		setMsgType("voice");
		voiceJsonItem = new VoiceJsonItem(mediaId);
	}
	
	@JSONField(name="voice")
	private VoiceJsonItem voiceJsonItem;

	public VoiceJsonItem getVoiceJsonItem() {
		return voiceJsonItem;
	}

	public void setVoiceJsonItem(VoiceJsonItem voiceJsonItem) {
		this.voiceJsonItem = voiceJsonItem;
	}
	
	public String toString(){
		ObjectToJson<VoiceUserMessage> json= new ObjectToJson<VoiceUserMessage>();
		return json.convert(this);
	}
}
