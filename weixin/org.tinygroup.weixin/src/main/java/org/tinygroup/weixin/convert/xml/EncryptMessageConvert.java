package org.tinygroup.weixin.convert.xml;

import org.tinygroup.weixin.WeiXinContext;
import org.tinygroup.weixin.WeiXinConvertMode;
import org.tinygroup.weixin.message.EncryptMessage;
import org.tinygroup.xmlparser.node.XmlNode;

public class EncryptMessageConvert extends AbstractXmlNodeConvert{

	public EncryptMessageConvert() {
		super(EncryptMessage.class);
	}

	public WeiXinConvertMode getWeiXinConvertMode() {
		return WeiXinConvertMode.ALL;
	}

	protected boolean checkMatch(XmlNode input, WeiXinContext context) {
		return contains(input,"Encrypt");
	}

}
