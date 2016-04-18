package org.tinygroup.weixinkf.kfmessage;

import org.tinygroup.convert.objectjson.fastjson.ObjectToJson;
import org.tinygroup.weixinkf.kfmessage.item.NewsJsonItem;

import com.alibaba.fastjson.annotation.JSONField;

public class NewsKfMessage extends CommonKfMessage{

	public NewsKfMessage(){
		setMsgType("news");
	}
	
	@JSONField(name="news")
	private NewsJsonItem newsJsonItem;

	public NewsJsonItem getNewsJsonItem() {
		return newsJsonItem;
	}

	public void setNewsJsonItem(NewsJsonItem newsJsonItem) {
		this.newsJsonItem = newsJsonItem;
	}



	public String toString(){
		ObjectToJson<NewsKfMessage> json= new ObjectToJson<NewsKfMessage>();
		return json.convert(this);
	}
}
