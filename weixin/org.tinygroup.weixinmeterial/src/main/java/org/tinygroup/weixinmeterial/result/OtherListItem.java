package org.tinygroup.weixinmeterial.result;

import com.alibaba.fastjson.annotation.JSONField;

public class OtherListItem {

	private String url;
	
	private String name;
	
	private long updateTime;
	
	private String mediaId;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JSONField(name="update_time")
	public long getUpdateTime() {
		return updateTime;
	}

	@JSONField(name="update_time")
	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}

	@JSONField(name="media_id")
	public String getMediaId() {
		return mediaId;
	}

	@JSONField(name="media_id")
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	
	
}
