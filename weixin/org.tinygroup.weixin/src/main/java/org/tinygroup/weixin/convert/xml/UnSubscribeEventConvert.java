package org.tinygroup.weixin.convert.xml;

import org.tinygroup.weixin.WeiXinContext;
import org.tinygroup.weixin.WeiXinConvertMode;
import org.tinygroup.weixin.event.UnSubscribeEvent;
import org.tinygroup.weixin.util.WeiXinEventMode;
import org.tinygroup.xmlparser.node.XmlNode;

public class UnSubscribeEventConvert extends AbstractXmlNodeConvert {

	public UnSubscribeEventConvert(){
		super(UnSubscribeEvent.class);
	}

	public WeiXinConvertMode getWeiXinConvertMode() {
		return WeiXinConvertMode.RECEIVE;
	}

	protected boolean checkMatch(XmlNode input, WeiXinContext context) {
		return WeiXinEventMode.UNSUBSCRIBE.getEvent().equals(getEvent(input));
	}
	
}
