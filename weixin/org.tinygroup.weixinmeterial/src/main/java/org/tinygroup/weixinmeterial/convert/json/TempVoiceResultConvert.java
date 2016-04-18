package org.tinygroup.weixinmeterial.convert.json;

import org.tinygroup.weixin.WeiXinContext;
import org.tinygroup.weixin.WeiXinConvertMode;
import org.tinygroup.weixin.convert.json.AbstractJSONObjectConvert;
import org.tinygroup.weixinmeterial.result.TempVoiceResult;

import com.alibaba.fastjson.JSONObject;

public class TempVoiceResultConvert extends AbstractJSONObjectConvert{

	public TempVoiceResultConvert() {
		super(TempVoiceResult.class);
	}

	public WeiXinConvertMode getWeiXinConvertMode() {
		return WeiXinConvertMode.SEND;
	}

	protected boolean checkMatch(JSONObject input, WeiXinContext context) {
		return input.containsValue("voice") && input.containsKey("media_id") && input.containsKey("created_at");
	}

}
