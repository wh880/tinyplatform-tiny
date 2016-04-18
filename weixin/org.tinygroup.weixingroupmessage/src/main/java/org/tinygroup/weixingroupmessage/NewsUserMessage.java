package org.tinygroup.weixingroupmessage;

import org.tinygroup.convert.objectjson.fastjson.ObjectToJson;
import org.tinygroup.weixingroupmessage.item.NewsJsonItem;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * OpenId列表群发图文消息
 * @author yancheng11334
 *
 */
public class NewsUserMessage extends UserGroupMessage{

	public NewsUserMessage(){
		this(null);
	}
	
	public NewsUserMessage(String mediaId){
		setMsgType("mpnews");
		newsJsonItem = new NewsJsonItem(mediaId);
	}
	
	@JSONField(name="mpnews")
	private NewsJsonItem newsJsonItem;

	public NewsJsonItem getNewsJsonItem() {
		return newsJsonItem;
	}

	public void setNewsJsonItem(NewsJsonItem newsJsonItem) {
		this.newsJsonItem = newsJsonItem;
	}
	
	public String toString(){
		ObjectToJson<NewsUserMessage> json= new ObjectToJson<NewsUserMessage>();
		return json.convert(this);
	}
}
