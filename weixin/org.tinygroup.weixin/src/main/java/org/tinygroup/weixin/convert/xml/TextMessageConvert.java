package org.tinygroup.weixin.convert.xml;

import org.tinygroup.weixin.WeiXinContext;
import org.tinygroup.weixin.WeiXinConvertMode;
import org.tinygroup.weixin.message.TextMessage;
import org.tinygroup.weixin.util.WeiXinMessageMode;
import org.tinygroup.xmlparser.node.XmlNode;

public class TextMessageConvert extends AbstractXmlNodeConvert {

	public TextMessageConvert(){
		super(TextMessage.class);
	}

	public WeiXinConvertMode getWeiXinConvertMode() {
		return WeiXinConvertMode.RECEIVE;
	}

	protected boolean checkMatch(XmlNode input, WeiXinContext context) {
		return WeiXinMessageMode.TEXT.getType().equals(getMsgType(input));
	}
	

}
