package org.tinygroup.weixinkf.convert.json;

import org.tinygroup.weixin.WeiXinContext;
import org.tinygroup.weixin.WeiXinConvertMode;
import org.tinygroup.weixin.convert.json.AbstractJSONObjectConvert;
import org.tinygroup.weixinkf.result.CustomerAccountListResult;

import com.alibaba.fastjson.JSONObject;

public class CustomerAccountListResultConvert extends AbstractJSONObjectConvert{

	public CustomerAccountListResultConvert() {
		super(CustomerAccountListResult.class);
	}

	public WeiXinConvertMode getWeiXinConvertMode() {
		return WeiXinConvertMode.SEND;
	}

	protected boolean checkMatch(JSONObject input, WeiXinContext context) {
		return input.containsKey("kf_list");
	}

}
