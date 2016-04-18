package org.tinygroup.weixin.convert.json;

import org.tinygroup.weixin.WeiXinContext;
import org.tinygroup.weixin.WeiXinConvertMode;
import org.tinygroup.weixin.result.AccessToken;

import com.alibaba.fastjson.JSONObject;

public class AccessTokenConvert extends AbstractJSONObjectConvert{

	public AccessTokenConvert() {
		super(AccessToken.class);
		setPriority(100);
	}

	public WeiXinConvertMode getWeiXinConvertMode() {
		return WeiXinConvertMode.SEND;
	}

	protected boolean checkMatch(JSONObject input, WeiXinContext context) {
		return input.containsKey("access_token");
	}

}
