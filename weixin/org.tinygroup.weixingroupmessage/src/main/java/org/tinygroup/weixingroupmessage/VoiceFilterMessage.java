package org.tinygroup.weixingroupmessage;

import org.tinygroup.convert.objectjson.fastjson.ObjectToJson;
import org.tinygroup.weixingroupmessage.item.VoiceJsonItem;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 按分组的群发语音消息
 * @author yancheng11334
 *
 */
public class VoiceFilterMessage extends FilterGroupMessage{

	public VoiceFilterMessage(){
		this(null);
	}
	
	public VoiceFilterMessage(String mediaId){
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
		ObjectToJson<VoiceFilterMessage> json= new ObjectToJson<VoiceFilterMessage>();
		return json.convert(this);
	}
}
