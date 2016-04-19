package org.tinygroup.weixin.convert.xml;

import org.tinygroup.weixin.WeiXinContext;
import org.tinygroup.weixin.WeiXinConvertMode;
import org.tinygroup.weixin.message.ImageMessage;
import org.tinygroup.weixin.util.WeiXinMessageMode;
import org.tinygroup.xmlparser.node.XmlNode;

public class ImageMessageConvert extends AbstractXmlNodeConvert {

	public ImageMessageConvert() {
		super(ImageMessage.class);
	}

	public WeiXinConvertMode getWeiXinConvertMode() {
		return WeiXinConvertMode.RECEIVE;
	}

	protected boolean checkMatch(XmlNode input, WeiXinContext context) {
		return WeiXinMessageMode.IMAGE.getType().equals(getMsgType(input));
	}

}
