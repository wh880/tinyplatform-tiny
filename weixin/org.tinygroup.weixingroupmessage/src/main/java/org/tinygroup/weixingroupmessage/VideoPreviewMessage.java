package org.tinygroup.weixingroupmessage;

import org.tinygroup.convert.objectjson.fastjson.ObjectToJson;
import org.tinygroup.weixingroupmessage.item.VideoJsonItem;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 视频预览消息
 * @author yancheng11334
 *
 */
public class VideoPreviewMessage extends CommonPreviewMessage{

	public VideoPreviewMessage(){
		this(null,null);
	}
	
	public VideoPreviewMessage(String toUser,String mediaId){
		setMsgType("mpvideo");
		setToUser(toUser);
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
		ObjectToJson<VideoPreviewMessage> json= new ObjectToJson<VideoPreviewMessage>();
		return json.convert(this);
	}
}
