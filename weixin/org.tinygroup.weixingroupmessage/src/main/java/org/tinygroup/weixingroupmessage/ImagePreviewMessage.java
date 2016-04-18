package org.tinygroup.weixingroupmessage;

import org.tinygroup.convert.objectjson.fastjson.ObjectToJson;
import org.tinygroup.weixingroupmessage.item.ImageJsonItem;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 图片预览消息
 * @author yancheng11334
 *
 */
public class ImagePreviewMessage extends CommonPreviewMessage{

	public ImagePreviewMessage(){
		this(null,null);
	}
	
	public ImagePreviewMessage(String toUser,String mediaId){
		setMsgType("image");
		setToUser(toUser);
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
		ObjectToJson<ImagePreviewMessage> json= new ObjectToJson<ImagePreviewMessage>();
		return json.convert(this);
	}
}
