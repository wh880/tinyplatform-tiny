package org.tinygroup.weixinpay.convert.xml;

import org.tinygroup.weixin.WeiXinContext;
import org.tinygroup.weixin.WeiXinConvertMode;
import org.tinygroup.weixin.convert.xml.AbstractXmlNodeConvert;
import org.tinygroup.weixinpay.result.BusinessResult;
import org.tinygroup.xmlparser.node.XmlNode;

/**
 * 业务失败结果转换器
 * @author yancheng11334
 *
 */
public class BusinessResultConvert extends AbstractXmlNodeConvert{

	public BusinessResultConvert() {
		super(BusinessResult.class);
		setPriority(90);
	}

	public WeiXinConvertMode getWeiXinConvertMode() {
		return WeiXinConvertMode.SEND;
	}

	protected boolean checkMatch(XmlNode input, WeiXinContext context) {
		return "SUCCESS".equals(get(input, "return_code")) && "FAIL".equals(get(input, "result_code"));
	}

}
