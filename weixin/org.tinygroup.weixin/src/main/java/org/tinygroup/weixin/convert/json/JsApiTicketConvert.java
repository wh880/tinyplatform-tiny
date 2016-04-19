package org.tinygroup.weixin.convert.json;

import org.tinygroup.weixin.WeiXinContext;
import org.tinygroup.weixin.WeiXinConvertMode;
import org.tinygroup.weixin.result.JsApiTicket;

import com.alibaba.fastjson.JSONObject;

public class JsApiTicketConvert extends AbstractJSONObjectConvert{

	public JsApiTicketConvert() {
		super(JsApiTicket.class);
	}

	public WeiXinConvertMode getWeiXinConvertMode() {
		return WeiXinConvertMode.SEND;
	}

	protected boolean checkMatch(JSONObject input, WeiXinContext context) {
		return input.containsKey("ticket") && input.containsKey("expires_in");
	}

}
