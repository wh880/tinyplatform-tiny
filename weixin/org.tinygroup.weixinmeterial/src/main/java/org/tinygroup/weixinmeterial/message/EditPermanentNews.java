package org.tinygroup.weixinmeterial.message;

import org.tinygroup.convert.objectjson.fastjson.ObjectToJson;
import org.tinygroup.weixin.common.ToServerMessage;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 修改永久图文
 * @author yancheng11334
 *
 */
public class EditPermanentNews implements ToServerMessage{

	@JSONField(name="articles")
	private NewsItem newsItem;
	
	private String index;
	
	@JSONField(name="media_id")
	private String mediaId;

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public NewsItem getNewsItem() {
		return newsItem;
	}

	public void setNewsItem(NewsItem newsItem) {
		this.newsItem = newsItem;
	}
	
	public String toString(){
		ObjectToJson<EditPermanentNews> json= new ObjectToJson<EditPermanentNews>();
		return json.convert(this);
	}
	
	@JSONField(serialize=false)
	public String getWeiXinKey() {
		return "editPermanentNews";
	}
}
