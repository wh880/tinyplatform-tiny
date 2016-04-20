package org.tinygroup.weixinmeterial.message;

import org.tinygroup.convert.objectjson.fastjson.ObjectToJson;

/**
 * 视频素材列表请求消息
 * @author yancheng11334
 *
 */
public class VideoListMessage extends GetOtherListMessage{

	public VideoListMessage(){
		this.setType("video");
	}
	
	public String toString(){
		ObjectToJson<VideoListMessage> json= new ObjectToJson<VideoListMessage>();
		return json.convert(this);
	}
}
