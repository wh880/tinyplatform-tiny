package org.tinygroup.weixin.common;

/**
 * 请求JS的临时票据
 * @author yancheng11334
 *
 */
public class GetTicket implements ToServerMessage{

	private String accessToken;
	
	public String getWeiXinKey() {
		return "ticket";
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

}
