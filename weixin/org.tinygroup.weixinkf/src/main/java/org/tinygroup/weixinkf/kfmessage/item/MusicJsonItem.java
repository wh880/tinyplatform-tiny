package org.tinygroup.weixinkf.kfmessage.item;

import com.alibaba.fastjson.annotation.JSONField;

public class MusicJsonItem {

	@JSONField(name="thumb_media_id")
	private  String thumbMediaId;
	
	private String title;
	
	private String description;
	
	@JSONField(name="musicurl")
	private String musicUrl;
	
	@JSONField(name="hgmusicurl")
	private String hqMusicUrl;

	public String getThumbMediaId() {
		return thumbMediaId;
	}

	public void setThumbMediaId(String thumbMediaId) {
		this.thumbMediaId = thumbMediaId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMusicUrl() {
		return musicUrl;
	}

	public void setMusicUrl(String musicUrl) {
		this.musicUrl = musicUrl;
	}

	public String getHqMusicUrl() {
		return hqMusicUrl;
	}

	public void setHqMusicUrl(String hqMusicUrl) {
		this.hqMusicUrl = hqMusicUrl;
	}
	
	
}
