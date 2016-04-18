package org.tinygroup.weixin.convert.xml;

import org.tinygroup.weixin.WeiXinContext;
import org.tinygroup.weixin.WeiXinConvertMode;
import org.tinygroup.weixin.event.ClickEvent;
import org.tinygroup.weixin.util.WeiXinEventMode;
import org.tinygroup.xmlparser.node.XmlNode;

public class ClickEventConvert extends AbstractXmlNodeConvert {

	public ClickEventConvert(){
		super(ClickEvent.class);
	}
	
	public WeiXinConvertMode getWeiXinConvertMode() {
		return WeiXinConvertMode.RECEIVE;
	}
	
	protected boolean checkMatch(XmlNode input, WeiXinContext context) {
		return WeiXinEventMode.CLICK.name().equals(getEvent(input));
	}
	
}
