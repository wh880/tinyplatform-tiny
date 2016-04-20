package org.tinygroup.weixingroupmessage;

import org.tinygroup.convert.objectjson.fastjson.ObjectToJson;
import org.tinygroup.weixingroupmessage.item.VideoJsonItem;

import com.alibaba.fastjson.annotation.JSONField;

public class VideoUserMessage extends UserGroupMessage{
	
	public VideoUserMessage(){
		this(null);
	}
	
	public VideoUserMessage(String mediaId){
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

	public String toString(){
		ObjectToJson<VideoUserMessage> json= new ObjectToJson<VideoUserMessage>();
		return json.convert(this);
	}
	
}
