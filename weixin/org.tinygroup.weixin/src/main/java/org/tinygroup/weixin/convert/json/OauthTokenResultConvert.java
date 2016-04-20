package org.tinygroup.weixin.convert.json;

import org.tinygroup.weixin.WeiXinContext;
import org.tinygroup.weixin.WeiXinConvertMode;
import org.tinygroup.weixin.result.OauthTokenResult;

import com.alibaba.fastjson.JSONObject;

public class OauthTokenResultConvert extends AbstractJSONObjectConvert{

	public OauthTokenResultConvert() {
		super(OauthTokenResult.class);
	}

	public WeiXinConvertMode getWeiXinConvertMode() {
		return WeiXinConvertMode.SEND;
	}

	protected boolean checkMatch(JSONObject input, WeiXinContext context) {
		return input.containsKey("openid") && input.containsKey("access_token");
	}

}
