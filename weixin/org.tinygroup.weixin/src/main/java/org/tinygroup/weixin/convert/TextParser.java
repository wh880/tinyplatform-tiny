package org.tinygroup.weixin.convert;

import org.tinygroup.weixin.WeiXinContext;
import org.tinygroup.weixin.WeiXinConvert;
import org.tinygroup.weixin.WeiXinConvertMode;

/**
 * 普通文本报文转换器
 * @author yancheng11334
 *
 */
public class TextParser extends AbstractParser{

	@SuppressWarnings("unchecked")
	public <T> T parse(String result, WeiXinContext context,
			WeiXinConvertMode mode) {
		for(WeiXinConvert convert:convertList){
		    if( checkConvertMode(convert,mode) && convert.isMatch(result,context)){
		       return (T) convert.convert(result,context);
		    }
		}
		return null;
	}

}
