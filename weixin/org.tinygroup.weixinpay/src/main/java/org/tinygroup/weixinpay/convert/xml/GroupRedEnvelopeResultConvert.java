package org.tinygroup.weixinpay.convert.xml;

import org.tinygroup.weixin.WeiXinContext;
import org.tinygroup.weixin.WeiXinConvertMode;
import org.tinygroup.weixin.convert.xml.AbstractXmlNodeConvert;
import org.tinygroup.weixinpay.result.GroupRedEnvelopeResult;
import org.tinygroup.xmlparser.node.XmlNode;

/**
 * 发送裂变红包结果的转换类
 * @author yancheng11334
 *
 */
public class GroupRedEnvelopeResultConvert extends AbstractXmlNodeConvert{

	public GroupRedEnvelopeResultConvert() {
		super(GroupRedEnvelopeResult.class);
		setPriority(-100);
	}

	public WeiXinConvertMode getWeiXinConvertMode() {
		return WeiXinConvertMode.SEND;
	}

	protected boolean checkMatch(XmlNode input, WeiXinContext context) {
		return "SUCCESS".equals(get(input, "return_code"))
		&& "SUCCESS".equals(get(input, "result_code"))
		&& contains(input, "send_listid") && contains(input, "total_num");
	}

}
