package org.tinygroup.weixinkf.kfmessage.item;

import com.alibaba.fastjson.annotation.JSONField;

public class ImageJsonItem {

	@JSONField(name="media_id")
	private  String mediaId;

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	
	public ImageJsonItem(){
		
	}
	
    public ImageJsonItem(String mediaId){
		this.mediaId = mediaId;
	}
}
