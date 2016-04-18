package org.tinygroup.weixin.message;

import org.tinygroup.weixin.util.WeiXinMessageMode;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 图片消息
 * @author yancheng11334
 *
 */
@XStreamAlias("xml")
public class ImageMessage extends CommonMessage{
	
	public ImageMessage(){
		setMsgType(WeiXinMessageMode.IMAGE.getType());
	}

	@XStreamAlias("PicUrl")
	private String picUrl;
	
	@XStreamAlias("MediaId")
	private String mediaId;

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	
}
