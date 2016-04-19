package org.tinygroup.weixingroupmessage;

import org.tinygroup.convert.objectjson.fastjson.ObjectToJson;
import org.tinygroup.weixingroupmessage.item.ImageJsonItem;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * OpenId列表群发图片消息
 * @author yancheng11334
 *
 */
public class ImageUserMessage extends UserGroupMessage{

	public ImageUserMessage(){
		this(null);
	}
	
	public ImageUserMessage(String mediaId){
		setMsgType("image");
		imageJsonItem = new ImageJsonItem(mediaId);
	}
	
	@JSONField(name="image")
	private ImageJsonItem imageJsonItem;

	public ImageJsonItem getImageJsonItem() {
		return imageJsonItem;
	}

	public void setImageJsonItem(ImageJsonItem imageJsonItem) {
		this.imageJsonItem = imageJsonItem;
	}
	
	public String toString(){
		ObjectToJson<ImageUserMessage> json= new ObjectToJson<ImageUserMessage>();
		return json.convert(this);
	}
}
