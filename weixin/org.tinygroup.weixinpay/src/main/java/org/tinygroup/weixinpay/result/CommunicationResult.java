package org.tinygroup.weixinpay.result;

import org.tinygroup.weixin.common.ToServerResult;
import org.tinygroup.weixinpay.pojo.CommunicationResultPojo;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 支付的通信失败结果
 * @author yancheng11334
 *
 */
@XStreamAlias("xml")
public class CommunicationResult extends CommunicationResultPojo implements ToServerResult{
	
	public String toString(){
		XStream xstream = new XStream();
		xstream.setMode(XStream.NO_REFERENCES);
		xstream.processAnnotations(getClass());
		return xstream.toXML(this);
	}
}
