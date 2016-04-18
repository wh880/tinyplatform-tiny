package org.tinygroup.weixinmeterial.result;

import com.alibaba.fastjson.annotation.JSONField;

public class NewsListItem {

    private String title;
	
	private String thumbMediaId;
	
	private String author;
	
	private String digest;
	
	private String showCover;
	
	private String content;
	
	private String contentUrl;
	
	private String url;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@JSONField(name="thumb_media_id")
	public String getThumbMediaId() {
		return thumbMediaId;
	}

	@JSONField(name="thumb_media_id")
	public void setThumbMediaId(String thumbMediaId) {
		this.thumbMediaId = thumbMediaId;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getDigest() {
		return digest;
	}

	public void setDigest(String digest) {
		this.digest = digest;
	}

	@JSONField(name="show_cover_pic")
	public String getShowCover() {
		return showCover;
	}

	@JSONField(name="show_cover_pic")
	public void setShowCover(String showCover) {
		this.showCover = showCover;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@JSONField(name="content_source_url")
	public String getContentUrl() {
		return contentUrl;
	}

	@JSONField(name="content_source_url")
	public void setContentUrl(String contentUrl) {
		this.contentUrl = contentUrl;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
