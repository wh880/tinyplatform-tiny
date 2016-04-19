package org.tinygroup.weixingroupmessage.item;

import com.alibaba.fastjson.annotation.JSONField;

public class VideoJsonItem {

	@JSONField(name="media_id")
	private  String mediaId;

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	
	 public VideoJsonItem(){
			
	}
		
	 public VideoJsonItem(String mediaId){
			this.mediaId = mediaId;
     }
}
