package org.tinygroup.weixinmeterial.convert.json;

import org.tinygroup.weixin.WeiXinContext;
import org.tinygroup.weixin.WeiXinConvertMode;
import org.tinygroup.weixin.convert.json.AbstractJSONObjectConvert;
import org.tinygroup.weixinmeterial.result.TempThumbResult;

import com.alibaba.fastjson.JSONObject;

public class TempThumbResultConvert extends AbstractJSONObjectConvert{

	public TempThumbResultConvert() {
		super(TempThumbResult.class);
	}

	public WeiXinConvertMode getWeiXinConvertMode() {
		return WeiXinConvertMode.SEND;
	}

	protected boolean checkMatch(JSONObject input, WeiXinContext context) {
		return input.containsValue("thumb") && input.containsKey("thumb_media_id") && input.containsKey("created_at");
	}

}
