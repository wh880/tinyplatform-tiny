package org.tinygroup.weixinmenu.convert.json;

import org.tinygroup.weixin.WeiXinContext;
import org.tinygroup.weixin.WeiXinConvertMode;
import org.tinygroup.weixin.convert.json.AbstractJSONObjectConvert;
import org.tinygroup.weixinmenu.result.WeiXinMenuResult;

import com.alibaba.fastjson.JSONObject;

public class WeiXinMenuResultConvert extends AbstractJSONObjectConvert {

	public WeiXinMenuResultConvert() {
		super(WeiXinMenuResult.class);
	}

	public WeiXinConvertMode getWeiXinConvertMode() {
		return WeiXinConvertMode.SEND;
	}

	protected boolean checkMatch(JSONObject input, WeiXinContext context) {
		return input.containsKey("menu");
	}

}
