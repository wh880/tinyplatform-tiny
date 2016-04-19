package org.tinygroup.weixin.convert.xml;

import org.tinygroup.weixin.WeiXinContext;
import org.tinygroup.weixin.WeiXinConvertMode;
import org.tinygroup.weixin.message.LinkMessage;
import org.tinygroup.weixin.util.WeiXinMessageMode;
import org.tinygroup.xmlparser.node.XmlNode;

public class LinkMessageConvert extends AbstractXmlNodeConvert {

	public LinkMessageConvert() {
		super(LinkMessage.class);
	}

	public WeiXinConvertMode getWeiXinConvertMode() {
		return WeiXinConvertMode.RECEIVE;
	}

	protected boolean checkMatch(XmlNode input, WeiXinContext context) {
		return WeiXinMessageMode.LINK.getType().equals(getMsgType(input));
	}

}
