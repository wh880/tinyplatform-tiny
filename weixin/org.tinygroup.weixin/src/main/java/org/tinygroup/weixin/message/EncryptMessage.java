package org.tinygroup.weixin.message;

import org.tinygroup.weixin.common.FromServerMessage;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 加密消息
 * @author yancheng11334
 *
 */
@XStreamAlias("xml")
public class EncryptMessage implements FromServerMessage{

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
	
}
