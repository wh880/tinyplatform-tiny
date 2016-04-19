package org.tinygroup.weixingroupmessage;

import org.tinygroup.weixin.common.ToServerMessage;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 预览接口的群消息
 * @author yancheng11334
 *
 */
public class CommonPreviewMessage implements ToServerMessage{

	@JSONField(name="msgtype")
	private String msgType;
	
	@JSONField(name="touser")
	private String toUser;

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getToUser() {
		return toUser;
	}

	public void setToUser(String toUser) {
		this.toUser = toUser;
	}

	@JSONField(serialize=false)
	public String getWeiXinKey() {
		return "previewMessage";
	}
}
