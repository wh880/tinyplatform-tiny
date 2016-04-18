package org.tinygroup.weixin.convert.json;

import org.tinygroup.weixin.WeiXinContext;
import org.tinygroup.weixin.WeiXinConvertMode;
import org.tinygroup.weixin.result.ErrorResult;

import com.alibaba.fastjson.JSONObject;

public class ErrorResultConvert extends AbstractJSONObjectConvert {

	public ErrorResultConvert() {
		super(ErrorResult.class);
		setPriority(100);
	}

	public WeiXinConvertMode getWeiXinConvertMode() {
		return WeiXinConvertMode.ALL;
	}

	protected boolean checkMatch(JSONObject input, WeiXinContext context) {
		return input.containsKey("errcode");
	}

}
