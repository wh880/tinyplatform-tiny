package org.tinygroup.weixingroupmessage.result;

import org.tinygroup.weixin.common.ToServerResult;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 群发消息状态
 * @author yancheng11334
 *
 */
public class MessageStatusResult implements ToServerResult{

	@JSONField(name="msg_id")
    String msgId;
    
    @JSONField(name="msg_status")
    String msgStatus;

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public String getMsgStatus() {
		return msgStatus;
	}

	public void setMsgStatus(String msgStatus) {
		this.msgStatus = msgStatus;
	}
    
    
}
