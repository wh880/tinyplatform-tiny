package org.tinygroup.weixingroupmessage.convert.json;

import org.tinygroup.weixin.WeiXinContext;
import org.tinygroup.weixin.WeiXinConvertMode;
import org.tinygroup.weixin.convert.json.AbstractJSONObjectConvert;
import org.tinygroup.weixingroupmessage.result.MessageStatusResult;

import com.alibaba.fastjson.JSONObject;

public class MessageStatusResultConvert extends AbstractJSONObjectConvert{

	public MessageStatusResultConvert() {
		super(MessageStatusResult.class);
	}

	public WeiXinConvertMode getWeiXinConvertMode() {
		return WeiXinConvertMode.SEND;
	}

	protected boolean checkMatch(JSONObject input, WeiXinContext context) {
		return input.containsKey("msg_id") && input.containsKey("msg_status");
	}

}
