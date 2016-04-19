package org.tinygroup.weixinkf.kfmessage;

import org.tinygroup.convert.objectjson.fastjson.ObjectToJson;
import org.tinygroup.weixinkf.kfmessage.item.ImageJsonItem;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 客服图片消息Json包装
 * @author yancheng11334
 *
 */
public class ImageKfMessage extends CommonKfMessage{

	public ImageKfMessage(){
		this(null);
	}
	
	public ImageKfMessage(String mediaId){
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
		ObjectToJson<ImageKfMessage> json= new ObjectToJson<ImageKfMessage>();
		return json.convert(this);
	}
	
}
