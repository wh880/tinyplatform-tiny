package org.tinygroup.weixinmeterial.message;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 单条图文信息
 * @author yancheng11334
 *
 */
public class NewsItem {

	private String title="";
	
	@JSONField(name="thumb_media_id")
	private String thumbMediaId="";
	
	private String author="";
	
	private String digest="";
	
	@JSONField(name="show_cover_pic")
	private String showCover="";
	
	private String content="";
	
	@JSONField(name="content_source_url")
	private String contentUrl="";

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getThumbMediaId() {
		return thumbMediaId;
	}

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

	public String getShowCover() {
		return showCover;
	}

	public void setShowCover(String showCover) {
		this.showCover = showCover;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContentUrl() {
		return contentUrl;
	}

	public void setContentUrl(String contentUrl) {
		this.contentUrl = contentUrl;
	}
	
	
}
