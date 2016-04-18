package org.tinygroup.weixinmeterial.message;

import org.tinygroup.convert.objectjson.fastjson.ObjectToJson;

/**
 * 图片素材列表请求消息
 * @author yancheng11334
 *
 */
public class ImageListMessage extends GetOtherListMessage{

	public ImageListMessage(){
		setType("image");
	}
	
	public String toString(){
		ObjectToJson<ImageListMessage> json= new ObjectToJson<ImageListMessage>();
		return json.convert(this);
	}
}
