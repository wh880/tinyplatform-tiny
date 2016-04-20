package org.tinygroup.weixinmeterial.convert.json;

import org.tinygroup.weixin.WeiXinContext;
import org.tinygroup.weixin.WeiXinConvertMode;
import org.tinygroup.weixin.convert.json.AbstractJSONObjectConvert;
import org.tinygroup.weixinmeterial.result.MaterialNumsResult;

import com.alibaba.fastjson.JSONObject;

public class MaterialNumsResultConvert extends AbstractJSONObjectConvert {

	public MaterialNumsResultConvert() {
		super(MaterialNumsResult.class);
	}

	public WeiXinConvertMode getWeiXinConvertMode() {
		return WeiXinConvertMode.SEND;
	}

	protected boolean checkMatch(JSONObject input, WeiXinContext context) {
		return input.containsKey("voice_count");
	}

}
