package org.tinygroup.weixinuser.convert.json;

import org.tinygroup.weixin.WeiXinContext;
import org.tinygroup.weixin.WeiXinConvertMode;
import org.tinygroup.weixin.convert.json.AbstractJSONObjectConvert;
import org.tinygroup.weixinuser.result.UserListResult;

import com.alibaba.fastjson.JSONObject;

public class UserListResultConvert extends AbstractJSONObjectConvert{

	public UserListResultConvert() {
		super(UserListResult.class);
	}

	public WeiXinConvertMode getWeiXinConvertMode() {
		return WeiXinConvertMode.SEND;
	}

	protected boolean checkMatch(JSONObject input, WeiXinContext context) {
		return input.containsKey("data") && input.containsKey("next_openid");
	}

}
