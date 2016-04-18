package org.tinygroup.weixinmeterial.message;

import java.util.ArrayList;
import java.util.List;

import org.tinygroup.convert.objectjson.fastjson.ObjectToJson;
import org.tinygroup.weixin.common.ToServerMessage;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 上传永久图文
 * @author yancheng11334
 *
 */
public class PermanentNewsMessage implements ToServerMessage{

	@JSONField(name="articles")
	private List<NewsItem>  newsItemList;

	public List<NewsItem> getNewsItemList() {
		if(newsItemList==null){
		   newsItemList = new ArrayList<NewsItem>();
		}
		return newsItemList;
	}

	public void setNewsItemList(List<NewsItem> newsItemList) {
		this.newsItemList = newsItemList;
	}
	
	public void addNewsItem(NewsItem newsItem){
		getNewsItemList().add(newsItem);
	}
	
	public String toString(){
		ObjectToJson<PermanentNewsMessage> json= new ObjectToJson<PermanentNewsMessage>();
		return json.convert(this);
	}
	
	@JSONField(serialize=false)
	public String getWeiXinKey() {
		return "addPermanentNews";
	}
}
