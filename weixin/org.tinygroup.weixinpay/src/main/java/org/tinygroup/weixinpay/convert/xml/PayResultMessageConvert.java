package org.tinygroup.weixinpay.convert.xml;

import org.tinygroup.weixin.WeiXinContext;
import org.tinygroup.weixin.WeiXinConvertMode;
import org.tinygroup.weixin.convert.xml.AbstractXmlNodeConvert;
import org.tinygroup.weixinpay.message.PayResultMessage;
import org.tinygroup.xmlparser.node.XmlNode;

/**
 * 微信支付结果的转换器
 * 
 * @author yancheng11334
 * 
 */
public class PayResultMessageConvert extends AbstractXmlNodeConvert {

	public PayResultMessageConvert() {
		super(PayResultMessage.class);
	}

	public WeiXinConvertMode getWeiXinConvertMode() {
		return WeiXinConvertMode.RECEIVE;
	}

	protected boolean checkMatch(XmlNode input, WeiXinContext context) {
		return "SUCCESS".equals(get(input, "return_code"))
				&& "SUCCESS".equals(get(input, "result_code"))
				&& contains(input, "openid") && contains(input, "cash_fee");
	}

}
