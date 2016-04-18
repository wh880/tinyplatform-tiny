package org.tinygroup.weixinmeterial.message;

import org.tinygroup.convert.objectjson.fastjson.ObjectToJson;

/**
 * 语音素材列表请求消息
 * @author yancheng11334
 *
 */
public class VoiceListMessage extends GetOtherListMessage{
	
	public VoiceListMessage(){
		setType("voice");
	}

	public String toString(){
		ObjectToJson<VoiceListMessage> json= new ObjectToJson<VoiceListMessage>();
		return json.convert(this);
	}
}
