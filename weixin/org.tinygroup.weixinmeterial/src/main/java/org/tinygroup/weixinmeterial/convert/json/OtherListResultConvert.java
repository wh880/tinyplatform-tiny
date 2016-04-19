package org.tinygroup.weixinmeterial.convert.json;

import org.tinygroup.weixin.WeiXinContext;
import org.tinygroup.weixin.WeiXinConvertMode;
import org.tinygroup.weixin.convert.json.AbstractJSONObjectConvert;
import org.tinygroup.weixinmeterial.result.OtherListResult;

import com.alibaba.fastjson.JSONObject;

public class OtherListResultConvert extends AbstractJSONObjectConvert {

	//获取素材列表结果比较特殊：图文和其他素材外部结构一致，只是内部列表元素结构不同。
//	public boolean isMatch(JSONObject input) {
//		try{
//			if(input.containsKey("item")){
//				JSONArray array = input.getJSONArray("item");
//				return array.getJSONObject(0).containsKey("name");
//			}
//		}catch(Exception e){
//			//忽略异常
//			return false;
//		}
//		
//		return false;
//	}

	public OtherListResultConvert() {
		super(OtherListResult.class);
	}

	public WeiXinConvertMode getWeiXinConvertMode() {
		return WeiXinConvertMode.SEND;
	}

	protected boolean checkMatch(JSONObject input, WeiXinContext context) {
		return input.containsKey("item");
	}

}
