package org.tinygroup.weixin.convert.xml;

import org.tinygroup.weixin.WeiXinContext;
import org.tinygroup.weixin.WeiXinConvertMode;
import org.tinygroup.weixin.message.VoiceMessage;
import org.tinygroup.weixin.util.WeiXinMessageMode;
import org.tinygroup.xmlparser.node.XmlNode;

public class VoiceMessageConvert extends AbstractXmlNodeConvert{

	public VoiceMessageConvert() {
		super(VoiceMessage.class);
	}

	public WeiXinConvertMode getWeiXinConvertMode() {
		return WeiXinConvertMode.RECEIVE;
	}

	protected boolean checkMatch(XmlNode input, WeiXinContext context) {
		return WeiXinMessageMode.VOICE.getType().equals(getMsgType(input));
	}

}
