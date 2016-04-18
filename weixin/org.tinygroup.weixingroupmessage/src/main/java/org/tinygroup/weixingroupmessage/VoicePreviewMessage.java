package org.tinygroup.weixingroupmessage;

import org.tinygroup.convert.objectjson.fastjson.ObjectToJson;
import org.tinygroup.weixingroupmessage.item.VoiceJsonItem;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 语音预览消息
 * @author yancheng11334
 *
 */
public class VoicePreviewMessage extends CommonPreviewMessage{

	public VoicePreviewMessage(){
		this(null,null);
	}
	
	public VoicePreviewMessage(String toUser,String mediaId){
		setMsgType("voice");
		setToUser(toUser);
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
		ObjectToJson<VoicePreviewMessage> json= new ObjectToJson<VoicePreviewMessage>();
		return json.convert(this);
	}
}
