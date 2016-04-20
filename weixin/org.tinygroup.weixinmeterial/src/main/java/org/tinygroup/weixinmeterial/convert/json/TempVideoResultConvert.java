package org.tinygroup.weixinmeterial.convert.json;

import org.tinygroup.weixin.WeiXinContext;
import org.tinygroup.weixin.WeiXinConvertMode;
import org.tinygroup.weixin.convert.json.AbstractJSONObjectConvert;
import org.tinygroup.weixinmeterial.result.TempVideoResult;

import com.alibaba.fastjson.JSONObject;

public class TempVideoResultConvert extends AbstractJSONObjectConvert{

	public TempVideoResultConvert() {
		super(TempVideoResult.class);
	}

	public WeiXinConvertMode getWeiXinConvertMode() {
		return WeiXinConvertMode.SEND;
	}

	protected boolean checkMatch(JSONObject input, WeiXinContext context) {
		return input.containsValue("video") && input.containsKey("media_id") && input.containsKey("created_at");
	}

}
