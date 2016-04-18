package org.tinygroup.weixinpay.convert.xml;

import org.tinygroup.weixin.WeiXinContext;
import org.tinygroup.weixin.WeiXinConvertMode;
import org.tinygroup.weixin.convert.xml.AbstractXmlNodeConvert;
import org.tinygroup.weixinpay.result.CommunicationResult;
import org.tinygroup.xmlparser.node.XmlNode;

/**
 * 支付通信失败结果转换器
 * @author yancheng11334
 *
 */
public class CommunicationResultConvert extends AbstractXmlNodeConvert{

	public CommunicationResultConvert() {
		super(CommunicationResult.class);
		setPriority(100);
	}

	public WeiXinConvertMode getWeiXinConvertMode() {
		return WeiXinConvertMode.SEND;
	}

	protected boolean checkMatch(XmlNode input, WeiXinContext context) {
		return "FAIL".equals(get(input, "return_code"));
	}

}
