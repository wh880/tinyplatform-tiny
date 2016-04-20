package org.tinygroup.weixin.convert.xml;

import org.tinygroup.weixin.WeiXinContext;
import org.tinygroup.weixin.WeiXinConvertMode;
import org.tinygroup.weixin.event.ScanAlertEvent;
import org.tinygroup.weixin.util.WeiXinEventMode;
import org.tinygroup.xmlparser.node.XmlNode;

public class ScanAlertEventConvert extends AbstractXmlNodeConvert {

	public ScanAlertEventConvert(){
		super(ScanAlertEvent.class);
	}
	
	public WeiXinConvertMode getWeiXinConvertMode() {
		return WeiXinConvertMode.RECEIVE;
	}
	
	protected boolean checkMatch(XmlNode input, WeiXinContext context) {
		return WeiXinEventMode.SCANCODE_WAITMSG.getEvent().equals(getEvent(input));
	}

}
