package org.tinygroup.weixinpay.convert.xml;

import org.tinygroup.weixin.WeiXinContext;
import org.tinygroup.weixin.WeiXinConvertMode;
import org.tinygroup.weixin.convert.xml.AbstractXmlNodeConvert;
import org.tinygroup.weixinpay.result.RefundResult;
import org.tinygroup.xmlparser.node.XmlNode;

/**
 * 退款结果转换类
 * @author yancheng11334
 *
 */
public class RefundResultConvert extends AbstractXmlNodeConvert{

	public RefundResultConvert() {
		super(RefundResult.class);
	}

	public WeiXinConvertMode getWeiXinConvertMode() {
		return WeiXinConvertMode.SEND;
	}

	protected boolean checkMatch(XmlNode input, WeiXinContext context) {
		return "SUCCESS".equals(get(input, "return_code")) && "SUCCESS".equals(get(input, "result_code")) && contains(input, "refund_id");
	}

}
