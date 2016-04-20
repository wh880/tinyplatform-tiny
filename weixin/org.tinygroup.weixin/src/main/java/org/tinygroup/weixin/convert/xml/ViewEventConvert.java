package org.tinygroup.weixin.convert.xml;

import org.tinygroup.weixin.WeiXinContext;
import org.tinygroup.weixin.WeiXinConvertMode;
import org.tinygroup.weixin.event.ViewEvent;
import org.tinygroup.weixin.util.WeiXinEventMode;
import org.tinygroup.xmlparser.node.XmlNode;

public class ViewEventConvert extends AbstractXmlNodeConvert{

	public ViewEventConvert(){
		super(ViewEvent.class);
	}
	
	public WeiXinConvertMode getWeiXinConvertMode() {
		return WeiXinConvertMode.RECEIVE;
	}
	
	protected boolean checkMatch(XmlNode input, WeiXinContext context) {
		return WeiXinEventMode.VIEW.name().equals(getEvent(input));
	}
	
}
