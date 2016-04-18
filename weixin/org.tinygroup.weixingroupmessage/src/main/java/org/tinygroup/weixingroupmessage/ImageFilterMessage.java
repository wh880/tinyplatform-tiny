package org.tinygroup.weixingroupmessage;

import org.tinygroup.convert.objectjson.fastjson.ObjectToJson;
import org.tinygroup.weixingroupmessage.item.ImageJsonItem;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 按分组的群发图片消息
 * @author yancheng11334
 *
 */
public class ImageFilterMessage extends FilterGroupMessage{

	public ImageFilterMessage(){
		this(null);
	}
	
	public ImageFilterMessage(String mediaId){
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
		ObjectToJson<ImageFilterMessage> json= new ObjectToJson<ImageFilterMessage>();
		return json.convert(this);
	}
}
