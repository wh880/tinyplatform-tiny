package org.tinygroup.weixin.convert.xml;

import org.tinygroup.weixin.WeiXinContext;
import org.tinygroup.weixin.WeiXinConvertMode;
import org.tinygroup.weixin.event.ScanPushEvent;
import org.tinygroup.weixin.util.WeiXinEventMode;
import org.tinygroup.xmlparser.node.XmlNode;

public class ScanPushEventConvert extends AbstractXmlNodeConvert {

	public ScanPushEventConvert() {
		super(ScanPushEvent.class);
	}

	public WeiXinConvertMode getWeiXinConvertMode() {
		return WeiXinConvertMode.RECEIVE;
	}

	protected boolean checkMatch(XmlNode input, WeiXinContext context) {
		return WeiXinEventMode.SCANCODE_PUSH.getEvent().equals(getEvent(input));
	}

}
