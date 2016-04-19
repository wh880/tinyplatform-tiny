package org.tinygroup.weixin.convert.xml;

import org.tinygroup.weixin.WeiXinContext;
import org.tinygroup.weixin.WeiXinConvertMode;
import org.tinygroup.weixin.message.ShortVideoMessage;
import org.tinygroup.weixin.util.WeiXinMessageMode;
import org.tinygroup.xmlparser.node.XmlNode;

public class ShortVideoMessageConvert extends AbstractXmlNodeConvert{

	public ShortVideoMessageConvert() {
		super(ShortVideoMessage.class);
	}

	public WeiXinConvertMode getWeiXinConvertMode() {
		return WeiXinConvertMode.RECEIVE;
	}

	protected boolean checkMatch(XmlNode input, WeiXinContext context) {
		return WeiXinMessageMode.SHORT_VIDEO.getType().equals(getMsgType(input));
	}

}
