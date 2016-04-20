package org.tinygroup.weixingroupmessage;

import org.tinygroup.convert.objectjson.fastjson.ObjectToJson;
import org.tinygroup.weixingroupmessage.item.TextJsonItem;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 文本预览消息
 * @author yancheng11334
 *
 */
public class TextPreviewMessage extends CommonPreviewMessage{

	public TextPreviewMessage(){
		this(null,null);
	}
	
	public TextPreviewMessage(String toUser,String content){
		setMsgType("text");
		setToUser(toUser);
		textJsonItem = new TextJsonItem(content);
	}

	@JSONField(name="text")
	private TextJsonItem textJsonItem;

	public TextJsonItem getTextJsonItem() {
		return textJsonItem;
	}

	public void setTextJsonItem(TextJsonItem textJsonItem) {
		this.textJsonItem = textJsonItem;
	}
	
	public String toString(){
		ObjectToJson<TextPreviewMessage> json= new ObjectToJson<TextPreviewMessage>();
		return json.convert(this);
	}
}
