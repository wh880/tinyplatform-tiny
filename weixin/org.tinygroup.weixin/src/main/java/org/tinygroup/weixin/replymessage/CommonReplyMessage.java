package org.tinygroup.weixin.replymessage;

import org.tinygroup.weixin.common.FromServerResult;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 通用回复消息<br>
 * 微信消息有三种模式：非加密、加密、兼容，兼容模式会同时包含前两者内容。
 * @author yancheng11334
 *
 */
public class CommonReplyMessage implements FromServerResult{

	@XStreamAlias("FromUserName")
	private String fromUserName;
	
	@XStreamAlias("MsgType")
	private String msgType;
	
	@XStreamAlias("CreateTime")
	private int createTime;
	
	@XStreamAlias("Encrypt")
	private String encrypt;
	
	@XStreamAlias("ToUserName")
	private String toUserName;

	public String getEncrypt() {
		return encrypt;
	}

	public void setEncrypt(String encrypt) {
		this.encrypt = encrypt;
	}

	public String getToUserName() {
		return toUserName;
	}

	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}

	public String getFromUserName() {
		return fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public int getCreateTime() {
		return createTime;
	}

	public void setCreateTime(int createTime) {
		this.createTime = createTime;
	}
	
}
