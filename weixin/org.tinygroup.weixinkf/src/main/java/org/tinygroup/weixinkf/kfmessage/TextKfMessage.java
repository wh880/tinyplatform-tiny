package org.tinygroup.weixinkf.kfmessage;

import org.tinygroup.convert.objectjson.fastjson.ObjectToJson;
import org.tinygroup.weixinkf.kfmessage.item.TextJsonItem;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 客服文本消息Json包装
 * @author yancheng11334
 *
 */
public class TextKfMessage extends CommonKfMessage{

	public TextKfMessage(){
	   this(null);
	}
	
	public TextKfMessage(String content){
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
		ObjectToJson<TextKfMessage> json= new ObjectToJson<TextKfMessage>();
		return json.convert(this);
	}
}
