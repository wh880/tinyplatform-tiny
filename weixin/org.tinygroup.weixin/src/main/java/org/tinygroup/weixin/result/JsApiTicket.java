package org.tinygroup.weixin.result;

import org.tinygroup.weixin.common.ToServerResult;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * js接口票据
 * @author yancheng11334
 *
 */
public class JsApiTicket implements ToServerResult {

    String ticket;
    
	@JSONField(name="expires_in")
    long expiresIn;

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public long getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(long expiresIn) {
		this.expiresIn = expiresIn;
	}
	
	
}
