package org.tinygroup.weixin.convert.xml;

import org.tinygroup.weixin.WeiXinContext;
import org.tinygroup.weixin.WeiXinConvertMode;
import org.tinygroup.weixin.event.LocationEvent;
import org.tinygroup.weixin.util.WeiXinEventMode;
import org.tinygroup.xmlparser.node.XmlNode;

public class LocationEventConvert extends AbstractXmlNodeConvert {

	public LocationEventConvert(){
		super(LocationEvent.class);
	}
	
	public WeiXinConvertMode getWeiXinConvertMode() {
		return WeiXinConvertMode.RECEIVE;
	}
	
	protected boolean checkMatch(XmlNode input, WeiXinContext context) {
		return WeiXinEventMode.LOCATION.getEvent().equals(getEvent(input));
	}
	
}
