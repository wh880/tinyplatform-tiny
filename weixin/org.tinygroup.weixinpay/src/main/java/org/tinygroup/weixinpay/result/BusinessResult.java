package org.tinygroup.weixinpay.result;

import org.tinygroup.weixin.common.ToServerResult;
import org.tinygroup.weixinpay.pojo.BusinessResultPojo;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 支付的业务失败结果
 * @author yancheng11334
 *
 */
@XStreamAlias("xml")
public class BusinessResult extends BusinessResultPojo implements ToServerResult{

	
}
