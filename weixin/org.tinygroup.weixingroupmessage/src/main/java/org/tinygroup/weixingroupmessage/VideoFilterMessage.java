package org.tinygroup.weixingroupmessage;

import org.tinygroup.weixingroupmessage.item.VideoJsonItem;

import com.alibaba.fastjson.annotation.JSONField;

public class VideoFilterMessage extends FilterGroupMessage{

	public VideoFilterMessage(){
		this(null);
	}
	
	public VideoFilterMessage(String mediaId){
		setMsgType("mpvideo");
		videoJsonItem = new VideoJsonItem(mediaId);
	}

	
	@JSONField(name="mpvideo")
	private VideoJsonItem videoJsonItem;


	public VideoJsonItem getVideoJsonItem() {
		return videoJsonItem;
	}

	public void setVideoJsonItem(VideoJsonItem videoJsonItem) {
		this.videoJsonItem = videoJsonItem;
	}

}
