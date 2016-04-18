package org.tinygroup.weixin.message;

import org.tinygroup.weixin.util.WeiXinMessageMode;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 视频消息
 * @author yancheng11334
 *
 */
@XStreamAlias("xml")
public class VideoMessage extends CommonMessage{

	public VideoMessage(){
		setMsgType(WeiXinMessageMode.VIDEO.getType());
	}
	
	@XStreamAlias("MediaId")
	private String mediaId;
	
	@XStreamAlias("ThumbMediaId")
	private String thumbMediaId;

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public String getThumbMediaId() {
		return thumbMediaId;
	}

	public void setThumbMediaId(String thumbMediaId) {
		this.thumbMediaId = thumbMediaId;
	}

}
