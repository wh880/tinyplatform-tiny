package org.tinygroup.weixinuser.message;

import org.tinygroup.weixin.common.ToServerMessage;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 获得用户基本信息的请求
 * @author yancheng11334
 *
 */
public class UserInfoMessage implements ToServerMessage{

	String openId;
	
	String lang;

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	@JSONField(serialize=false)
	public String getWeiXinKey() {
		return "getUserInfo";
	}
	
}
