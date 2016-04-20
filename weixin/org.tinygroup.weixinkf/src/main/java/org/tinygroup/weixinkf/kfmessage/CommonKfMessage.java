package org.tinygroup.weixinkf.kfmessage;

import org.tinygroup.weixin.common.ToServerMessage;
import org.tinygroup.weixinkf.kfaccount.CustomerServiceAccount;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 客服消息统一接口
 * @author yancheng11334
 *
 */
public class CommonKfMessage implements ToServerMessage{

	@JSONField(name="touser")
	private String toUser;
	
	@JSONField(name="msgtype")
	private String msgType;
	
	public String getToUser() {
		return toUser;
	}

	public void setToUser(String toUser) {
		this.toUser = toUser;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	//客服消息可以指定客服信息，如果为空则为微信号
	@JSONField(name="customservice")
	private CustomerServiceAccount kfAccount;

	public CustomerServiceAccount getKfAccount() {
		return kfAccount;
	}

	public void setKfAccount(CustomerServiceAccount kfAccount) {
		this.kfAccount = kfAccount;
	}

	@JSONField(serialize=false)
	public String getWeiXinKey() {
		return "sendKf";
	}
	
}
