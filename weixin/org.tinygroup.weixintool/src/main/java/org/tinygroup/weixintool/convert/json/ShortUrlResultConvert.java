package org.tinygroup.weixintool.convert.json;

import org.tinygroup.weixin.WeiXinContext;
import org.tinygroup.weixin.WeiXinConvertMode;
import org.tinygroup.weixin.convert.json.AbstractJSONObjectConvert;
import org.tinygroup.weixintool.result.ShortUrlResult;

import com.alibaba.fastjson.JSONObject;

public class ShortUrlResultConvert extends AbstractJSONObjectConvert {

	public ShortUrlResultConvert() {
		super(ShortUrlResult.class);
	}

	public WeiXinConvertMode getWeiXinConvertMode() {
		return WeiXinConvertMode.SEND;
	}

	protected boolean checkMatch(JSONObject input, WeiXinContext context) {
		return input.containsKey("short_url");
	}

}
