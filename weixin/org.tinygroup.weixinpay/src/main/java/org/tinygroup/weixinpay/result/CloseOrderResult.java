package org.tinygroup.weixinpay.result;

import org.tinygroup.weixin.common.ToServerResult;
import org.tinygroup.weixinpay.pojo.BusinessResultPojo;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 关闭订单的反馈结果
 * @author yancheng11334
 *
 */
@XStreamAlias("xml")
public class CloseOrderResult extends BusinessResultPojo implements ToServerResult{
	
}
