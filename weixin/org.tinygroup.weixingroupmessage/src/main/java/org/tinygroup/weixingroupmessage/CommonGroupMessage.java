package org.tinygroup.weixingroupmessage;

import org.tinygroup.weixin.common.ToServerMessage;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 群消息基础对象
 * @author yancheng11334
 *
 */
public abstract class CommonGroupMessage implements ToServerMessage{

	@JSONField(name="msgtype")
	private String msgType;

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

}
