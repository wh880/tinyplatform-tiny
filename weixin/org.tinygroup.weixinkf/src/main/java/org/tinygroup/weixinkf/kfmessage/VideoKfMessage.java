package org.tinygroup.weixinkf.kfmessage;

import org.tinygroup.convert.objectjson.fastjson.ObjectToJson;
import org.tinygroup.weixinkf.kfmessage.item.VideoJsonItem;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 客服视频消息Json包装
 * @author yancheng11334
 *
 */
public class VideoKfMessage extends CommonKfMessage{

	public VideoKfMessage(){
		setMsgType("video");
	}
	
	@JSONField(name="video")
	private VideoJsonItem videoJsonItem;

	public VideoJsonItem getVideoJsonItem() {
		return videoJsonItem;
	}

	public void setVideoJsonItem(VideoJsonItem videoJsonItem) {
		this.videoJsonItem = videoJsonItem;
	}
	
	public String toString(){
		ObjectToJson<VideoKfMessage> json= new ObjectToJson<VideoKfMessage>();
		return json.convert(this);
	}
}
