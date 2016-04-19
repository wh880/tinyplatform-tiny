package org.tinygroup.weixin.convert;

import org.tinygroup.weixin.WeiXinContext;
import org.tinygroup.weixin.WeiXinConvert;
import org.tinygroup.weixin.WeiXinConvertMode;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * json报文转换器
 * @author yancheng11334
 *
 */
public class JsonParser extends AbstractParser{


	public <T> T parse(String result,WeiXinContext context,WeiXinConvertMode mode){
		return parse(JSON.parseObject(result),context,mode);
	}
	
	@SuppressWarnings("unchecked")
	private <T> T parse(JSONObject result,WeiXinContext context,WeiXinConvertMode mode){
		for(WeiXinConvert convert:convertList){
		    if( checkConvertMode(convert,mode) && convert.isMatch(result,context)){
		       return (T) convert.convert(result,context);
		    }
		}
		return null;
	}
	
}
