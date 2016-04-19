package org.tinygroup.weixinmeterial.result;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 上传永久图片、缩略图的结果
 * @author yancheng11334
 *
 */
public class PermanentUrlResult {

    private String url;
	
	private String mediaId;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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
