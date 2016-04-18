package org.tinygroup.weixin.convert.xml;

import org.tinygroup.weixin.WeiXinContext;
import org.tinygroup.weixin.WeiXinConvertMode;
import org.tinygroup.weixin.event.MessageSendFinishEvent;
import org.tinygroup.weixin.util.WeiXinEventMode;
import org.tinygroup.xmlparser.node.XmlNode;

public class MessageSendFinishEventConvert extends AbstractXmlNodeConvert{

	public MessageSendFinishEventConvert(){
		super(MessageSendFinishEvent.class);
	}
	
	public WeiXinConvertMode getWeiXinConvertMode() {
		return WeiXinConvertMode.RECEIVE;
	}
	
	protected boolean checkMatch(XmlNode input, WeiXinContext context) {
		return WeiXinEventMode.MASS_SEND_JOB_FINISH.getEvent().equals(getEvent(input));
	}
	
}
