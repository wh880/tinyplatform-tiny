package org.tinygroup.weixingroupmessage;

import org.tinygroup.convert.objectjson.fastjson.ObjectToJson;
import org.tinygroup.weixingroupmessage.item.TextJsonItem;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * OpenId列表群发文本消息
 * @author yancheng11334
 *
 */
public class TextUserMessage extends UserGroupMessage{
	
	public TextUserMessage(){
		this(null);
	}
	
	public TextUserMessage(String content){
		setMsgType("text");
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
		ObjectToJson<TextUserMessage> json= new ObjectToJson<TextUserMessage>();
		return json.convert(this);
	}
}
