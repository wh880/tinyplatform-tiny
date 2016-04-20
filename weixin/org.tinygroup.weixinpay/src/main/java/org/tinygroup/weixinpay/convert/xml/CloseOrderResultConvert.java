package org.tinygroup.weixinpay.convert.xml;

import org.tinygroup.weixin.WeiXinContext;
import org.tinygroup.weixin.WeiXinConvertMode;
import org.tinygroup.weixin.convert.xml.AbstractXmlNodeConvert;
import org.tinygroup.weixinpay.result.CloseOrderResult;
import org.tinygroup.xmlparser.node.XmlNode;

public class CloseOrderResultConvert extends AbstractXmlNodeConvert{

	public CloseOrderResultConvert() {
		super(CloseOrderResult.class);
	}

	public WeiXinConvertMode getWeiXinConvertMode() {
		return WeiXinConvertMode.SEND;
	}

	protected boolean checkMatch(XmlNode input, WeiXinContext context) {
		return "SUCCESS".equals(get(input, "return_code")) && "SUCCESS".equals(get(input, "result_code")) && contains(input, "openid");
	}

}
