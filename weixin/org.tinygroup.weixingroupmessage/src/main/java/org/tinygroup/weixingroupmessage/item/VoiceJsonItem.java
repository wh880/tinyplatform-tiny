package org.tinygroup.weixingroupmessage.item;

import com.alibaba.fastjson.annotation.JSONField;

public class VoiceJsonItem {

	@JSONField(name="media_id")
	private  String mediaId;

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	
    public VoiceJsonItem(){
		
	}
	
    public VoiceJsonItem(String mediaId){
		this.mediaId = mediaId;
	}
}
