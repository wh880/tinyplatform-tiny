package org.tinygroup.weixingroupmessage;

import org.tinygroup.convert.objectjson.fastjson.ObjectToJson;
import org.tinygroup.weixingroupmessage.item.NewsJsonItem;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 图文预览消息
 * @author yancheng11334
 *
 */
public class NewsPreviewMessage extends CommonPreviewMessage{

	public NewsPreviewMessage(){
		this(null,null);
	}
	
    public NewsPreviewMessage(String toUser,String mediaId){
    	setMsgType("mpnews");
    	setToUser(toUser);
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
		ObjectToJson<NewsPreviewMessage> json= new ObjectToJson<NewsPreviewMessage>();
		return json.convert(this);
	}
	
}
