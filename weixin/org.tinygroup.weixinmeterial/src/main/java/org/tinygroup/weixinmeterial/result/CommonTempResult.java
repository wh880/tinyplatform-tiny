package org.tinygroup.weixinmeterial.result;

import org.tinygroup.weixin.common.ToServerResult;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 临时素材上传结果
 * @author yancheng11334
 *
 */
public class CommonTempResult implements ToServerResult{

	private String type;
    
	private String mediaId;

	private String createdAt;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@JSONField(name="media_id")
	public String getMediaId() {
		return mediaId;
	}

	@JSONField(name="media_id")
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	@JSONField(name="created_at")
	public String getCreatedAt() {
		return createdAt;
	}

	@JSONField(name="created_at")
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
}
