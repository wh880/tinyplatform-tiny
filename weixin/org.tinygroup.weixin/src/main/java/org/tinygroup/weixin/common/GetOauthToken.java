package org.tinygroup.weixin.common;

/**
 * 根据code获取用户的信息
 * @author yancheng11334
 *
 */
public class GetOauthToken implements ToServerMessage{

	private String code;
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getWeiXinKey() {
		return "oauth_token";
	}

	public String toString() {
		return "GetOauthToken [code=" + code + "]";
	}

}
