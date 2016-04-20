package org.tinygroup.weixingroupmessage;

import org.tinygroup.convert.objectjson.fastjson.ObjectToJson;
import org.tinygroup.weixingroupmessage.item.TextJsonItem;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 按分组的群发文本消息
 * @author yancheng11334
 *
 */
public class TextFilterMessage extends FilterGroupMessage{

	public TextFilterMessage(){
		this(null);
	}
	
	public TextFilterMessage(String content){
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
		ObjectToJson<TextFilterMessage> json= new ObjectToJson<TextFilterMessage>();
		return json.convert(this);
	}
}
