package org.tinygroup.weixinmeterial.result;

import java.util.List;

import org.tinygroup.weixin.common.ToServerResult;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 图文列表请求结果
 * @author yancheng11334
 *
 */
//微信的报文结构设计太蛋疼
public class NewsListResult implements ToServerResult{

	//总记录数
	private int totalNum;
	
	//本批记录数
	private int itemNum;
	
	private List<Item> items;
	
	@JSONField(name="item")
	public List<Item> getItems() {
		return items;
	}

	@JSONField(name="item")
	public void setItems(List<Item> items) {
		this.items = items;
	}

	@JSONField(name="total_count")
	public int getTotalNum() {
		return totalNum;
	}

	@JSONField(name="total_count")
	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}

	@JSONField(name="item_count")
	public int getItemNum() {
		return itemNum;
	}

	@JSONField(name="item_count")
	public void setItemNum(int itemNum) {
		this.itemNum = itemNum;
	}
	
	class Item {
		private String mediaId;
		
		private long updateTime;
		
		private Content content;

		@JSONField(name="media_id")
		public String getMediaId() {
			return mediaId;
		}

		@JSONField(name="media_id")
		public void setMediaId(String mediaId) {
			this.mediaId = mediaId;
		}

		@JSONField(name="update_time")
		public long getUpdateTime() {
			return updateTime;
		}

		@JSONField(name="update_time")
		public void setUpdateTime(long updateTime) {
			this.updateTime = updateTime;
		}

		@JSONField(name="content")
		public Content getContent() {
			return content;
		}

		@JSONField(name="content")
		public void setContent(Content content) {
			this.content = content;
		}

	    class Content {
	    	private List<NewsListItem> newsListItems;

	    	@JSONField(name="news_item")
			public List<NewsListItem> getNewsListItems() {
				return newsListItems;
			}

	    	@JSONField(name="news_item")
			public void setNewsListItems(List<NewsListItem> newsListItems) {
				this.newsListItems = newsListItems;
			}
			
		}
		
	}
	
}
