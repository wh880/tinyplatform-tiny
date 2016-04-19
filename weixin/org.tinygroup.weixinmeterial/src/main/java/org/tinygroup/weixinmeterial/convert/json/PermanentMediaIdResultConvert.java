package org.tinygroup.weixinmeterial.convert.json;

import org.tinygroup.weixin.WeiXinContext;
import org.tinygroup.weixin.WeiXinConvertMode;
import org.tinygroup.weixin.convert.json.AbstractJSONObjectConvert;
import org.tinygroup.weixinmeterial.result.PermanentMediaIdResult;

import com.alibaba.fastjson.JSONObject;

public class PermanentMediaIdResultConvert extends AbstractJSONObjectConvert {

	public PermanentMediaIdResultConvert() {
		super(PermanentMediaIdResult.class);
	}

	public WeiXinConvertMode getWeiXinConvertMode() {
		return WeiXinConvertMode.SEND;
	}

	protected boolean checkMatch(JSONObject input, WeiXinContext context) {
		return input.containsKey("media_id") && !input.containsKey("url");
	}

}
