package org.tinygroup.weixinmeterial.result;

import org.tinygroup.weixin.common.ToServerResult;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 临时缩略图结果
 * @author yancheng11334
 *
 */
public class TempThumbResult implements ToServerResult{

    private String type;
    
	private String thumbMediaId;

	private String createdAt;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@JSONField(name="created_at")
	public String getCreatedAt() {
		return createdAt;
	}

	@JSONField(name="created_at")
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	@JSONField(name="thumb_media_id")
	public String getThumbMediaId() {
		return thumbMediaId;
	}

	@JSONField(name="thumb_media_id")
	public void setThumbMediaId(String thumbMediaId) {
		this.thumbMediaId = thumbMediaId;
	}
}
