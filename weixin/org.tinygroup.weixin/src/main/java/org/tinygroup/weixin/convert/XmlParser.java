package org.tinygroup.weixin.convert;

import org.tinygroup.weixin.WeiXinContext;
import org.tinygroup.weixin.WeiXinConvert;
import org.tinygroup.weixin.WeiXinConvertMode;
import org.tinygroup.xmlparser.node.XmlNode;
import org.tinygroup.xmlparser.parser.XmlStringParser;

/**
 * XML报文转换器
 * @author yancheng11334
 *
 */
public class XmlParser extends AbstractParser{
	
	public <T> T parse(String message,WeiXinContext context,WeiXinConvertMode mode){
		XmlStringParser parser = new XmlStringParser();
		XmlNode node =parser.parse(message).getRoot();
		return parse(node,context,mode);
	}
	
	@SuppressWarnings("unchecked")
	private <T> T parse(XmlNode message,WeiXinContext context,WeiXinConvertMode mode){
		for(WeiXinConvert convert:convertList){
		    if(checkConvertMode(convert,mode) && convert.isMatch(message,context)){
		       return (T) convert.convert(message,context);
		    }
		}
		return null;
	}
}
