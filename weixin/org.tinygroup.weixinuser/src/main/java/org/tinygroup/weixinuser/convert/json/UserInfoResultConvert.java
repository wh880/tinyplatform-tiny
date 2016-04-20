package org.tinygroup.weixinuser.convert.json;

import org.tinygroup.weixin.WeiXinContext;
import org.tinygroup.weixin.WeiXinConvertMode;
import org.tinygroup.weixin.convert.json.AbstractJSONObjectConvert;
import org.tinygroup.weixinuser.result.UserInfoResult;

import com.alibaba.fastjson.JSONObject;

public class UserInfoResultConvert extends AbstractJSONObjectConvert{

	
	public UserInfoResultConvert() {
		super(UserInfoResult.class);
	}

	public WeiXinConvertMode getWeiXinConvertMode() {
		return WeiXinConvertMode.SEND;
	}

	protected boolean checkMatch(JSONObject input, WeiXinContext context) {
		return input.containsKey("openid") && input.containsKey("groupid");
	}

}
