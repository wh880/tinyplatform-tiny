package org.tinygroup.weixinmeterial.convert.json;

import org.tinygroup.weixin.WeiXinContext;
import org.tinygroup.weixin.WeiXinConvertMode;
import org.tinygroup.weixin.convert.json.AbstractJSONObjectConvert;
import org.tinygroup.weixinmeterial.result.NewsListResult;

import com.alibaba.fastjson.JSONObject;

public class NewsListResultConvert extends AbstractJSONObjectConvert {

	//获取素材列表结果比较特殊：图文和其他素材外部结构一致，只是内部列表元素结构不同。
//	public boolean isMatch(JSONObject input) {
//		try{
//			if(input.containsKey("item")){
//				JSONArray array = input.getJSONArray("item");
//				return array.getJSONObject(0).containsKey("content");
//			}
//		}catch(Exception e){
//			//忽略异常
//			return false;
//		}
//		
//		return false;
//	}

	public NewsListResultConvert() {
		super(NewsListResult.class);
	}

	public WeiXinConvertMode getWeiXinConvertMode() {
		return WeiXinConvertMode.SEND;
	}

	protected boolean checkMatch(JSONObject input, WeiXinContext context) {
		return input.containsKey("item");
	}

}
