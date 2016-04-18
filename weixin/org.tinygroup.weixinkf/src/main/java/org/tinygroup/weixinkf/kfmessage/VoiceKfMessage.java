package org.tinygroup.weixinkf.kfmessage;

import org.tinygroup.convert.objectjson.fastjson.ObjectToJson;
import org.tinygroup.weixinkf.kfmessage.item.VoiceJsonItem;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 客服语音消息Json包装
 * @author yancheng11334
 *
 */
public class VoiceKfMessage extends CommonKfMessage{

	public VoiceKfMessage(){
		this(null);
	}
	
	public VoiceKfMessage(String mediaId){
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
		ObjectToJson<VoiceKfMessage> json= new ObjectToJson<VoiceKfMessage>();
		return json.convert(this);
	}
	
}
