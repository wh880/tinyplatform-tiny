package org.tinygroup.weixinmeterial.convert.json;

import org.tinygroup.weixin.WeiXinContext;
import org.tinygroup.weixin.WeiXinConvertMode;
import org.tinygroup.weixin.convert.json.AbstractJSONObjectConvert;
import org.tinygroup.weixinmeterial.result.TempImageResult;

import com.alibaba.fastjson.JSONObject;

public class TempImageResultConvert extends AbstractJSONObjectConvert{

	public TempImageResultConvert() {
		super(TempImageResult.class);
	}

	public WeiXinConvertMode getWeiXinConvertMode() {
		return WeiXinConvertMode.SEND;
	}

	protected boolean checkMatch(JSONObject input, WeiXinContext context) {
		return input.containsValue("image") && input.containsKey("media_id") && input.containsKey("created_at");
	}

}
