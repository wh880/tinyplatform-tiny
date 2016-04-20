package org.tinygroup.weixinmeterial.result;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 上传永久语音、视频、图文的结果
 * @author yancheng11334
 *
 */
public class PermanentMediaIdResult {

	private String mediaId;

	@JSONField(name="media_id")
	public String getMediaId() {
		return mediaId;
	}

	@JSONField(name="media_id")
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
}
