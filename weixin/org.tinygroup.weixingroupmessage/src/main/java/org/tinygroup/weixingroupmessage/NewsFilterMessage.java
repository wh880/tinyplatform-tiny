package org.tinygroup.weixingroupmessage;

import org.tinygroup.convert.objectjson.fastjson.ObjectToJson;
import org.tinygroup.weixingroupmessage.item.NewsJsonItem;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 按分组的群发图文消息
 * @author yancheng11334
 *
 */
public class NewsFilterMessage extends FilterGroupMessage{

	public NewsFilterMessage(){
		this(null);
	}
	
	public NewsFilterMessage(String mediaId){
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
		ObjectToJson<NewsFilterMessage> json= new ObjectToJson<NewsFilterMessage>();
		return json.convert(this);
	}
}
