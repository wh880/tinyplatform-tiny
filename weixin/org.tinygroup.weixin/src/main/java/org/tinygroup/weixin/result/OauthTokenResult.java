package org.tinygroup.weixin.result;

import org.tinygroup.weixin.common.ToServerResult;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 用户请求网页授权的信息结果
 * @author yancheng11334
 *
 */
public class OauthTokenResult implements ToServerResult {

	@JSONField(name="access_token")
    String accessToken;
	
	@JSONField(name="expires_in")
    long expiresIn;
	
	@JSONField(name="refresh_token")
	String refreshToken;
	
	@JSONField(name="openid")
	String openId;
	
	String scope;
	
	@JSONField(name="unionid")
	String unionId;

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public long getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(long expiresIn) {
		this.expiresIn = expiresIn;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getUnionId() {
		return unionId;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}
	
}
