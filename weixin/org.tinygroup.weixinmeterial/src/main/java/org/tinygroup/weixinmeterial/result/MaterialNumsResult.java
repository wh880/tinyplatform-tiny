package org.tinygroup.weixinmeterial.result;

import org.tinygroup.weixin.common.ToServerResult;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 永久素材总数
 * @author yancheng11334
 *
 */
public class MaterialNumsResult implements ToServerResult{

	private int voiceNums;
	
	private int imageNums;
	
	private int newsNums;
	
	private int videoNums;

	@JSONField(name="voice_count")
	public int getVoiceNums() {
		return voiceNums;
	}

	@JSONField(name="voice_count")
	public void setVoiceNums(int voiceNums) {
		this.voiceNums = voiceNums;
	}

	@JSONField(name="image_count")
	public int getImageNums() {
		return imageNums;
	}

	@JSONField(name="image_count")
	public void setImageNums(int imageNums) {
		this.imageNums = imageNums;
	}

	@JSONField(name="news_count")
	public int getNewsNums() {
		return newsNums;
	}

	@JSONField(name="news_count")
	public void setNewsNums(int newsNums) {
		this.newsNums = newsNums;
	}

	@JSONField(name="video_count")
	public int getVideoNums() {
		return videoNums;
	}

	@JSONField(name="video_count")
	public void setVideoNums(int videoNums) {
		this.videoNums = videoNums;
	}
	
}
